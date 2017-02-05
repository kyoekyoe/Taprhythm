package com.testa3d.taprhythm;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.testa3d.taprhythm.MainMenuScreen;

public class taprhythm extends Game {


	public void create() {

		this.setScreen(new GameScreen(this));
	}

	public void render() {
		super.render(); // 重要！ 書き忘れ注意
	}

	public void dispose() {

	}
}