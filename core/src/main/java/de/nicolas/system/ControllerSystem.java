package de.nicolas.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import de.nicolas.component.ControllerComponent;
import de.nicolas.component.MoveComponent;
import de.nicolas.input.Command;

public class ControllerSystem extends IteratingSystem {

   public ControllerSystem(){
       super(Family.all(ControllerComponent.class).get());
   }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        ControllerComponent controllerComponent = ControllerComponent.MAPPER.get(entity);
        if (controllerComponent.getPressedCommands().isEmpty() && controllerComponent.getReleasedCommands().isEmpty()){
            return;
        }

        for (Command pressedCommand : controllerComponent.getPressedCommands()){
            switch (pressedCommand){
                case UP -> moveEntity(entity, 0f, 1f);
                case DOWN -> moveEntity(entity, 0f, -1f);
                case LEFT -> moveEntity(entity, -1f, 0f);
                case RIGHT -> moveEntity(entity, 1f, 0f);
            }
        }
        controllerComponent.getPressedCommands().clear();

        for (Command releasedCommand : controllerComponent.getReleasedCommands()){
            switch (releasedCommand){
                case UP -> moveEntity(entity, 0f, -1f);
                case DOWN -> moveEntity(entity, 0f, 1f);
                case LEFT -> moveEntity(entity, 1f, 0f);
                case RIGHT -> moveEntity(entity, -1f, 0f);
            }
        }
        controllerComponent.getReleasedCommands().clear();
    }

    private void moveEntity(Entity entity, float directionX, float directionY) {
        MoveComponent moveComponent = MoveComponent.MAPPER.get(entity);
        if (moveComponent == null){
            return;
        }

        moveComponent.getDirection().x += directionX;
        moveComponent.getDirection().y += directionY;
    }
}
