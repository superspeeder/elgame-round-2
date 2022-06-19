package dev.woc.elgame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.utils.ScreenUtils;
import dev.woc.elgame.render.AtlasSystem;
import dev.woc.elgame.screens.GameScreen;

public class ElGame extends Game {
	private GameScreen gameScreen;

	@Override
	public void create () {
		gameScreen = new GameScreen(this);
		setScreen(gameScreen);
	}

	@Override
	public void render () {
		ScreenUtils.clear(1, 0, 0, 1);
		super.render();
	}
}
