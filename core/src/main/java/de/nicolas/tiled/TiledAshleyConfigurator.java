package de.nicolas.tiled;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.TextureData;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FileTextureData;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.objects.TiledMapTileMapObject;
import com.badlogic.gdx.math.Vector;
import com.badlogic.gdx.math.Vector2;
import de.nicolas.GDXGame;
import de.nicolas.asset.AssetService;
import de.nicolas.asset.AtlasAsset;
import de.nicolas.component.GraphicComponent;
import de.nicolas.component.TransformComponent;

public class TiledAshleyConfigurator {

    private Engine engine;
    private AssetService assetService;

    public TiledAshleyConfigurator(Engine engine, AssetService assetService) {
        this.engine = engine;
        this.assetService = assetService;
    }

    public void onLoadObjects(TiledMapTileMapObject tileMapObject){
        Entity entity = engine.createEntity();
        TiledMapTile tile = tileMapObject.getTile();
        TextureRegion textureRegion = getTextureRegion(tile);
        int z = tile.getProperties().get("z", 1, Integer.class);
        entity.add(new GraphicComponent(Color.WHITE.cpy(), textureRegion));
        addEntityTransform(
           tileMapObject.getX(), tileMapObject.getY(),
            z,
            textureRegion.getRegionWidth(), textureRegion.getRegionHeight(),
            tileMapObject.getScaleX(), tileMapObject.getScaleY(),
            entity
        );

        engine.addEntity(entity);
    }

    private void addEntityTransform(
        float x, float y,
        int z,
        float width, float height,
        float scaleX, float scaleY,
        Entity entity
    ) {
        Vector2 position = new Vector2(x, y);
        Vector2 size = new Vector2(width, height);
        Vector2 scaling = new Vector2(scaleX, scaleY);

        position.scl(GDXGame.UNIT_SCALE);
        size.scl(GDXGame.UNIT_SCALE);

        entity.add(new TransformComponent(position, z, size, scaling, 0f));
    }

    private TextureRegion getTextureRegion(TiledMapTile tile) {
        String atlasAssetStr = tile.getProperties().get("atlasAsset", AtlasAsset.OBJECTS.name(), String.class);
        AtlasAsset atlasAsset = AtlasAsset.valueOf(atlasAssetStr);
        TextureAtlas textureAtlas = assetService.get(atlasAsset);
        FileTextureData textureData = (FileTextureData)tile.getTextureRegion().getTexture().getTextureData();
        String atlasKey = textureData.getFileHandle().nameWithoutExtension();
        TextureAtlas.AtlasRegion region = textureAtlas.findRegion(atlasKey + "/" + atlasKey);
        if (region != null){
            System.out.println("Hallo");
            return region;
        }
//        return null;
        return tile.getTextureRegion();
    }
}
