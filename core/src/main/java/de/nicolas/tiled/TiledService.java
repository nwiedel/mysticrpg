package de.nicolas.tiled;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.objects.TiledMapTileMapObject;
import com.badlogic.gdx.utils.GdxRuntimeException;
import de.nicolas.asset.AssetService;
import de.nicolas.asset.MapAsset;

import java.util.function.Consumer;

public class TiledService {

    private final AssetService assetService;

    private TiledMap currentMap;

    private Consumer<TiledMap> mapChangeConsumer;
    private Consumer<TiledMapTileMapObject>loadObjectConsumer;

    public TiledService(AssetService assetService) {
        this.assetService = assetService;
        mapChangeConsumer = null;
        loadObjectConsumer = null;
        currentMap = null;
    }

    public TiledMap loadMap(MapAsset mapAsset){
        TiledMap tiledMap = assetService.load(mapAsset);
        tiledMap.getProperties().put("mapAsset", mapAsset);
        return tiledMap;
    }

    public void setMap(TiledMap map){
        if (currentMap != null){
            assetService.unload(currentMap.getProperties().get("mapAsset", MapAsset.class));
        }
        currentMap = map;
        loadMapObjects(map);
        if (mapChangeConsumer != null){
            mapChangeConsumer.accept(map);
        }
    }

    private void loadMapObjects(TiledMap tiledMap){
        for (MapLayer layer : tiledMap.getLayers()){
            if ("objects".equals(layer.getName())){
                loadObjectLayer(layer);
            }
        }
    }

    private void loadObjectLayer(MapLayer objectLayer){
        if (loadObjectConsumer == null){
            return;
        }

        for (MapObject mapObject : objectLayer.getObjects()){
            if (mapObject instanceof TiledMapTileMapObject tiledMaObject){
                loadObjectConsumer.accept(tiledMaObject);
            } else {
                throw new GdxRuntimeException("Unsupported object: "
                    + mapObject.getClass().getSimpleName());
            }
        }
    }

    public void setMapChangeConsumer(Consumer<TiledMap> mapChangeConsumer) {
        this.mapChangeConsumer = mapChangeConsumer;
    }

    public void setLoadObjectConsumer(Consumer<TiledMapTileMapObject> loadObjectConsumer) {
        this.loadObjectConsumer = loadObjectConsumer;
    }
}
