package de.nicolas.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector2;
import de.nicolas.component.FacingComponent;
import de.nicolas.component.MoveComponent;

import static de.nicolas.component.FacingComponent.*;

public class FacingSystem extends IteratingSystem {

    public FacingSystem(){
        super(Family.all(FacingComponent.class, MoveComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        MoveComponent moveComponent = MoveComponent.MAPPER.get(entity);
        Vector2 moveDirection = moveComponent.getDirection();
        if (moveDirection.isZero()){
            return;
        }

        FacingComponent facingComponent = MAPPER.get(entity);
        if (moveDirection.y > 0){
            facingComponent.setDirection(FacingDirection.UP);
        }
        else if (moveDirection.y < 0){
            facingComponent.setDirection(FacingDirection.DOWN);
        }
        else if (moveDirection.x > 0){
            facingComponent.setDirection(FacingDirection.RIGHT);
        }
        else {
            facingComponent.setDirection(FacingDirection.LEFT);
        }
    }
}
