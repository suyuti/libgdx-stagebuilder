package net.peakgames.libgdx.stagebuilder.core.keyboard;

import android.graphics.Rect;
import android.view.View;
import android.view.ViewTreeObserver;

import com.badlogic.gdx.backends.android.AndroidGraphics;

public class AndroidKeyboardEventService implements SoftKeyboardEventInterface{

	private static final int MIN_KEYBOARD_HEIGHT = 120;
	private AndroidGraphics graphics;
	private SoftKeyboardEventListener softKeyboardEventListener;

	public AndroidKeyboardEventService(AndroidGraphics graphics) {
		this.graphics = graphics;
	}
	
	@Override
	public void initialize() {
		final View root = graphics.getView();
		root.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
			public void onGlobalLayout() {
				Rect visibleAreaRectangle = new Rect();
				root.getWindowVisibleDisplayFrame(visibleAreaRectangle);
				int screenHeight = root.getRootView().getHeight();
				int visibleAreaHeight = visibleAreaRectangle.bottom - visibleAreaRectangle.top;
				int heightDifference = screenHeight - visibleAreaHeight;
				
				if (heightDifference > MIN_KEYBOARD_HEIGHT) {
					softKeyboardEventListener.softKeyboardOpened(heightDifference);
				} else {
					softKeyboardEventListener.softKeyboardClosed(heightDifference);
				}
			}
		});
	}

	@Override
	public void setSoftKeyboardEventListener(
			SoftKeyboardEventListener eventListener) {
		this.softKeyboardEventListener = eventListener;
	}
	
}
