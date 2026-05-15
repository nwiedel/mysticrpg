package de.nicolas;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.profiling.GLProfiler;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.Viewport;
import de.nicolas.asset.AssetService;
import de.nicolas.asset.MapAsset;

public class GameScreen extends ScreenAdapter {

    private final GDXGame game;

    private final AssetService assetService;
    private final Batch batch;
    private final Viewport viewport;
    private final OrthographicCamera camera;
    private final Engine engine;

    private final OrthogonalTiledMapRenderer mapRenderer;

    public GameScreen(GDXGame game){
        this.game = game;
        batch = game.getBatch();
        assetService = game.getAssetService();
        viewport = game.getViewport();
        camera = game.getCamera();
        engine = new Engine();

        engine.addSystem(new RenderSystem(batch, viewport, assetService));

        mapRenderer = new OrthogonalTiledMapRenderer(null, GDXGame.UNIT_SCALE, batch);
    }

    @Override
    public void show() {
        assetService.load(MapAsset.Main);
        mapRenderer.setMap(assetService.get(MapAsset.Main));
    }

    @Override
    public void hide() {
        engine.removeAllEntities();
    }

    @Override
    public void render(float delta) {
        delta = Math.min(delta, 1 / 30f);
        engine.update(delta);

        viewport.apply();
        batch.setColor(Color.WHITE);
        mapRenderer.setView(camera);
        mapRenderer.render();
    }

    @Override
    public void dispose() {
        for (EntitySystem system : engine.getSystems()){
            if (system instanceof Disposable disposableSystem){
                disposableSystem.dispose();
            }
        }

        mapRenderer.dispose();
    }
}
