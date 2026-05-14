package de.nicolas;

import com.badlogic.gdx.*;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.profiling.GLProfiler;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import de.nicolas.asset.AssetService;

import java.util.HashMap;
import java.util.Map;

public class GDXGame extends Game {

    /** Spielewelt größe und die größe einer Einheit*/
    public static final float WORLD_WIDTH = 16f;
    public static final float WORLD_HEIGHT = 9f;
    public static final float UNIT_SCALE = 1f / 16f;

    /** unser Pinsel */
    private Batch batch;

    /** Kameravariablen */
    private OrthographicCamera camera;
    private Viewport viewport;

    /** Asset Management */
    private AssetService assetService;

    /** Überwachung für unser Program */
    private GLProfiler glProfiler;
    private FPSLogger fpsLogger;

    /** Liste, die die im Spiel existierenden Screen beinhaltet */
    private final Map<Class<? extends Screen>, Screen> screenCache = new HashMap<>();

    @Override
    public void create() {
        Gdx.app.setLogLevel(Application.LOG_DEBUG);

        batch = new SpriteBatch();

        camera = new OrthographicCamera();
        viewport = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);

        assetService = new AssetService(new InternalFileHandleResolver());

        glProfiler = new GLProfiler(Gdx.graphics);
        glProfiler.enable();
        fpsLogger = new FPSLogger();

        addScreen(new GameScreen(this));
        setScreen(GameScreen.class);
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        super.resize(width, height);
    }

    /** Methode, mit der man Screens zur Map hinzufügen kann */
    public void addScreen(Screen screen){
        if (screenCache.containsValue(screen)){
            return;
        }
        screenCache.put(screen.getClass(), screen);
    }

    /** abgewandelte setScreen-Methode die sich den entsprechenden Screen aus der Map holt */
    public void setScreen(Class<? extends Screen> screenClass){
        Screen screen = screenCache.get(screenClass);
        if (screen == null){
            throw new GdxRuntimeException("No screen with class " + screenClass + "found in the screen cache.");
        }
        super.setScreen(screen);
    }

    @Override
    public void render() {
        glProfiler.reset();

        ScreenUtils.clear(0f, 0f,0f, 1f);

        super.render();

        Gdx.graphics.setTitle("Mystic Tutorial - Draw Calls: " + glProfiler.getDrawCalls());
        fpsLogger.log();
    }

    @Override
    public void dispose() {
        screenCache.values().forEach(Screen::dispose);
        screenCache.clear();

        batch.dispose();

        assetService.debugDiagnostics();
        assetService.dispose();
    }

    /** Getter und Setter */
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
