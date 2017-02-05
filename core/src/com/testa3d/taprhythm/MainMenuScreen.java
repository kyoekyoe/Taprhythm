package com.testa3d.taprhythm;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;

/**
 * Created by Test A 3D on 2017/01/29.
 */
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;


public class MainMenuScreen implements Screen {

    final taprhythm game;
    private float score;

    OrthographicCamera camera;

    public MainMenuScreen(final taprhythm game) {
        this.game = game;


    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(255 / 255.f, 255 / 255.f, 255 / 255.f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

    }

    @Override
    public void resize(int width, int height) {

    }
    public void setGscore(float gscore){
        score = gscore;
    }
    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void show() {
        Gdx.app.log("TapRhythm:MainMenuScore",String.valueOf(score));
    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
