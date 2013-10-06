package net.peakgames.libgdx.stagebuilder.core.demo;

import com.badlogic.gdx.Screen;
import net.peakgames.libgdx.stagebuilder.core.AbstractGame;

public class StageBuilderDemo extends AbstractGame {

    @Override
    public void create() {
        Screen demoScreen = new SplashScreen(this);
        setScreen(demoScreen);
    }
}
