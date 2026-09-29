package de.nicolas.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.gdx.math.Vector2;

public class MoveComponent implements Component {

    public static final ComponentMapper<MoveComponent> MAPPER = ComponentMapper.getFor(MoveComponent.class);

    private float maxSpeed;
    private final Vector2 direction;
    private boolean isRotated;

    public MoveComponent(float maxSpeed){
        this.maxSpeed = maxSpeed;
        direction = new Vector2();
    }

    public float getMaxSpeed() {
        return maxSpeed;
    }

    public Vector2 getDirection() {
        return direction;
    }

    public void setRotated(boolean rotated) {
        isRotated = rotated;
    }

    public boolean isRotated() {
        return isRotated;
    }
}
