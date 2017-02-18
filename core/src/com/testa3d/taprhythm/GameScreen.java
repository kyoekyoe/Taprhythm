package com.testa3d.taprhythm;

import com.badlogic.gdx.Screen;

/**
 * Created by Test A 3D on 2017/01/29.
 */

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.StringBuilder;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.delay;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.parallel;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.repeat;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.run;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

public class GameScreen implements Screen {
    final taprhythm game;
    // スコア表示用のクラスを定義する
    //   Actorクラスを継承する
    //   Hieroで作成したビットマップフォントを読み込み、
    //   そのフォントを使ってBatchにtext変数中のテキストを書き込む
    private static final class ScoreText extends Actor {
        String text = "";
        BitmapFont font = new BitmapFont(Gdx.files.internal("m_plus_1p.fnt"));

        @Override
        public void draw(Batch batch, float parentAlpha) {
            super.draw(batch, parentAlpha);
            font.draw(batch, text, getX(), getY());
        }
    }
    private Long FinishTime;
    private ScoreText scoreText;
    private Stage stage;
    private int nowquestion = 1;
    //Randomクラスのインスタンス化
    private Image snare;
    private Image playbutton;
    private Image table;
    private boolean isFinished = false;
    private  Long starttime;//（タップ時刻計算用の基準時刻）録音をスタートしてから2000ms(メトロ4拍分)経過した時刻
    private  Long playstarttime;//（モニタ用に追加）お手本再生をスタートした時刻
    //現在の時刻　－　starttime または playstarttime で　拍数がわかる
    private List<Long> taplist = new ArrayList<Long>();
    private List<Long> diffrencelist = new ArrayList<Long>();
    private List<Integer> otehon = new ArrayList<Integer>();
    private List<Integer> minposi = new ArrayList<Integer>();
    private Music muon;
    private float k = 0.0001f; //減点係数/*//TODO:　数字が大きいほど、減点が大きくなり点数が下がる*/
    //Music -- Data
    private List<Integer> musicdata;/* = new ArrayList<Integer>(){
        {
            add(1);
            add(0);
            add(1);
            add(0);
            add(1);
            add(0);
            add(1);
            add(0);
            add(1);
            add(0);
            add(1);
            add(0);
            add(1);
            add(0);
            add(1);
            add(0);

        }
    };*/
    private List<Integer> musicdata1 = new ArrayList<Integer>(){
        {
            add(1);
            add(0);
            add(1);
            add(0);
            add(1);
            add(1);
            add(1);
            add(0);
            add(1);
            add(0);
            add(1);
            add(1);
            add(0);
            add(1);
            add(1);
            add(0);

        }
    };
    private List<Integer> musicdata2 = new ArrayList<Integer>(){
        {
            add(1);
            add(1);
            add(0);
            add(1);
            add(1);
            add(0);
            add(1);
            add(0);
            add(1);
            add(1);
            add(1);
            add(1);
            add(1);
            add(0);
            add(0);
            add(0);

        }
    };
    private List<Integer> musicdata3 = new ArrayList<Integer>(){
        {
            add(1);
            add(0);
            add(1);
            add(1);
            add(0);
            add(1);
            add(0);
            add(1);
            add(1);
            add(0);
            add(1);
            add(1);
            add(0);
            add(1);
            add(1);
            add(0);

        }
    };
    private List<Integer> musicdata4 = new ArrayList<Integer>(){
        {
            add(1);
            add(1);
            add(1);
            add(1);
            add(1);
            add(0);
            add(1);
            add(1);
            add(0);
            add(1);
            add(0);
            add(1);
            add(1);
            add(0);
            add(1);
            add(0);

        }
    };
    private List<Integer> musicdata5 = new ArrayList<Integer>(){
        {
            add(1);
            add(1);
            add(0);
            add(1);
            add(1);
            add(1);
            add(0);
            add(1);
            add(1);
            add(1);
            add(0);
            add(1);
            add(1);
            add(0);
            add(1);
            add(0);

        }
    };
    //Music-Data ** End
    private Random rnd = new Random();
    private Sound snaresound;
    private Timer timer = new Timer();
    private boolean onshow = true;
    private Sound metorotin;
    private Sound metoroka;
    private int soundcount;
    private Image recbutton;
    private Image chq;
    private Image logo;
    private boolean onrecord = false;
    private boolean onplay = false;
    private OrthographicCamera camera;
    private float scoreg = 100.00f;
    public List<Integer> ononeindex(List<Integer> inputarray) {
        List<Integer> result = new ArrayList<Integer>();
        for (int i = 0; i < inputarray.size(); i++) {
            int item = inputarray.get(i);
            if (item == 1) {
                result.add(i*250);
            }
        }
        return result;
    }
    public void saiteigi(){

    }
    public GameScreen(final taprhythm game) {
        this.game = game;

        if(nowquestion == 1){
            musicdata = new ArrayList(musicdata1);
        }else if(nowquestion == 2){
            musicdata = new ArrayList(musicdata2);
        }else if(nowquestion == 3){
            musicdata = new ArrayList(musicdata3);
        }else if(nowquestion == 4){
            musicdata = new ArrayList(musicdata4);
        }else if(nowquestion == 5){
            musicdata = new ArrayList(musicdata5);
        }
        musicdata = new ArrayList(musicdata1);
        // LibGDXのデフォルトフォント(Arial)を利用します
        // カメラを生成します
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
        //ここから定義--------------------------------------------
        stage = new Stage(new FitViewport(1920,1080));  //ステージ作成処理
        Gdx.input.setInputProcessor(stage);//ステージのリスナー作成
        snare = new Image(new Texture(Gdx.files.internal("drums.png")));//スネアの画像定義
        playbutton = new Image(new Texture(Gdx.files.internal("Listen.png")));//再生ボタンの画像定義
        recbutton = new Image(new Texture(Gdx.files.internal("Start.png")));
        snaresound = Gdx.audio.newSound(Gdx.files.internal("snare.wav"));//スネアの音色定義
        metorotin  = Gdx.audio.newSound(Gdx.files.internal("Metronome-tin.wav"));//メトロノームの音色定義
        metoroka   = Gdx.audio.newSound(Gdx.files.internal("Metronome-ka.wav"));//メトロノームの音色定義
        muon = Gdx.audio.newMusic(Gdx.files.internal("muon.ogg"));
        chq = new Image(new Texture(Gdx.files.internal("ChangeQ.png")));
        logo = new Image(new Texture(Gdx.files.internal("TapRhythm.png")));
        //ここまで定義-------------------------------------------


        //問題生成-------------------------------------
        //問題生成終了---------------------------------
        muon.setLooping(true);
        logo.setPosition(0+(logo.getWidth() / 2),stage.getHeight() - logo.getHeight() );
        logo.setOrigin(logo.getWidth()/2,logo.getHeight()/2);
        logo.setScale(1.6f);
        stage.addActor(logo);
        // 太鼓を中心の位置に配置
        snare.setOrigin(snare.getWidth()/2,snare.getHeight()/2);
        snare.setScale(2.5f);
        snare.setPosition(stage.getWidth() / 2 - snare.getWidth() /2 ,stage.getHeight() / 4 * 3 - snare.getHeight() * 0.5f);
        stage.addActor(snare);  // 太鼓をステージに追加する
        playbutton.setPosition(stage.getWidth() * 0.15f - playbutton.getWidth() * 0.5f ,stage.getHeight() / 4 * 3 - playbutton.getHeight() * 0.5f);
        playbutton.setScale(2);
        playbutton.setOrigin(playbutton.getWidth() / 2 , playbutton.getHeight() / 2);
        stage.addActor(playbutton);  //再生ボタンをステージに追加する
        chq.setScale(2);
        chq.setPosition(stage.getWidth()*0.15f,stage.getHeight() * 0.2f);
        chq.setOrigin(chq.getWidth() / 2, chq.getHeight() / 2);
        stage.addActor(chq);
        recbutton.setPosition(stage.getWidth() * 0.15f * 5.5f- recbutton.getWidth() * 0.5f ,stage.getHeight() / 4 * 3 - recbutton.getHeight() * 0.5f);
        recbutton.setScale(2);
        recbutton.setOrigin(recbutton.getWidth() / 2 , recbutton.getHeight() / 2);
        stage.addActor(recbutton);
        chq.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                chq.setScale(1.8f);
                return true;
            }
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                chq.setScale(2);
                int ran = rnd.nextInt(5);
                ran++;
                nowquestion = ran;
                if(ran == 1){
                    musicdata = new ArrayList(musicdata1);
                }else if(ran == 2){
                    musicdata = new ArrayList(musicdata2);
                }else if(ran == 3){
                    musicdata = new ArrayList(musicdata3);
                }else if(ran == 4){
                    musicdata = new ArrayList(musicdata4);
                }else if(ran == 5){
                    musicdata = new ArrayList(musicdata5);
                }

            }
        });
        snare.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				/*
				太鼓のタッチダウンイベント。
				・縮小処理　・再生処理
				 */
                snare.setScale(2.65f);

                snaresound.play();
                if(onrecord == true){
						/*taplist.add(TimeUtils.millis() - starttime);
						Gdx.app.log("TAPRHYTHM",String.valueOf( TimeUtils.millis()-starttime));
						long nowtime = TimeUtils.millis()-starttime;
						int saisyou = ononeindex(musicdata).get(0);
						for(int index = 1;index > ononeindex(musicdata).size();index++){
							if(saisyou > nowtime - ononeindex(musicdata).get(index)){
								saisyou = nowtime - ononeindex(musicdata).get(index);
							}
						}
						Gdx.app.log("taprhythm",String.valueOf(nowtime - saisyou));
						diffrencelist.add(nowtime - saisyou);*/
                    //-----------------------------------------------------------------------
                    //今の時間
                    Long nowtime = TimeUtils.millis()-starttime-200;	//タッチパネル反応遅れ200msオフセットさせる
                    List<Integer> diftmp = new ArrayList<Integer>(otehon.size());
                    Integer minpostison = 0;
                    taplist.add(nowtime);
                    for(int i = 0; i < otehon.size();i++){
                        diftmp.add(Math.abs(otehon.get(i) - Integer.valueOf(nowtime.toString())));
                        Gdx.app.log("taprhythm:otehon",otehon.get(i).toString());
                    }
                    Gdx.app.log("taprhythm:nowtime",nowtime.toString());
                    Gdx.app.log("taprhythm:diftmp",diftmp.toString());
                    Integer saisyou = diftmp.get(0);
                    for (int index = 1; index < diftmp.size();index++){
                        if(saisyou > diftmp.get(index)){
                            saisyou = diftmp.get(index);
                            minpostison = index;
                        }
                    }
						/*
						for(int index = 0; index < diftmp.size() -1;index++){
							if(diftmp.get(index) < diftmp.get(index+1)){
								minpostison = index;
								//break;
							}else{
								minpostison = index+1;
							}
						/*	if(index == 2){
								Gdx.app.log("taprhythm:3nddiftmp",diftmp.toString());
							}
						}*/

                    Gdx.app.log("taprhythm:taplist",String.valueOf(taplist.size()));
                    diffrencelist.add(Long.valueOf(saisyou.toString()));
                    minposi.add(Integer.valueOf(minpostison.toString()));
                }
                return true;
            }
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
					/*
				太鼓のタッチアップイベント。
				・拡大処理
				 */
                snare.setScale(2.5f);
            }
        });
        recbutton.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				/*
				太鼓のタッチダウンイベント。
				・縮小処理　・再生処理
				 */
                recbutton.setScale(2.5f);
                return true;
            }
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
					/*
				太鼓のタッチアップイベント。
				・拡大処理
				 */
                muon.play();
          TimerTask metrorec = new TimerTask(){

                    int cnt = 0;

                    public void run() {
                        if(cnt < 24){
                            if(cnt % 8 == 0){
                                metorotin.play();
                            }
                            if(cnt % 2 == 0 && cnt % 8 != 0){
                                metoroka.play();
                            }
                            cnt++;
                        }
                        if(cnt == 23){
                            onrecord = false;
                            muon.stop();
                            Integer diffminpositmp = 0;
                            List<Integer> diffminposi = new ArrayList<Integer>();
                            Gdx.app.log("taprhythm:taplist", String.valueOf(taplist));
                            //Gdx.app.log("taprhythm",String.valueOf(diffrencelist));
                            Gdx.app.log("taprhythm:diffrencelist", diffrencelist.toString());
                            Gdx.app.log("taprhythm:otehonlist", otehon.toString());
                            Gdx.app.log("taprhythm:minposi", minposi.toString());
                            //-----------------------得点計算-------------------------------
                                /*TODO:TODO:
                                TODO:復旧用
								float  score0 = 100.00f;
                                Integer diffmintmp = 0;
								for(int otint = 0;otint < otehon.size();otint++){    //お手本の数だけループ
									boolean onindextap = false;
									int minindex = 0;
									for(int difint = 0;difint < minposi.size();difint++){   //タップした数（minposiの数）分ループ
                                        if (difint == 0){
                                            diffmintmp = Integer.valueOf(diffrencelist.get(difint).toString());
                                        }
										if(minposi.get(difint) == otint){
                                            if(Integer.valueOf(diffrencelist.get(difint).toString()) < diffmintmp) {
                                                diffmintmp = Integer.valueOf(diffrencelist.get(difint).toString()); //仮の差分最小値
                                                minindex = difint;
                                            }
											onindextap = true;

										}else if(onindextap == true){
                                            break;
                                        }
									}

									if(onindextap == true){ //打ってるか
										Gdx.app.log("taprhythm:onindextap","true");
                                        Gdx.app.log("taprhythm:minindex",String.valueOf(minindex));
										score0 = score0 * (1 - (diffrencelist.get(minindex) * k));
									}else{
										score0 = score0 - (100 / otehon.size());
									}
									Gdx.app.log("taprhythm:score0",String.valueOf(score0));
								}
								Gdx.app.log("taprhythm:endscore",String.valueOf(score0));
								scoreg = score0;*/
                            // TODO:復旧用

                            //お手本の位置ごとに、最も近いタップを探す------------------
                            float score0 = 100.00f;
                            if(taplist.size() == 0){
                                score0 = 0;

                            }else {

                                for (int otint = 0; otint < otehon.size(); otint++) {    //お手本の数分ループ
                                    //タップ回数でループ----------------

                                    int cntotehon = 0;    //お手本カウンタ初期化
                                    for (int difint = 0; difint < minposi.size(); difint++) {		//タップした数分ループ
                                        ; //タップ位置総当たり(minposiのサイズ分ループ)

                                        //該当位置以外では判定をスキップ（→次のminposiに）
                                        if (minposi.get(difint) != otint) {
                                            continue;    //判定スキップ
                                        }


                                        if (difint == 0) {//初回(difint==0)のみ特別処理
                                            diffminpositmp = -1;    //初期化


                                            if (minposi.get(difint) == otint) {
                                                diffminpositmp = 0;    //初期化

                                            }
                                        } else {            //2回目以降

                                            // -------- 仮の最小差分位置の決定 ----------- //
                                            // ->
                                            if (minposi.get(difint) == minposi.get(difint - 1)) {		//	前回タップ位置と今回タップ位置に対応する 最も近いお手本の位置が等しい場合のしょり
                                                if (diffrencelist.get(difint) < diffrencelist.get(difint - 1)) {	// 前回タップ位置の差分値よりも今回タップ位置の差分値のほうが小さい場合
                                                    // diffminpositmp = minposi.get(difint);    //仮の差分最小位置
                                                    diffminpositmp = difint;    				//仮の差分最小位置(タップした位置のインデックス)を今回タップ位置のインデックスとする
                                                } else {
                                                    // diffminpositmp = minposi.get(difint - 1);    //仮の差分最小位置
                                                    diffminpositmp = difint - 1;    				//仮の差分最小位置(タップした位置のインデックス)を前回タップ位置のインデックスとする
                                                }
                                            } else {
                                                // diffminpositmp = minposi.get(difint);    //仮の差分最小位置
                                                diffminpositmp = difint;    //仮の差分最小位置(タップした位置のインデックス)を今回タップ位置のインデックスとする
                                            }


                                        }

                                    }

                                    //差分最小となるインデックスを配列に格納
                                    // (-1:該当タップなし)

                                    diffminposi.add(diffminpositmp);

                                }


                                Gdx.app.log("taprhythm:diffminposi", diffminposi.toString());
                                //お手本の位置ごとに、得点計算-----------------
                                for (int otint2 = 0; otint2 < otehon.size(); otint2++) {//お手本の数分ループ
                                    if (diffminposi.get(otint2) >= 0) {
                                        // 1 お手本からの差分に応じた減点
                                        //	Gdx.app.log("taprhythm",String.valueOf(otint2));
                                        score0 = score0 - (100.00f * (diffrencelist.get(diffminposi.get(otint2)) * k));
                                    } else if (diffminposi.get(otint2) == -1) {
                                        // 2 タップしなかったことによる減点
                                        score0 = score0 - (100.00f / score0 - (100 / otehon.size()));
                                    } else {
                                        // 想定外エラー
                                        Gdx.app.log("souteigaisouteigai", "souteigaisouteigai");
                                    }


                                    Gdx.app.log("taprhythm:forscore", String.valueOf(score0));
                                    //上下限ガード
                                    if (score0 > 100.00f) {
                                        score0 = 100.00f;
                                    } else if (score0 < 0.00f) {
                                        score0 = 0.00f;
                                    }

                                }
                                Gdx.app.log("taprhythm:上下限前score", String.valueOf(score0));
                                //多くタップした場合の減点
                                if (minposi.size() > otehon.size()) {
                                    score0 = score0 * ((float) otehon.size() / (float) minposi.size());
                                }
                                //少なくタップした場合の減点
                                if (minposi.size() < otehon.size()) {
                                    score0 = score0 * ((float) minposi.size() / (float) otehon.size());
                                }


                                //上下限ガード
                                if (score0 >= 100.00f) {
                                    score0 = 99.999f;
                                } else if (score0 < 0.00f) {
                                    score0 = 0.000f;
                                }
                            }
                            //スコア格納
                            scoreg=score0;


                            Gdx.app.log("taprhythm:endscore",String.valueOf(scoreg));
                            isFinished = true;
                            FinishTime =  TimeUtils.millis() + 500;

                                  /*  scoreText.text = "あなたの得点は...";
                                    //wait
                                    scoreText.text = scoreText.text + String.valueOf(scoreg) + "点です.";*/

                            //-----------------------得点計算-------------------------------
                        }
                    }

                    };


                taplist.clear();
                diffrencelist.clear();
                minposi.clear();

                //	Gdx.app.log("taprhythm:score0",String.valueOf(score));
                //        Gdx.app.log("taprhythm",String.valueOf(starttime));
                starttime = TimeUtils.millis() + 2000;  //4拍分イントロ(2000ms)オフセット
                if (onrecord == false) {
                    timer.scheduleAtFixedRate(metrorec, 250L, 250L);


                    onrecord = true;
                }
                recbutton.setScale(2);

            }
        });
        playbutton.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				/*
				再生ボタンのタッチダウンイベント。
				・何もしない
				 */

                playbutton.setScale(2.5f);
                return true;
            }
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
					/*
				再生ボタンのタッチアップイベント。
				・ランダムリズムを再生
				 */
                if (onplay == false) {
                    onplay = true;
                    Gdx.app.log("Taprhythm", "OKOKOKOKOKOKOKOKOKOKOOOKOKO");
                    muon.play();
                    soundcount = 0;
                    /*Action autoplay = sequence(parallel(sequence(run(new Runnable() {
                                @Override
                                public void run() {
                                    playstarttime = TimeUtils.millis();//リセット
                                }
                            }),repeat(3,sequence(

                            run(new Runnable() {
                                @Override
                                public void run() {
                                    Gdx.app.log("taprhythm:metrotime", String.valueOf(TimeUtils.millis() - playstarttime));
                                    metorotin.play();
                                }
                            }),
                            delay(0.5f),
                            run(new Runnable() {
                                @Override
                                public void run() {
                                    metoroka.play();
                                }
                            }),
                            delay(0.5f),
                            run(new Runnable() {
                                @Override
                                public void run() {
                                    metoroka.play();
                                }
                            }),
                            delay(0.5f),
                            run(new Runnable() {
                                @Override
                                public void run() {
                                    metoroka.play();
                                }
                            }),
                            delay(0.5f)
                            ))),
                            sequence(delay(2.0f),
                                    repeat(16,sequence(
                                            run(new Runnable() {
                                                @Override
                                                public void run() {
                                        musicdata[1]
                                          int[]


                                                    if(musicdata.get(soundcount) == 1){
                                                        snaresound.play();
                                                    }
                                                    soundcount++;
                                                }
                                            }),delay(0.25f)

                                    )))),
                            run(new Runnable() {
                                @Override
                                public void run() {
                                    muon.stop();
                                    onplay = false;
                                }
                            })
                    );

                    stage.addAction(autoplay);*/
                    TimerTask timerTask = new TimerTask() {

                        int cnt = 0;

                        public void run() {
                            if (cnt < 24) {
                                if (cnt % 8 == 0) {
                                    metorotin.play();
                                }
                                if (cnt % 2 == 0 && cnt % 8 != 0) {
                                    metoroka.play();
                                }
                                if (cnt > 7) {
                                    if (musicdata.get(cnt - 8) == 1) {
                                        snaresound.play();
                                    }
                                }
                                soundcount++;
                                cnt++;
                            }
                            if (cnt == 23) {
                                muon.stop();
                                onplay = false;
                            }
                        }
                    };
                    Timer timer = new Timer();
                    timer.scheduleAtFixedRate(timerTask, 250L, 250L);

                } else {

                }
                playbutton.setScale(2);
            }
            });
                //Gdx.app.log("taprhythm",String.valueOf(ononeindex(musicdata)));
                otehon = ononeindex(musicdata);
            }




    @Override
    public void render (float delta) {
        Long nowtime = TimeUtils.millis();

        // camera.update();
        if (isFinished) {
            if(FinishTime < nowtime){
                Gdx.app.log("taprhythm:endscore",String.valueOf(scoreg));
                timer.cancel();
                MainMenuScreen screen = new MainMenuScreen(game);
                screen.setGscore(scoreg);
                game.setScreen(screen);
            }        }

        Gdx.gl.glClearColor(255 / 255.f, 255 / 255.f, 255 / 255.f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        //   batch.setProjectionMatrix(camera.combined);
        stage.act(Gdx.graphics.getDeltaTime());     // ステージの状態を前回render呼び出しからの経過時間(delta time)分だけ更新する
        stage.draw();
      /*  batch.begin();
        font.draw(batch, "Drops Collected: " + scoreg, 0, 480);  // ← この行を追加します: スコアを表示するプログラム
        batch.end();*/


    }
    @Override
    public void resume () {

    }

    @Override
    public void dispose () {

        stage.dispose();
    }
    @Override
    public void show() {
        onshow = true;
    }
    @Override
    public void pause() {

    }
    @Override
    public void hide() {
        onshow = false;
    }
   @Override
   public void resize (int width, int height){

   }
}
