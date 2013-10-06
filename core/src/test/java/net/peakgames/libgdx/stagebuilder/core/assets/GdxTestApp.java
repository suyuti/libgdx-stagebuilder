package net.peakgames.libgdx.stagebuilder.core.assets;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class GdxTestApp {
	private static boolean appLaunched = false;
	final private static Object lock = new Object();
	
	public  static void launchApp() {
		synchronized (lock) {
			if (appLaunched) {
				return;
			} else {
				LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
				config.width = 1;
				config.height = 1;
				new LwjglApplication(new Game() {
                    @Override
                    public void create() {
                    }
                }, config);
				appLaunched = true;
			}
		}
	} 
}
