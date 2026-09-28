package de.nicolas.input;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import de.nicolas.component.ControllerComponent;

public class GameControllerState implements ControllerState {

    private final ImmutableArray<Entity> controllerEntities;

    public GameControllerState(Engine engine) {
        this.controllerEntities = engine.getEntitiesFor(Family.all(ControllerComponent.class).get());
    }

    @Override
    public void keyDown(Command command) {
        for (Entity entity : controllerEntities){
            ControllerComponent.MAPPER.get(entity).getPressedCommands().add(command);
        }
    }

    @Override
    public void keyUp(Command command) {
        for (Entity entity : controllerEntities){
            ControllerComponent.MAPPER.get(entity).getReleasedCommands().add(command);
        }
    }
}
