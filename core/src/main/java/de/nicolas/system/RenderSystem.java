package de.nicolas.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.systems.SortedIteratingSystem;
import com.badlogic.gdx.utils.Disposable;

public class RenderSystem extends SortedIteratingSystem implements Disposable {

    @Override
    protected void processEntity(Entity entity, float v) {

    }

    @Override
    public void dispose() {

    }
}
