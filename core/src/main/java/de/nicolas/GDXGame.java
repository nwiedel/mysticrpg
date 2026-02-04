package de.nicolas;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import de.nicolas.asset.AssetService;

import java.util.HashMap;
import java.util.Map;

public class GDXGame extends Game {

    public static final float WORLD_WIDTH = 16f;
    public static final float WORLD_HEIGHT = 9f;
    public static final float UNIT_SCALE = 1f / 16f;

    private Batch batch;

    private OrthographicCamera camera;
    private Viewport viewport;

    private AssetService assetService;

    private final Map<Class<? extends Screen>, Screen> screenCache = new HashMap<>();

    @Override
    public void create() {

        batch = new SpriteBatch();

        camera = new OrthographicCamera();
        viewport = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);

        assetService = new AssetService(new InternalFileHandleResolver());

        addScreen(new GameScreen(this));
        setScreen(GameScreen.class);
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        super.resize(width, height);
    }

    public void addScreen(Screen screen){
        if (screenCache.containsValue(screen)){
            return;
        }
        screenCache.put(screen.getClass(), screen);
    }

    public void setScreen(Class<? extends Screen> screenClass){
        Screen screen = screenCache.get(screenClass);
        if (screen == null){
            throw new GdxRuntimeException("No screen with class " + screenClass + "found in the screen cache.");
        }
        super.setScreen(screen);
    }

    @Override
    public void dispose() {
        screenCache.values().forEach(Screen::dispose);
        screenCache.clear();

        batch.dispose();

        assetService.debugDiagnostics();
        assetService.dispose();
    }

    public Batch getBatch() {
        return batch;
    }

    public AssetService getAssetService() {
        return assetService;
    }

    public Viewport getViewport() {
        return viewport;
    }

    public OrthographicCamera getCamera() {
        return camera;
    }
}
