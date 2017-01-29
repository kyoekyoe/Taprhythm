package com.testa3d.taprhythm;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.testa3d.taprhythm.taprhythm;

public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		config.useAccelerometer = false;  // 加速度センサーを無効にする
		config.useCompass = false;        // GPSセンサーを無効にする
		initialize(new taprhythm(), config);
	}
}

