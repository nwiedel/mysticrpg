package de.nicolas.system;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.World;
import de.nicolas.component.PhysicComponent;
import de.nicolas.component.TransformComponent;

public class PhysicSystem extends IteratingSystem implements EntityListener {

    private final World world;
    private final float interval;
    private float accumulator;

    public PhysicSystem(World world, float interval) {
        super(Family.all(PhysicComponent.class, TransformComponent.class).get());
        this.world = world;
        this.interval = interval;
        accumulator = 0f;
    }

    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);
        engine.addEntityListener(getFamily(), this);
    }

    @Override
    public void removedFromEngine(Engine engine) {
        super.removedFromEngine(engine);
        engine.removeEntityListener(this);
    }

    @Override
    public void entityAdded(Entity entity) {}

    @Override
    public void entityRemoved(Entity entity) {
        PhysicComponent physicComponent = PhysicComponent.MAPPER.get(entity);
        if (physicComponent != null){
            world.destroyBody(physicComponent.getBody());
        }

    }

    @Override
    public void update(float deltaTime) {
        accumulator += deltaTime;

        while (accumulator >= interval){
            accumulator -= accumulator;
            super.update(deltaTime);
            world.step(interval, 6, 2);
        }
        world.clearForces();

        // interpolation
        float alpha = accumulator / interval;
        for (int i = 0; i < getEntities().size(); i++){
            interpolateEntity(getEntities().get(i), alpha);
        }
    }

    @Override
    protected void processEntity(Entity entity, float v) {
        PhysicComponent physicComponent = PhysicComponent.MAPPER.get(entity);
        physicComponent.getPrevPosition().set(physicComponent.getBody().getPosition());
    }

    private void interpolateEntity(Entity entity, float alpha) {
        TransformComponent transformComponent = TransformComponent.MAPPER.get(entity);
        PhysicComponent physicComponent = PhysicComponent.MAPPER.get(entity);

        transformComponent.getPosition().set(
            MathUtils.lerp(physicComponent.getPrevPosition().x, physicComponent.getBody().getPosition().x, alpha),
            MathUtils.lerp(physicComponent.getPrevPosition().y, physicComponent.getBody().getPosition().y, alpha)
        );
    }
}
