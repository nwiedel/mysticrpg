package de.nicolas.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import de.nicolas.GDXGame;
import de.nicolas.asset.AssetService;
import de.nicolas.asset.AtlasAsset;

public class LoadingScreen extends ScreenAdapter {

    private final GDXGame game;
    private final AssetService assetService;

    public LoadingScreen(GDXGame game, AssetService assetService) {
        this.game = game;
        this.assetService = assetService;
    }

    @Override
    public void show() {
        for (AtlasAsset atlas : AtlasAsset.values()){
            assetService.queue(atlas);
        }
    }

    @Override
    public void render(float delta) {
        if (assetService.update()){
            Gdx.app.debug("LoadingScreen", "Finished asset loading!");
            createScreens();
            game.removeScreen(this);
            dispose();
            game.setScreen(GameScreen.class);
        }
    }

    private void createScreens() {

        game.addScreen(new GameScreen(this.game));
    }
}
