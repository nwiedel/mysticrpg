package de.nicolas.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import de.nicolas.component.FsmComponent;

public class FsmSystem extends IteratingSystem {

    public FsmSystem(){
        super(Family.all(FsmComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        FsmComponent.MAPPER.get(entity).getAnimationFsm().update();
    }
}
