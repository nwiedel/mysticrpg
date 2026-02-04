package de.nicolas;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.viewport.Viewport;
import de.nicolas.asset.AssetService;
import de.nicolas.asset.MapAsset;

public class GameScreen extends ScreenAdapter {

    private final GDXGame game;

    private final AssetService assetService;
    private final Batch batch;
    private final Viewport viewport;
    private final OrthographicCamera camera;

    private final OrthogonalTiledMapRenderer mapRenderer;

    public GameScreen(GDXGame game){
        this.game = game;
        batch = game.getBatch();
        assetService = game.getAssetService();
        viewport = game.getViewport();
        camera = game.getCamera();

        mapRenderer = new OrthogonalTiledMapRenderer(null, GDXGame.UNIT_SCALE, batch);
    }

    @Override
    public void show() {
        assetService.load(MapAsset.Main);
        mapRenderer.setMap(assetService.get(MapAsset.Main));
    }

    @Override
    public void render(float delta) {
        viewport.apply();
        batch.setColor(Color.WHITE);
        mapRenderer.setView(camera);
        mapRenderer.render();
    }

    @Override
    public void dispose() {
        mapRenderer.dispose();
    }
}
