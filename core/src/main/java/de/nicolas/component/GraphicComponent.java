package de.nicolas.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class GraphicComponent implements Component {
    public static final ComponentMapper<GraphicComponent> MAPPER = ComponentMapper.getFor(GraphicComponent.class);

    private TextureRegion region;
    private final Color color;

    public GraphicComponent(Color color, TextureRegion region) {
        this.color = color;
        this.region = region;
    }

    public void setRegion(TextureRegion region) {
        this.region = region;
    }

    public TextureRegion getRegion() {
        return region;
    }

    public Color getColor() {
        return color;
    }
}
