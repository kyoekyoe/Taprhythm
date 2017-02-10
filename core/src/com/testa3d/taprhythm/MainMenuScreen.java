package com.testa3d.taprhythm;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;

/**
 * Created by Test A 3D on 2017/01/29.
 */
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.FitViewport;


import java.math.BigDecimal;
import java.text.DecimalFormat;



public class MainMenuScreen implements Screen {

    final taprhythm game;
    private float score;
    private String one;
    private String two;
    private String three;
    private String four;
    private String five;
    OrthographicCamera camera;
    private double dtokuten;
    private String escore;
    private Stage stage;
    private Image im1;
    private Image im2;
    private Image im3;
    private Image im4;
    private Image im5;

    public MainMenuScreen(final taprhythm game) {
        this.game = game;
        escore ="";
    
    }
    public void settokuten(){

        DecimalFormat exFormat1 = new DecimalFormat("00.000");
        escore = exFormat1.format(dtokuten);
        escore = escore.replace(".", "");

        Gdx.app.log("TapRhythm:MainMenuScore",escore);
        one = escore.substring(0,1);
        two = escore.substring(1,2);
        three = escore.substring(2,3);
        four = escore.substring(3,4);
        five = escore.substring(4,5);
        Gdx.app.log("TapRhythm:one",one);
        Gdx.app.log("TapRhythm:two",two);
        Gdx.app.log("TapRhythm:three",three);
        Gdx.app.log("TapRhythm:four",four);
        Gdx.app.log("TapRhythm:five",five);
    }

    @Override//
    public void render(float delta) {
        Gdx.gl.glClearColor(255 / 255.f, 255 / 255.f, 255 / 255.f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage = new Stage(new FitViewport(1920,1080));  //ステージ作成処理


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
    dtokuten = score;
        settokuten();

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
