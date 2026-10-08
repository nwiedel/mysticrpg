package de.nicolas.screen;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.Viewport;
import de.nicolas.GDXGame;
import de.nicolas.asset.AssetService;
import de.nicolas.asset.MapAsset;
import de.nicolas.input.GameControllerState;
import de.nicolas.input.KeyboardController;
import de.nicolas.system.*;
import de.nicolas.tiled.TiledAshleyConfigurator;
import de.nicolas.tiled.TiledService;

import java.util.function.Consumer;

public class GameScreen extends ScreenAdapter {

    private final GDXGame game;

    private final Engine engine;
    private final TiledService tiledService;
    private final TiledAshleyConfigurator tiledAshleyConfigurator;
    private final KeyboardController keyboardController;

    public GameScreen(GDXGame game){
        this.game = game;
        tiledService = new TiledService(game.getAssetService());
        engine = new Engine();
        tiledAshleyConfigurator = new TiledAshleyConfigurator(engine, game.getAssetService());
        keyboardController = new KeyboardController(GameControllerState.class, engine);

        engine.addSystem(new ControllerSystem());
        engine.addSystem(new MoveSystem());
        engine.addSystem(new FsmSystem());
        engine.addSystem(new FacingSystem());
        engine.addSystem(new AnimationSystem(game.getAssetService()));
        engine.addSystem(new RenderSystem(game.getBatch(), game.getViewport(), game.getCamera()));
    }

    @Override
    public void show() {
        game.setInputProcessors(keyboardController);
        keyboardController.setActiveState(GameControllerState.class);

        Consumer<TiledMap> renderConsumer = engine.getSystem(RenderSystem.class)::setMap;
        tiledService.setMapChangeConsumer(renderConsumer);
        tiledService.setLoadObjectConsumer(tiledAshleyConfigurator::onLoadObjects);

        TiledMap tiledMap = tiledService.loadMap(MapAsset.Main);
        tiledService.setMap(tiledMap);
    }

    @Override
    public void hide() {
        engine.removeAllEntities();
    }

    @Override
    public void render(float delta) {
        delta = Math.min(delta, 1 / 30f);
        engine.update(delta);


    }

    @Override
    public void dispose() {
        for (EntitySystem system : engine.getSystems()){
            if (system instanceof Disposable disposableSystem){
                disposableSystem.dispose();
            }
        }
    }
}
