package com.team404.battleship;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.team404.battleship.battleship;

public class AndroidLauncher extends AndroidApplication implements JavaAndroidBrigde {
	AudioManager audioManager;


	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		audioManager = (AudioManager) getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
		initialize(new battleship(this), config);

	}

	@Override
	public void OpenWifiSetting()
	{
		startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
	}

	@Override
	public void InCreaseVolume() {
		audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC,AudioManager.ADJUST_RAISE, AudioManager.FLAG_SHOW_UI);
	}

	@Override
	public void DeCreaseVolume() {
		audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC,AudioManager.ADJUST_LOWER, AudioManager.FLAG_SHOW_UI);
	}


}
