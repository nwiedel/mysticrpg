package de.nicolas.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector2;
import de.nicolas.component.MoveComponent;
import de.nicolas.component.TransformComponent;

public class MoveSystem extends IteratingSystem {

    private final Vector2 normalizedDirection = new Vector2();

    public MoveSystem(){
        super(Family.all(MoveComponent.class, TransformComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        MoveComponent moveComponent = MoveComponent.MAPPER.get(entity);
        if (moveComponent.isRotated() || moveComponent.getDirection().isZero()){
            return;
        }

        normalizedDirection.set(moveComponent.getDirection()).nor();
        TransformComponent transformComponent = TransformComponent.MAPPER.get(entity);
        Vector2 position = transformComponent.getPosition();
        transformComponent.getPosition().set(
            position.x + moveComponent.getMaxSpeed() * normalizedDirection.x * deltaTime,
            position.y + moveComponent.getMaxSpeed() * normalizedDirection.y * deltaTime
        );
    }
}
