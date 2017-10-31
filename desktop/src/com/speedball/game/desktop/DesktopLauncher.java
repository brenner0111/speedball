package com.speedball.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.speedball.client.SpeedballClient;
import com.speedball.game.SpeedBall;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Speedball";
		config.height = 720;
		config.width = 1080;
		new LwjglApplication(new SpeedballClient(), config);
	}
}
