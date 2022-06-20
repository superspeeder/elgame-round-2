package dev.woc.elgame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.ScreenUtils;
import dev.woc.elgame.screens.GameScreen;

public class ElGame extends Game {
	private GameScreen gameScreen;
	private static final Color SKYBLUE = new Color(0x87ceebff);

	@Override
	public void create () {
		gameScreen = new GameScreen(this);
		setScreen(gameScreen);
	}

	@Override
	public void render () {
		ScreenUtils.clear(SKYBLUE);
		super.render();
	}
}
