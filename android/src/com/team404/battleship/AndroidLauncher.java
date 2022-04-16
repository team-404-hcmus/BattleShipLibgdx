package com.team404.battleship;

import android.os.Bundle;
import android.util.Log;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.team404.battleship.battleship;

public class AndroidLauncher extends AndroidApplication implements JavaAndroidBrigde {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		initialize(new battleship(this), config);


	}

	@Override
	public void Log(String log)
	{
		Log.w("vu",log);
	}
}
