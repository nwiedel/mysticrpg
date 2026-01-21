package de.nicolas;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;

import java.util.HashMap;
import java.util.Map;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class GDXGame extends Game {

    private final Map<Class<? extends Screen>, Screen> screenCache = new HashMap<>();

    @Override
    public void create() {
        setScreen(new FirstScreen());
    }

    public void addScreen(Screen screen){
        if (screenCache.containsValue(screen)){
            return;
        }
        screenCache.put(screen.getClass(), screen);
    }
}
