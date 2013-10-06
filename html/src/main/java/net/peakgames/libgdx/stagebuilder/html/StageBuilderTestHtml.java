package net.peakgames.libgdx.stagebuilder.html;

import net.peakgames.libgdx.stagebuilder.core.StageBuilderTest;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;

public class StageBuilderTestHtml extends GwtApplication {
	@Override
	public ApplicationListener getApplicationListener () {
		return new StageBuilderTest();
	}
	
	@Override
	public GwtApplicationConfiguration getConfig () {
		return new GwtApplicationConfiguration(480, 320);
	}
}
