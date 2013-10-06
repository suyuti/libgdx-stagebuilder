package net.peakgames.libgdx.stagebuilder.java;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.math.Vector2;
import net.peakgames.libgdx.stagebuilder.core.demo.StageBuilderDemo;

import java.util.LinkedList;
import java.util.List;

public class StageBuilderTestDesktop {
    public static void main(String[] args) {
        int width = 480;
        int height = 320;

        List<Vector2> supportedScreenResolutions = new LinkedList<Vector2>();
        supportedScreenResolutions.add(new Vector2(800, 480));

        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.useGL20 = true;
        config.width = width;
        config.height = height;

        StageBuilderDemo demo = new StageBuilderDemo();
        demo.initialize(width, height, supportedScreenResolutions);

        new LwjglApplication(demo, config);
    }
}
