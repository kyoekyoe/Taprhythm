package com.testa3d.taprhythm;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;

/**
 * Created by Test A 3D on 2017/01/29.
 */
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;


import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
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
   private float score;
    private Image back;
    private float backhiscore;
    private float hiscore;
    private Preferences prefs = Gdx.app.getPreferences("com.testa3d.taprhythm");
    private Image exit;
    private int one = 0;
    private int two = 0;
    private int three = 0;
    private int four = 0;
    private int five = 0;
    OrthographicCamera camera;
    private double dtokuten;
    private String escore;
    private Stage stage;
    private Image im1;
    private Image im2;
    private Image im3;
    private Image im4;
    private Image im5;
    private ScoreText hiscoretext;
    private Image YourScore;
    private Image bestscore;

    private Image dot;
    private Sound roll;
    private Image sclabel;
  //  int onrandom = 0;
  private Array<SpriteDrawable> blueDigits = new Array<SpriteDrawable>();
    private Array<SpriteDrawable> redDigits = new Array<SpriteDrawable>();

    Random rnd = new Random();
    private Boolean isRollPlaying = false;
    private float currentTime = 0;
    private int lastOnRandom = -1;
    public MainMenuScreen(final taprhythm game) {
    //    roll = Gdx.audio.newSound(Gdx.files.internal("roll.ogg"));//ロールの音色定義
      //roll.play();
        this.game = game;
        //-----------------ハイスコア計算
     /*   float hiscoress = prefs.getFloat("hiscore",120);
        if(hiscoress == 120){
            prefs.putFloat("hiscore",0.000f);
            prefs.flush();
        }

        backhiscore = prefs.getFloat("hiscore");
        prefs.flush();
        hiscore = backhiscore;
        if(dtokuten > hiscore){

            hiscore = (float)dtokuten;
            prefs.putFloat("hiscore",(float)dtokuten);
            prefs.flush();
        }

*/
        //-----------------ハイスコア計算
        bestscore = new Image(new Texture(Gdx.files.internal("BestScore.png")));
        exit = new Image(new Texture(Gdx.files.internal("Exit.png")));
        escore ="";
        stage = new Stage(new FitViewport(1920,1080));  //ステージ作成処理
        for (int i = 0; i < 10; i++) {
            blueDigits.add(new SpriteDrawable(new Sprite(new Texture(Gdx.files.internal("blue"+i+".png")))));
        }
        for (int i = 0; i < 10; i++) {
            redDigits.add(new SpriteDrawable(new Sprite(new Texture(Gdx.files.internal("red"+i+".png")))));
        }
        Gdx.input.setInputProcessor(stage);//ステージのリスナー作成
        exit.setPosition(stage.getWidth() * 0.8f,stage.getHeight() * 0.15f);
        exit.setScale(1.55f);
        exit.setOrigin(exit.getWidth()/2,exit.getHeight()/2);
        stage.addActor(exit);
        exit.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                exit.setScale(1.3f);
                return true;
            }
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                exit.setScale(1.55f);
                Gdx.app.exit();
            }
        });
        im1 = new Image(blueDigits.get(0));
        im2 = new Image(blueDigits.get(0));
        im3 = new Image(redDigits.get(0));
        im4 = new Image(redDigits.get(0));
        im5 = new Image(redDigits.get(0));
        YourScore = new Image(new Texture(Gdx.files.internal("YourScoreis.png")));
        dot = new Image(new Texture(Gdx.files.internal("Point.png")));
        bestscore = new Image(new Texture(Gdx.files.internal("BestScore.png")));
        back = new Image(new Texture(Gdx.files.internal("Replay.png")));
        sclabel = new Image(new Texture(Gdx.files.internal("Score.png")));
        im1.setPosition(stage.getWidth() * 0.1f,stage.getHeight() / 2);
        im2.setPosition(stage.getWidth() * 0.2f,stage.getHeight() / 2);
        im3.setPosition(stage.getWidth() * 0.35f,stage.getHeight() / 2);
        im4.setPosition(stage.getWidth() * 0.45f,stage.getHeight() / 2);
        im5.setPosition(stage.getWidth() * 0.55f,stage.getHeight() / 2);
        back.setPosition(stage.getWidth() * 0.55f,stage.getHeight() * 0.15f);
        back.setScale(2);
        back.setOrigin( back.getWidth() / 2 ,  back.getHeight() / 2);

        sclabel.setPosition(stage.getWidth() * 0.65f,stage.getHeight() / 2 - 50);
        stage.addActor(im1);
        stage.addActor(im2);
        stage.addActor(im3);
        stage.addActor(im4);
        stage.addActor(im5);
        sclabel.setScale(2);

  //      stage.addActor(sclabel);

      YourScore.setPosition(200, stage.getHeight() - 300);
        bestscore.setPosition(200, /*stage.getHeight() - 300 - */stage.getHeight()*0.25f);
        bestscore.setScale(2);
        stage.addActor(bestscore);
        YourScore.setScale(2);
        stage.addActor(YourScore);
        dot.setPosition(stage.getWidth() * 0.31f,stage.getHeight() / 2+42);
        stage.addActor(dot);
        roll = Gdx.audio.newSound(Gdx.files.internal("roll.mp3"));//ロールの音色定義
        stage.addActor(back);
        back.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                back.setScale(1.8f);
                return true;
            }
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                back.setScale(2);
                GameScreen screen = new GameScreen(game);
                game.setScreen(screen);
            }
        });

    }
    public void settokuten(){

        DecimalFormat exFormat1 = new DecimalFormat("00.000");
        escore = exFormat1.format(dtokuten);
        escore = escore.replace(".", "");

        Gdx.app.log("TapRhythm:MainMenuScore",escore);
        one = Integer.valueOf(escore.substring(0,1));
        two = Integer.valueOf(escore.substring(1,2));
        three = Integer.valueOf(escore.substring(2,3));
        four = Integer.valueOf(escore.substring(3,4));
        five = Integer.valueOf(escore.substring(4,5));
        Gdx.app.log("TapRhythm:one","" + one);
        Gdx.app.log("TapRhythm:two","" + two);
        Gdx.app.log("TapRhythm:three","" + three);
        Gdx.app.log("TapRhythm:four","" + four);
        Gdx.app.log("TapRhythm:five","" + five);
}

    @Override//
    public void render(float delta) {
        currentTime += delta;
        //Gdx.app.log("debug", "" + currentTime);

        Gdx.gl.glClearColor(255 / 255.f, 255 / 255.f, 255 / 255.f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if (!isRollPlaying) {
            // 音の読み込みが完了しておらず、正常に再生出来ない可能性がある(その場合、play()の戻り値が-1)ので、
            // 正常に再生できたかチェックする
            if (roll.play() != -1) {
              //  roll.play();
                isRollPlaying = true;
            }
        }
        int onrandom = getPhase(currentTime);
        // 前回からフェーズが変わったら、一旦得点を表示し直す
        if (lastOnRandom != onrandom) {
            settokuten();
            lastOnRandom = onrandom;
        }
        if(onrandom == 5){
            one = rnd.nextInt(9);
            two = rnd.nextInt(9);
            three = rnd.nextInt(9);
            four = rnd.nextInt(9);
            five = rnd.nextInt(9);
        }
        if(onrandom == 4) {
            one = rnd.nextInt(9);
            two = rnd.nextInt(9);
            three = rnd.nextInt(9);
            four = rnd.nextInt(9);
            //five = rnd.nextInt(9);
        }
        if(onrandom == 3){
            one = rnd.nextInt(9);
            two = rnd.nextInt(9);
            three = rnd.nextInt(9);
            //four = rnd.nextInt(9);
            //five = rnd.nextInt(9);
        }
        if(onrandom == 2){
            one = rnd.nextInt(9);
            two = rnd.nextInt(9);
            //three = rnd.nextInt(9);
            //four = rnd.nextInt(9);
            //five = rnd.nextInt(9);
        }
        if(onrandom < 2){
            hiscoretext.text =hiscore + "点";
        }
        im1.setDrawable(blueDigits.get(one));
        im2.setDrawable(blueDigits.get(two));
        im3.setDrawable(redDigits.get(three));
        im4.setDrawable(redDigits.get(four));
        im5.setDrawable(redDigits.get(five));
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


        dtokuten = score;
        float hiscoress = prefs.getFloat("hiscore",120);
        //if(hiscoress == 120){
            prefs.putFloat("hiscore",0.000f);
            prefs.flush();
     //   }

        backhiscore = prefs.getFloat("hiscore");
        prefs.flush();
        hiscore = backhiscore;
        if(score > hiscore){

            hiscore = score;
            prefs.putFloat("hiscore",score);
            prefs.flush();
        }
        hiscoretext = new ScoreText();
        hiscoretext.text =backhiscore + "点";
        hiscoretext.setPosition(bestscore.getX(), bestscore.getY());
        stage.addActor(hiscoretext);
    }

    // 経過時間に対して、スコアの表示フェーズを返す
    private int getPhase(float duration) {
        if (duration < 2) {
            return 5;
        } else if (duration < 2.5f) {
            return 4;
        } else if (duration < 3.0f) {
            return 3;
        } else if (duration < 3.8f) {
            return 2;
        } else {
            return 1;
        }
    }
    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
