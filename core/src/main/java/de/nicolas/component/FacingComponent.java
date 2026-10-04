package de.nicolas.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;

public class FacingComponent implements Component {

    public static final ComponentMapper<FacingComponent> MAPPER = ComponentMapper.getFor(FacingComponent.class);

    private FacingDirection direction;

    public FacingComponent(FacingDirection direction){
        this.direction = direction;
    }

    public FacingDirection getDirection() {
        return direction;
    }

    public void setDirection(FacingDirection direction) {
        this.direction = direction;
    }

    public enum FacingDirection{
        LEFT,
        RIGHT,
        UP,
        DOWN;

        public final String atlasKey;

        FacingDirection(){
            atlasKey = name().toLowerCase();
        }

        public String getAtlasKey(){
            return atlasKey;
        }
    }
}
