package de.nicolas.asset;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.utils.Disposable;

public class AssetService implements Disposable {

    private AssetManager assetManager;

    public AssetService(FileHandleResolver fileHandleResolver){

        assetManager = new AssetManager(fileHandleResolver);
        assetManager.setLoader(TiledMap.class, new TmxMapLoader());
    }

    public <T> T load(Asset<T> asset){
        assetManager.load(asset.getDescriptor());
        assetManager.finishLoading();
        return assetManager.get(asset.getDescriptor());
    }

    public <T> void queue(Asset<T> asset){
        assetManager.load(asset.getDescriptor());
    }

    public <T> T get(Asset<T> asset){
        return assetManager.get(asset.getDescriptor());
    }

    public boolean update(){
        return assetManager.update();
    }

    public void debugDiagnostics(){
        Gdx.app.debug("AssetService", assetManager.getDiagnostics());
    }

    @Override
    public void dispose() {
        assetManager.dispose();
    }
}
