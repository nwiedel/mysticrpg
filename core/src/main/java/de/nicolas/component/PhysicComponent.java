package de.nicolas.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

public class PhysicComponent implements Component {

    public static final ComponentMapper<PhysicComponent> MAPPER = ComponentMapper.getFor(PhysicComponent.class);

    private final Body body;
    private final Vector2 prevPosition;

    public PhysicComponent(Body body, Vector2 prevPosition) {
        this.body = body;
        this.prevPosition = prevPosition;
    }

    public Body getBody() {
        return body;
    }

    public Vector2 getPrevPosition() {
        return prevPosition;
    }
}
