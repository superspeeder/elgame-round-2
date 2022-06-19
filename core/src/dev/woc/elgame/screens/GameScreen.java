package dev.woc.elgame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import dev.woc.elgame.ElGame;
import dev.woc.elgame.render.AnimatedTexture;
import dev.woc.elgame.render.BaseTexture;
import dev.woc.elgame.utils.NamespacedPath;
import dev.woc.elgame.world.World;

public class GameScreen extends ScreenAdapter {
    private final ElGame game;
    private OrthographicCamera camera;
    private SpriteBatch batch = new SpriteBatch();
    private World world;
    private BaseTexture SPINNYTILE = BaseTexture.load(new NamespacedPath("base::spinny-tile"));

    public GameScreen(ElGame game) {
        this.game = game;
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
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

        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            camera.translate(0, -4);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            camera.translate(4, 0);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            camera.translate(-4, 0);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            camera.translate(0, 4);
        }
        camera.update();

        AnimatedTexture.nextFrame();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
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
