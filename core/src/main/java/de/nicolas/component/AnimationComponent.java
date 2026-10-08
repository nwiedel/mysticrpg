package de.nicolas.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import de.nicolas.asset.AtlasAsset;
import de.nicolas.component.FacingComponent.FacingDirection;

public class AnimationComponent implements Component {

    public static final ComponentMapper<AnimationComponent> MAPPER = ComponentMapper.getFor(AnimationComponent.class);

    private final AtlasAsset atlasAsset;
    private final String atlasKey;
    private AnimationType type;
    private FacingDirection direction;
    private PlayMode playMode;
    private float speed;
    private float stateTime;
    private Animation<TextureRegion> animation;
    private boolean dirty;

    public AnimationComponent(AtlasAsset atlasAsset,
                              String atlasKey,
                              AnimationType type,
                              PlayMode playMode,
                              float speed) {
        this.atlasAsset = atlasAsset;
        this.atlasKey = atlasKey;
        this.type = type;
        this.direction = null;
        this.playMode = playMode;
        this.speed = speed;
        this.stateTime = 0f;
        this.animation = null;
    }

    public void setAnimation(Animation<TextureRegion> animation, FacingDirection direction){
        this.animation = animation;
        this.direction = direction;
        stateTime = 0f;
        dirty = false;
    }

    public FacingDirection getDirection() {
        return direction;
    }

    public Animation<TextureRegion> getAnimation() {
        return animation;
    }

    public AtlasAsset getAtlasAsset() {
        return atlasAsset;
    }

    public String getAtlasKey() {
        return atlasKey;
    }

    public void setType(AnimationType type) {
        this.type = type;
        dirty = true;
    }

    public AnimationType getType() {
        return type;
    }

    public PlayMode getPlayMode() {
        return playMode;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public void setPlayMode(PlayMode playMode) {
        this.playMode = playMode;
    }

    public boolean isDirty() {
        return dirty;
    }

    public boolean isFinished(){
        return animation.isAnimationFinished(stateTime);
    }

    public float incAndGetStateTime(float deltaTime){
        stateTime += deltaTime * speed;
        return stateTime;
    }

    public enum AnimationType{
        IDLE, WALK;

        private final String atlasKey;

        AnimationType(){
            atlasKey = name().toLowerCase();
        }

        public String getAtlasKey() {
            return atlasKey;
        }
    }
}
