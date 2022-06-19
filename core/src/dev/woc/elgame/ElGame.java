package dev.woc.elgame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.utils.ScreenUtils;

public class ElGame extends Game {
	@Override
	public void create () {

	}

	@Override
	public void render () {
		ScreenUtils.clear(1, 0, 0, 1);
		super.render();
	}
}
