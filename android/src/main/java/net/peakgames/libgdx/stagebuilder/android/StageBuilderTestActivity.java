package net.peakgames.libgdx.stagebuilder.android;

import android.os.Bundle;
import android.util.DisplayMetrics;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.badlogic.gdx.math.Vector2;
import net.peakgames.libgdx.stagebuilder.core.StageBuilderTest;
import net.peakgames.libgdx.stagebuilder.core.demo.StageBuilderDemo;

import java.util.LinkedList;
import java.util.List;

public class StageBuilderTestActivity extends AndroidApplication {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        config.useGL20 = true;
        config.useAccelerometer = false;
        config.useCompass = false;

        List<Vector2> supportedScreenResolutions = new LinkedList<Vector2>();
        supportedScreenResolutions.add(new Vector2(800, 480));

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        StageBuilderDemo demo = new StageBuilderDemo();
        demo.initialize(metrics.widthPixels, metrics.heightPixels, supportedScreenResolutions);

        initialize(demo, config);
    }
}
