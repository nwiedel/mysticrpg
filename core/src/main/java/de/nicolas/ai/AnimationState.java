package de.nicolas.ai;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.ai.fsm.State;
import com.badlogic.gdx.ai.msg.Telegram;
import de.nicolas.component.AnimationComponent;
import de.nicolas.component.FsmComponent;
import de.nicolas.component.MoveComponent;

public enum AnimationState implements State<Entity> {
    IDLE{
        @Override
        public void enter(Entity entity) {
            AnimationComponent.MAPPER.get(entity).setType(AnimationComponent.AnimationType.IDLE);
        }

        @Override
        public void update(Entity entity) {
            MoveComponent moveComponent = MoveComponent.MAPPER.get(entity);
            if (moveComponent != null && !moveComponent.isRooted() && !moveComponent.getDirection().isZero()){
                FsmComponent.MAPPER.get(entity).getAnimationFsm().changeState(WALK);
                return;
            }

            // ...
        }

        @Override
        public void exit(Entity entity) {

        }

        @Override
        public boolean onMessage(Entity entity, Telegram telegram) {
            return false;
        }
    },
    WALK{
        @Override
        public void enter(Entity entity) {
            AnimationComponent.MAPPER.get(entity).setType(AnimationComponent.AnimationType.WALK);
        }

        @Override
        public void update(Entity entity) {
            MoveComponent moveComponent = MoveComponent.MAPPER.get(entity);
            if (moveComponent == null || moveComponent.getDirection().isZero() || moveComponent.isRooted()){
                FsmComponent.MAPPER.get(entity).getAnimationFsm().changeState(IDLE);
            }

            //...
        }

        @Override
        public void exit(Entity entity) {

        }

        @Override
        public boolean onMessage(Entity entity, Telegram telegram) {
            return false;
        }
    }

}
