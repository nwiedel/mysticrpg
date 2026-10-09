package de.nicolas.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.physics.box2d.World;
import de.nicolas.component.TransformComponent;

public class PhysicSystem extends IteratingSystem {

    private final World world;
    private final float interval;
    private float accumulator;

    public PhysicSystem(World world, float interval) {
        super(Family.all(PhysicsComponent.class, TransformComponent.class));
        this.world = world;
        this.interval = interval;
        accumulator = 0f;
    }

    @Override
    public void update(float deltaTime) {
        accumulator += deltaTime;

        while (accumulator > interval){
            accumulator -= accumulator;
            super.update(deltaTime);
            world.step(interval, 6, 2);
        }
        world.clearForces();

        // interpolation
    }

    @Override
    protected void processEntity(Entity entity, float v) {

    }
}
