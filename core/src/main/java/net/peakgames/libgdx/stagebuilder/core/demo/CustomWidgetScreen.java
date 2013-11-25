package net.peakgames.libgdx.stagebuilder.core.demo;

import net.peakgames.libgdx.stagebuilder.core.AbstractGame;
import net.peakgames.libgdx.stagebuilder.core.widgets.LoadingWidget;

public class CustomWidgetScreen extends DemoScreen {
    public CustomWidgetScreen(AbstractGame game) {
        super(game);
        
        LoadingWidget loadingWidget = (LoadingWidget)findActor("loading_widget");
        loadingWidget.show();
        
    }
}
