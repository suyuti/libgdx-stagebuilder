package net.peakgames.libgdx.stagebuilder.core.assets;

import com.badlogic.gdx.math.Vector2;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ResolutionHelperTest {

	@Test
	public void calculateGameArea_when_target_and_current_aspect_ratios_are_same() {
		ResolutionHelper helper = new ResolutionHelper(800f, 480f,  800f, 480f, 800f);
		Vector2 gameArea = helper.getGameAreaBounds();
		assertEquals(800f, gameArea.x, 0f);
		assertEquals(480f, gameArea.y, 0f);
		
		Vector2 gameAreaPos = helper.getGameAreaPosition();
		assertEquals(0f, gameAreaPos.x, 0f);
		assertEquals(0f, gameAreaPos.y, 0f);
		
		helper = new ResolutionHelper(800f, 480f,  800f, 480f, 1f);
		gameArea = helper.getGameAreaBounds();
		assertEquals(800f, gameArea.x, 0f);
		assertEquals(480f, gameArea.y, 0f);
		
		gameAreaPos = helper.getGameAreaPosition();
		assertEquals(0f, gameAreaPos.x, 0f);
		assertEquals(0f, gameAreaPos.y, 0f);
	}
	
	@Test
	public void calculateGameArea_when_target_aspect_ratio_is_greater_than_current() {
		ResolutionHelper helper = new ResolutionHelper(800f, 480f, 1920f, 1200f, 1920f);
		Vector2 gameArea = helper.getGameAreaBounds();
		assertEquals(1920f, gameArea.x, 0f);
		assertEquals(1152f, gameArea.y, 0f);
		
		Vector2 gameAreaPos = helper.getGameAreaPosition();
		assertEquals(0f, gameAreaPos.x, 0f);
		assertEquals(24f, gameAreaPos.y, 0f);
		
		
		helper = new ResolutionHelper(800f, 480f,960f, 640f, 960f);
		gameArea = helper.getGameAreaBounds();
		assertEquals(960f, gameArea.x, 0f);
		assertEquals(576f, gameArea.y, 0f);
		
		gameAreaPos = helper.getGameAreaPosition();
		assertEquals(0f, gameAreaPos.x, 0f);
		assertEquals(32f, gameAreaPos.y, 0f);
		
		helper = new ResolutionHelper(800f, 480f, 1280f, 800f, 1280f);
		gameArea = helper.getGameAreaBounds();
		assertEquals(1280f, gameArea.x, 0f);
		assertEquals(768f, gameArea.y, 0f);
		
		gameAreaPos = helper.getGameAreaPosition();
		assertEquals(0f, gameAreaPos.x, 0f);
		assertEquals(16f, gameAreaPos.y, 0f);
		
		helper = new ResolutionHelper(800f, 480f, 480f, 320f, 800f);
		gameArea = helper.getGameAreaBounds();
		assertEquals(480f, gameArea.x, 0f);
		assertEquals(288f, gameArea.y, 0f);
		
		gameAreaPos = helper.getGameAreaPosition();
		assertEquals(0f, gameAreaPos.x, 0f);
		assertEquals(16f, gameAreaPos.y, 0f);
	}
	
	@Test
	public void calculateGameArea_when_target_aspect_ratio_is_less_than_current() {
		ResolutionHelper helper = new ResolutionHelper(800f, 480f, 1280f, 720f, 1280f);
		Vector2 gameArea = helper.getGameAreaBounds();
		assertEquals(1200f, gameArea.x, 0f);
		assertEquals(720f, gameArea.y, 0f);
		
		Vector2 gameAreaPos = helper.getGameAreaPosition();
		assertEquals(40f, gameAreaPos.x, 0f);
		assertEquals(0f, gameAreaPos.y, 0f);
		
		helper = new ResolutionHelper(800f, 480f, 1366f, 768f, 1280f);
		gameArea = helper.getGameAreaBounds();
		assertEquals(1280f, gameArea.x, 0f);
		assertEquals(768f, gameArea.y, 0f);
		
		gameAreaPos = helper.getGameAreaPosition();
		assertEquals(43f, gameAreaPos.x, 0f);
		assertEquals(0f, gameAreaPos.y, 0f);
	}
	
	@Test
	public void calculateScreenCoordinates() {
		ResolutionHelper helper = new ResolutionHelper(800f, 480f, 1280f, 720f, 1280f);
		assertEquals(new Vector2(40, 0), helper.toScreenCoordinates(0, 0));
		assertEquals(new Vector2(140,100), helper.toScreenCoordinates(100, 100));
		
		helper = new ResolutionHelper(800f, 480f, 1920f, 1200f, 1280f);
		assertEquals(new Vector2(0, 24), helper.toScreenCoordinates(0, 0));
		assertEquals(new Vector2(500, 524), helper.toScreenCoordinates(500, 500));
	}

	
	@Test
	public void calculateBackgroundSize_and_position() {
		float targetAspectRatio = 10f/6f;
		ResolutionHelper helper = new ResolutionHelper(800f, 480f, 1280f, 720f, 1280f);
		assertEquals(new Vector2(1280f, 768f), helper.calculateBackgroundSize(800, 480));
		
		assertEquals(new Vector2(0, -40), helper.calculateBackgroundPosition(1280f, 800f));
		
		helper = new ResolutionHelper(800f, 480f, 1280f, 800f, 1280f);
		Vector2 bgBounds = helper.calculateBackgroundSize(800, 480);
		assertEquals(1333, (int)bgBounds.x);
		assertEquals(800, (int)bgBounds.y);
		assertEquals(new Vector2(0, 0), helper.calculateBackgroundPosition(1280f, 800f));
		
		helper = new ResolutionHelper(800f, 480f, 1280f, 800f, 1280f);
		bgBounds = helper.calculateBackgroundSize(1280f, 800f);
		assertEquals(1280, (int)bgBounds.x);
		assertEquals(800, (int)bgBounds.y);
		Vector2 bgPos = helper.calculateBackgroundPosition(1280f, 720f);
		assertEquals(-71, (int)bgPos.x);
		assertEquals(0, (int)bgPos.y);
	}
	
	@Test
	public void calculateMultiplier() {
		ResolutionHelper helper = new ResolutionHelper(800f, 480f, 1280f, 720f, 1280f);
		assertEquals(1200f/800f, helper.getPositionMultiplier(), 0f);
		
		helper = new ResolutionHelper(800f, 480f, 1280f, 800f, 1280f);
		assertEquals(1280f/800f, helper.getPositionMultiplier(), 0f);
		
		helper = new ResolutionHelper(800f, 480f, 1920f, 1200f, 1280f);
		assertEquals(1920/800f, helper.getPositionMultiplier(), 0f);
		assertEquals(1.5f /*1920/1280*/, helper.getSizeMultiplier(), 0f);
		
		helper = new ResolutionHelper(800f, 480f, 1024f, 600f, 800f);
		assertEquals(1000/800f, helper.getPositionMultiplier(), 0f);
		assertEquals(1000/800f /*1920/1280*/, helper.getSizeMultiplier(), 0f);
		
		helper = new ResolutionHelper(800f, 480f, 480f, 320f, 1280f);
		assertEquals(480/800f, helper.getPositionMultiplier(), 0f);
		assertEquals(480/1280f /*1920/1280*/, helper.getSizeMultiplier(), 0f);
		
	}
}
