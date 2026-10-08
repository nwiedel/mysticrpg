package de.nicolas.component;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.ai.fsm.DefaultStateMachine;
import de.nicolas.ai.AnimationState;

/** FinalStateMachine */
public class FsmComponent implements com.badlogic.ashley.core.Component {

    public static final ComponentMapper<FsmComponent> MAPPER = ComponentMapper.getFor(FsmComponent.class);

    private final DefaultStateMachine<Entity, AnimationState> animationFsm;

    public FsmComponent(Entity owner){
        animationFsm = new DefaultStateMachine<>(owner, AnimationState.IDLE);
    }

    public DefaultStateMachine<Entity, AnimationState> getAnimationFsm() {
        return animationFsm;
    }

}
