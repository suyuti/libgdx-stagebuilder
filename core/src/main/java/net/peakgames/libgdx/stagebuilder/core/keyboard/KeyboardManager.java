package net.peakgames.libgdx.stagebuilder.core.keyboard;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.FocusListener;

public class KeyboardManager implements SoftKeyboardEventListener {
	
	private SoftKeyboardEventListener listener;
	private boolean keyboardOpen;
	private Stage stage;
	private String focusedActorName;
	private int keyboardHeight;
	private float initialStageY;
	private Map<Actor, Float> initialYCoordinateMap;
	private int screenHeight;
	
	public KeyboardManager(int screenHeight) {
		this.screenHeight = screenHeight;
		this.initialYCoordinateMap = new HashMap<Actor, Float>();
	}

	private void updateStagePosition(Actor focusedActor) {
		// Ignore this event at the initialization phase of the application.
		if(stage == null || focusedActorName == null) {
			return;
		}
		
		Group rootGroup = stage.getRoot();
		float textFieldScreenY = getScreenYOfFocusedWidget(focusedActor);
		float topOfFocusedActor = textFieldScreenY + focusedActor.getHeight();
		if(isTextFieldOutOfScreen(topOfFocusedActor)) {
			float stageYDifference = screenHeight - topOfFocusedActor;
			shiftChildren(rootGroup, stageYDifference);
		} else {			
			float stageYDifference = keyboardHeight - textFieldScreenY;
			if(isTextFieldCoveredByKeyboard(stageYDifference)) {			
				shiftChildren(rootGroup, stageYDifference);
			}
		}

	}
	
	private void shiftChildren(Group rootGroup, float stageYDifference) {
		for(Actor actor : rootGroup.getChildren()) {
			actor.setY(actor.getY() + stageYDifference);
		}
	}

	private boolean isTextFieldOutOfScreen(float topOfFocusedActor) {
		return topOfFocusedActor > screenHeight;
	}
	
	private boolean isTextFieldCoveredByKeyboard(float stageYDifference) {
		return stageYDifference > 0;
	}
	
	private float getScreenYOfFocusedWidget(Actor focusedActor) {
		float screenY = focusedActor.getY();
		while(focusedActor.getParent() != null) {
			focusedActor = focusedActor.getParent();
			screenY += focusedActor.getY();
		}
		return screenY;
	}
	
	public void setListener(SoftKeyboardEventListener listener) {
		this.listener = listener;
	}

	public boolean isKeyboardOpen() {
		return keyboardOpen;
	}
	
	public void setStage(Stage stage) {
		this.stage = stage;
		addFocusChangedListenerToStage();
		initialYCoordinateMap.clear();
		for(Actor actor : stage.getRoot().getChildren()) {
			initialYCoordinateMap.put(actor, actor.getY());
		}
	}

	private void addFocusChangedListenerToStage() {
		this.stage.addListener(new FocusListener() {
			@Override
			public void keyboardFocusChanged(FocusEvent event, Actor actor,
					boolean focused) {
				super.keyboardFocusChanged(event, actor, focused);
				if(focused && actor instanceof TextField) {
					textFieldFocusChanged(actor.getName());
				}
			}
		});
	}

	public void textFieldFocusChanged(String actorName) {
		focusedActorName = actorName;
		if(isKeyboardOpen()) {
			Actor focusedActor = stage.getRoot().findActor(focusedActorName);
			updateStagePosition(focusedActor);
		}
	}

	@Override
	public void softKeyboardOpened(int keyboardHeight) {
		this.keyboardOpen = true;
		this.keyboardHeight = keyboardHeight;
		if(focusedActorName == null) {
			return;
		}
		Actor focusedActor = stage.getRoot().findActor(focusedActorName);
		if(focusedActor != null) {			
			updateStagePosition(focusedActor);
		} 
		if(listener != null) {
			listener.softKeyboardOpened(keyboardHeight);
		}
	}

	@Override
	public void softKeyboardClosed(int keyboardHeight) {
		this.keyboardOpen = false;
		if(stage == null) {
			return;
		}
		Group rootGroup = stage.getRoot();
		for(Actor actor : rootGroup.getChildren()) {
			Float actorInitialY = initialYCoordinateMap.get(actor);
			if(actorInitialY != null) {				
				actor.setY(actorInitialY);
			}
		}
		if(listener != null) {
			listener.softKeyboardClosed(keyboardHeight);
		}
	}
	
}
