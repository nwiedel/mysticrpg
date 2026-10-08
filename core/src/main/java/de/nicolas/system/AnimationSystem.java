package de.nicolas.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.GdxRuntimeException;
import de.nicolas.asset.AssetService;
import de.nicolas.asset.AtlasAsset;
import de.nicolas.component.AnimationComponent;
import de.nicolas.component.AnimationComponent.AnimationType;
import de.nicolas.component.FacingComponent;
import de.nicolas.component.FacingComponent.FacingDirection;
import de.nicolas.component.GraphicComponent;

import java.util.HashMap;
import java.util.Map;

public class AnimationSystem extends IteratingSystem {

    private static final float FRAME_DURATION = 1 / 8f;


    private final AssetService assetService;
    private final Map<CacheKey, Animation<TextureRegion>> animationCache;

    public AnimationSystem(AssetService assetService) {
        super(Family.all(AnimationComponent.class, GraphicComponent.class, FacingComponent.class).get());
        this.assetService = assetService;
        animationCache = new HashMap<>();
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        AnimationComponent animationComponent = AnimationComponent.MAPPER.get(entity);
        FacingDirection facingDirection = FacingComponent.MAPPER.get(entity).getDirection();
        final float stateTime;
        if (animationComponent.isDirty() || facingDirection != animationComponent.getDirection()){
            updateAnimation(animationComponent, facingDirection);
            stateTime = 0;
        } else {
            stateTime = animationComponent.incAndGetStateTime(deltaTime);
        }

        Animation<TextureRegion> animation = animationComponent.getAnimation();
        animation.setPlayMode(animationComponent.getPlayMode());
        TextureRegion keyFrame = animation.getKeyFrame(stateTime);
        GraphicComponent.MAPPER.get(entity).setRegion(keyFrame);
    }

    private void updateAnimation(AnimationComponent animationComponent, FacingDirection facingDirection) {
        AtlasAsset atlasAsset = animationComponent.getAtlasAsset();
        String atlasKey = animationComponent.getAtlasKey();
        AnimationType type = animationComponent.getType();
        CacheKey cacheKey = new CacheKey(atlasAsset, atlasKey, type, facingDirection);
        Animation<TextureRegion> animation = animationCache.computeIfAbsent(cacheKey, key -> {
            TextureAtlas textureAtlas = assetService.get(atlasAsset);
            String combinedKey = atlasKey + "/" + type.getAtlasKey() + "_" + facingDirection.getAtlasKey();
            Array<TextureAtlas.AtlasRegion> regions = textureAtlas.findRegions(combinedKey);
            if (regions.isEmpty()){
                throw new GdxRuntimeException("No regions found for key: " + combinedKey);
            }
            return new Animation<>(FRAME_DURATION, regions);
        });
        animationComponent.setAnimation(animation, facingDirection);
    }

    public record CacheKey(
        AtlasAsset atlasAsset,
        String atlasKey,
        AnimationType type,
        FacingDirection direction
    ){
    }
}
