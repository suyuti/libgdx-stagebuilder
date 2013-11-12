package net.peakgames.libgdx.stagebuilder.java;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import net.peakgames.libgdx.stagebuilder.core.demo.StageBuilderDemo;

public class StageBuilderTestDesktop {
    public static void main(String[] args) {
        int width = 800;
        int height = 480;

        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.useGL20 = true;
        config.width = width;
        config.height = height;

        StageBuilderDemo demo = new StageBuilderDemo();
        demo.initialize(width, height);

        new LwjglApplication(demo, config);
    }
}
