package dev.woc.elgame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import dev.woc.elgame.ElGame;
import dev.woc.elgame.render.AnimatedTexture;
import dev.woc.elgame.render.BaseTexture;
import dev.woc.elgame.tile.TileTypes;
import dev.woc.elgame.utils.NamespacedPath;
import dev.woc.elgame.utils.Vector2i;
import dev.woc.elgame.world.World;

public class GameScreen extends ScreenAdapter {
    private final ElGame game;
    private OrthographicCamera camera;
    private SpriteBatch batch = new SpriteBatch();
    private World world;
    private float zoom = 1.0f;

    public GameScreen(ElGame game) {
        this.game = game;
        camera = new OrthographicCamera(Gdx.graphics.getBackBufferWidth() / zoom, Gdx.graphics.getBackBufferHeight() / zoom);
        camera.setToOrtho(false);
        batch.setProjectionMatrix(camera.projection);
        world = new World();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        batch.setTransformMatrix(camera.view);
        batch.begin();
        world.render(batch, camera);
        batch.end();

        if (Gdx.input.isKeyPressed(Input.Keys.EQUALS)) {
            zoom += 0.03f;
            Vector3 p = camera.position.cpy();
            camera.setToOrtho(false, Gdx.graphics.getBackBufferWidth() / zoom, Gdx.graphics.getBackBufferHeight() / zoom);
            camera.position.set(p);
            batch.setProjectionMatrix(camera.projection);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.MINUS)) {
            zoom -= 0.03f;
            if (zoom < 0.5f) zoom = 0.5f;
            Vector3 p = camera.position.cpy();
            camera.setToOrtho(false, Gdx.graphics.getBackBufferWidth() / zoom, Gdx.graphics.getBackBufferHeight() / zoom);
            camera.position.set(p);
            batch.setProjectionMatrix(camera.projection);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            camera.translate(0, -16);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            camera.translate(16, 0);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            camera.translate(-16, 0);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            camera.translate(0, 16);
        }
        camera.update();

        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
            Vector3 mp = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            Vector3 wp = camera.unproject(mp);
            world.setTile(new Vector2i(Math.floorDiv((int) wp.x, 16), Math.floorDiv((int) wp.y, 16)), TileTypes.WOOD);
        }

        if (Gdx.input.isButtonPressed(Input.Buttons.RIGHT)) {
            Vector3 mp = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            Vector3 wp = camera.unproject(mp);
            world.setTile(new Vector2i(Math.floorDiv((int) wp.x, 16), Math.floorDiv((int) wp.y, 16)), TileTypes.AIR);
        }

        AnimatedTexture.nextFrame();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        Vector3 p = camera.position.cpy();
        camera.setToOrtho(false, width / zoom, height / zoom);
        camera.position.set(p);
        batch.setProjectionMatrix(camera.projection);
    }

    @Override
    public void show() {
        super.show();
    }

    @Override
    public void hide() {
        super.hide();
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
