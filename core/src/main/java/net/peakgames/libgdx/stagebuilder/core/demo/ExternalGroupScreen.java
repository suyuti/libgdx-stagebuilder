package net.peakgames.libgdx.stagebuilder.core.demo;


import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import net.peakgames.libgdx.stagebuilder.core.AbstractGame;

public class ExternalGroupScreen extends DemoScreen {

    public ExternalGroupScreen(AbstractGame game) {
        super(game);
        setPopupButtonListener();
    }
    
    public void onStageReloaded() {	
    	setPopupButtonListener();
    }

	private void setPopupButtonListener() {
		final TextButton button = findTextButton("showPopupButton");
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Actor popup = findActor("popup");
                if (popup.isVisible()) {
                    button.setText("Show Popup");
                    popup.setVisible(false);
                } else {
                    button.setText("Hide Popup");
                    popup.setVisible(true);
                    popup.addAction(Actions.sequence(Actions.alpha(0), Actions.alpha(1, 0.5f)));
                }
            }
        });
	}

}
