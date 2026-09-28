package de.nicolas.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;
import de.nicolas.input.Command;

import java.util.ArrayList;
import java.util.List;

public class ControllerComponent implements Component {

    public static final ComponentMapper<ControllerComponent> MAPPER = ComponentMapper.getFor(ControllerComponent.class);

    private final List<Command> pressedCommands;
    private final List<Command> releasedCommands;

    public ControllerComponent(){
        pressedCommands = new ArrayList<>();
        releasedCommands = new ArrayList<>();
    }

    public List<Command> getPressedCommands() {
        return pressedCommands;
    }

    public List<Command> getReleasedCommands() {
        return releasedCommands;
    }
}
