package de.nicolas.system;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.gdx.math.Vector2;

public class TransformComponent implements Component, Comparable<TransformComponent> {
    public static final ComponentMapper<TransformComponent> MAPPER = ComponentMapper.getFor(TransformComponent.class);

    private final Vector2 position;
    private final int z;
    private final Vector2 size;
    private final Vector2 scaling;
    private float rotationDeg;

    public TransformComponent(
        Vector2 position,
        int z,
        Vector2 size,
        Vector2 scaling,
        float rotationDeg
    ){
        this.position = position;
        this.z = z;
        this.size = size;
        this.scaling = scaling;
        this.rotationDeg = rotationDeg;
    }

    @Override
    public int compareTo(TransformComponent other) {
        if (this.z!= other.z){
            return Float.compare(this.z, other.z);
        }
        if (this.position.y != other.position.y){
            return Float.compare(this.position.y, other.position.y);
        }
        return Float.compare(this.position.x, this.position.x);
    }

    public Vector2 getPosition() {
        return position;
    }

    public Vector2 getSize() {
        return size;
    }

    public Vector2 getScaling() {
        return scaling;
    }

    public float getRotationDeg() {
        return rotationDeg;
    }
}
