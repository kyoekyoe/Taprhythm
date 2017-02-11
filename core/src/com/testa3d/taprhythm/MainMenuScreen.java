package com.testa3d.taprhythm;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;

/**
 * Created by Test A 3D on 2017/01/29.
 */
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.FitViewport;


import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Random;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.delay;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.run;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;


public class MainMenuScreen implements Screen {

    final taprhythm game;
    private static final class ScoreText extends Actor {
        String text = "";
        BitmapFont font = new BitmapFont(Gdx.files.internal("m_plus_1p.fnt"));

        @Override
        public void draw(Batch batch, float parentAlpha) {
            super.draw(batch, parentAlpha);
            font.draw(batch, text, getX(), getY());
        }
    }
    private ScoreText scoreText;
    private ScoreText dot;
    private float score;
    private String one = "0";
    private String two = "0";
    private String three = "0";
    private String four = "0";
    private String five = "0";
    OrthographicCamera camera;
    private double dtokuten;
    private String escore;
    private Stage stage;
    private Image im1;
    private Image im2;
    private Image im3;
    private Image im4;
    private Image im5;
    private Sound roll;
    int onrandom = 0;
    Random rnd = new Random();
    public MainMenuScreen(final taprhythm game) {
        this.game = game;
        escore ="";
        stage = new Stage(new FitViewport(1920,1080));  //ステージ作成処理
        im1 = new Image(new Texture(Gdx.files.internal("blue"+one+".png")));
        im2 = new Image(new Texture(Gdx.files.internal("blue"+two+".png")));
        im3 = new Image(new Texture(Gdx.files.internal("red"+three+".png")));
        im4 = new Image(new Texture(Gdx.files.internal("red"+four+".png")));
        im5 = new Image(new Texture(Gdx.files.internal("red"+five+".png")));
        im1.setPosition(stage.getWidth() * 0.1f,stage.getHeight() / 2);
        im2.setPosition(stage.getWidth() * 0.25f,stage.getHeight() / 2);
        im3.setPosition(stage.getWidth() * 0.55f,stage.getHeight() / 2);
        im4.setPosition(stage.getWidth() * 0.65f,stage.getHeight() / 2);
        im5.setPosition(stage.getWidth() * 0.7f,stage.getHeight() / 2);
        stage.addActor(im1);
        stage.addActor(im2);
        stage.addActor(im3);
        stage.addActor(im4);
        stage.addActor(im5);
        scoreText = new ScoreText();
        scoreText.text = "Your Score is ...";
        scoreText.setPosition(150, stage.getHeight() - 40);
        stage.addActor(scoreText);
        dot = new ScoreText();
        dot.text = ".";
        dot.setPosition(stage.getWidth() * 0.31f,stage.getHeight() / 2+42);
        stage.addActor(dot);
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
        if(onrandom == 5){
            one = String.valueOf(rnd.nextInt(10));
            two = String.valueOf(rnd.nextInt(10));
            three = String.valueOf(rnd.nextInt(10));
            four = String.valueOf(rnd.nextInt(10));
              five = String.valueOf(rnd.nextInt(10));
        }
        if(onrandom == 4){
            one = String.valueOf(rnd.nextInt(10));
            two = String.valueOf(rnd.nextInt(10));
            three = String.valueOf(rnd.nextInt(10));
            four = String.valueOf(rnd.nextInt(10));
          //  five = String.valueOf(rnd.nextInt(10));
        }
        if(onrandom == 3){
            one = String.valueOf(rnd.nextInt(10));
            two = String.valueOf(rnd.nextInt(10));
            three = String.valueOf(rnd.nextInt(10));
           // four = String.valueOf(rnd.nextInt(10));
            //  five = String.valueOf(rnd.nextInt(10));
        }
        if(onrandom == 2){
            one = String.valueOf(rnd.nextInt(10));
            two = String.valueOf(rnd.nextInt(10));
           // three = String.valueOf(rnd.nextInt(10));
           // four = String.valueOf(rnd.nextInt(10));
            //  five = String.valueOf(rnd.nextInt(10));
        }

        im1.remove();
        im2.remove();
        im3.remove();
        im4.remove();
        im5.remove();
        im1 = new Image(new Texture(Gdx.files.internal("blue"+one+".png")));
        im2 = new Image(new Texture(Gdx.files.internal("blue"+two+".png")));
        im3 = new Image(new Texture(Gdx.files.internal("red"+three+".png")));
        im4 = new Image(new Texture(Gdx.files.internal("red"+four+".png")));
        im5 = new Image(new Texture(Gdx.files.internal("red"+five+".png")));
        im1.setPosition(stage.getWidth() * 0.1f,stage.getHeight() / 2);
        im2.setPosition(stage.getWidth() * 0.2f,stage.getHeight() / 2);
        im3.setPosition(stage.getWidth() * 0.35f,stage.getHeight() / 2);
        im4.setPosition(stage.getWidth() * 0.45f,stage.getHeight() / 2);
        im5.setPosition(stage.getWidth() * 0.55f,stage.getHeight() / 2);
        stage.addActor(im1);
        stage.addActor(im2);
        stage.addActor(im3);
        stage.addActor(im4);
        stage.addActor(im5);
        stage.act(Gdx.graphics.getDeltaTime());     // ステージの状態を前回render呼び出しからの経過時間(delta time)分だけ更新する
        stage.draw();
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
        roll = Gdx.audio.newSound(Gdx.files.internal("roll.ogg"));//ロールの音色定義

        dtokuten = score;
        onrandom = 5;
        roll.play();
   stage.addAction(randomend);

    }
    private final Action randomend = sequence(
            delay(2),
            run(new Runnable() {
                @Override
                public void run() {
                    onrandom = 4;
                    settokuten();
                }
                }),
            delay(0.5f),
            run(new Runnable() {
                @Override
                public void run() {
                    onrandom = 3;
                    settokuten();
                }
            }),            delay(0.5f),
            run(new Runnable() {
                @Override
                public void run() {
                    onrandom = 2;
                    settokuten();
                }
            }),     delay(1.8f),
            run(new Runnable() {
                @Override
                public void run() {
                    onrandom = 1;
                    settokuten();
                }
            }));
    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
