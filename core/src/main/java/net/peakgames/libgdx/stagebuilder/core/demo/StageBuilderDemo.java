package net.peakgames.libgdx.stagebuilder.core.demo;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.math.Vector2;
import net.peakgames.libgdx.stagebuilder.core.AbstractGame;

import java.util.LinkedList;
import java.util.List;

public class StageBuilderDemo extends AbstractGame {

    @Override
    public List<Vector2> getSupportedResolutions() {
        List<Vector2> supportedScreenResolutions = new LinkedList<Vector2>();
        supportedScreenResolutions.add(new Vector2(800, 480));
        return supportedScreenResolutions;
    }

    @Override
    public void create() {
        Screen demoScreen = new SplashScreen(this);
        setScreen(demoScreen);
    }
}
