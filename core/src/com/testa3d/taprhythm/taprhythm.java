package com.testa3d.taprhythm;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.testa3d.taprhythm.MainMenuScreen;

public class taprhythm extends Game {

	public SpriteBatch batch;
	public BitmapFont font;

	public void create() {
		batch = new SpriteBatch();

		// LibGDXのデフォルトフォント(Arial)を利用します
		font = new BitmapFont();
		this.setScreen(new MainMenuScreen(this));
	}

	public void render() {
		super.render(); // 重要！ 書き忘れ注意
	}

	public void dispose() {
		// 読み込んだリソースを破棄します
		batch.dispose();
		font.dispose();
	}
}