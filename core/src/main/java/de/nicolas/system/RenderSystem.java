package de.nicolas.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.SortedIteratingSystem;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.Viewport;
import de.nicolas.GDXGame;
import de.nicolas.component.GraphicComponent;

import java.util.Comparator;

public class RenderSystem extends SortedIteratingSystem implements Disposable {

    private final OrthogonalTiledMapRenderer mapRenderer;
    private final Batch batch;
    private final Viewport viewport;
    private final OrthographicCamera camera;

    public RenderSystem(Batch batch, Viewport viewport, OrthographicCamera camera) {
        super(
            Family.all(TransformComponent.class, GraphicComponent.class).get(),
            Comparator.comparing(TransformComponent.MAPPER::get)
        );
        this.batch = batch;
        this.viewport = viewport;
        this.camera = camera;
        camera = (OrthographicCamera) viewport.getCamera();
        mapRenderer = new OrthogonalTiledMapRenderer(null, GDXGame.UNIT_SCALE, batch);
    }

    @Override
    public void update(float deltaTime) {

        viewport.apply();
        batch.setColor(Color.WHITE);
        mapRenderer.setView(camera);
        mapRenderer.render();

        forceSort();
        super.update(deltaTime);
    }

    @Override
    protected void processEntity(Entity entity, float v) {
        TransformComponent transform = TransformComponent.MAPPER.get(entity);
        GraphicComponent graphic = GraphicComponent.MAPPER.get(entity);
        if (graphic.getRegion() == null){
            return;
        }

        Vector2 position = transform.getPosition();
        Vector2 scaling = transform.getScaling();
        Vector2 size = transform.getSize();
        batch.setColor(graphic.getColor());
        batch.draw(
            graphic.getRegion(),
            position.x - (1f - scaling.x) * size.x * 0.5f,
            position.y - (1f - scaling.y) * size.y * 0.5f,
            size.x * 0.5f, size.y * 0.5f,
            size.x, size.y,
            scaling.x, scaling.y,
            transform.getRotationDeg()
        );
    }

    public void setMap(TiledMap tiledMap){

        mapRenderer.setMap(tiledMap);
    }

    @Override
    public void dispose() {

        mapRenderer.dispose();
    }
}
