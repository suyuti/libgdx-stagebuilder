package net.peakgames.libgdx.stagebuilder.core.widgets;

import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;

import net.peakgames.libgdx.stagebuilder.core.ICustomWidget;
import net.peakgames.libgdx.stagebuilder.core.assets.AssetsInterface;
import net.peakgames.libgdx.stagebuilder.core.assets.ResolutionHelper;
import net.peakgames.libgdx.stagebuilder.core.services.LocalizationService;

public class LoadingWidget extends WidgetGroup implements ICustomWidget{
	
	private ShapeRenderer shapeRenderer;
	private AssetsInterface assets;
	private String atlasName = "LoadingBar.atlas";
	private String backgroundFrame = "loading_back";
	private String foregroundFrame = "loading_bar";
	private boolean fullScreen = false;
	private float alpha = 1;
	private float boundaryWidth;
	private float boundaryHeight;
	private String fontName;
	
	private String text;
	private Image backgroundImage;
	private Image foregroundImage;
	private Label messageLabel;
	
	private long timeOutDuration = -1;
	/**
	 * Bu widget icin show metodu ne zaman cagirilmis.
	 */
	private long showTime = 0;
	
	public LoadingWidget(){
		
	}
	
	public LoadingWidget(AssetsInterface assets, ResolutionHelper resolutionHelper) {
		this.assets = assets;
		initialize(resolutionHelper);
	}
	
	public LoadingWidget(AssetsInterface assets, ResolutionHelper resolutionHelper, float alpha, boolean fullScreen, String text, String fontName, String backgroundFrame, String foregroundFrame, String atlasName) {
		this.assets = assets;
		this.alpha = alpha;
		this.fullScreen = fullScreen;
		this.text = text;
		this.fontName = fontName;
		this.backgroundFrame = backgroundFrame;
		this.foregroundFrame = foregroundFrame;
		this.atlasName = atlasName;
		initialize(resolutionHelper);
	}
	
	public LoadingWidget(AssetsInterface assets, ResolutionHelper resolutionHelper, float alpha, int boundaryWidth, int boundaryHeight, String text) {
		this.assets = assets;
		this.alpha = alpha;
		this.boundaryWidth = boundaryWidth;
		this.boundaryHeight = boundaryHeight;
		this.text = text;
		initialize(resolutionHelper);
	}
	
	public void initialize(ResolutionHelper resolutionHelper) {
		float sizeMultiplier = resolutionHelper.getSizeMultiplier();
		
		TextureAtlas atlas = assets.getTextureAtlas(atlasName);
		backgroundImage = new Image(atlas.findRegion(backgroundFrame));
		foregroundImage = new Image(atlas.findRegion(foregroundFrame));
		
		if(text != null) {			
			messageLabel = new Label(text, new LabelStyle(assets.getFont(fontName), Color.YELLOW));
		}
		
		resizeActor(backgroundImage, sizeMultiplier);
		resizeActor(foregroundImage, sizeMultiplier);
		resizeActor(messageLabel, sizeMultiplier);
		
		if(fullScreen) {
			this.boundaryWidth = resolutionHelper.getScreenWidth();
			this.boundaryHeight = resolutionHelper.getScreenHeight();
			setSize(boundaryWidth, boundaryHeight);
			backgroundImage.setPosition(boundaryWidth / 2 - this.backgroundImage.getWidth() / 2, boundaryHeight / 2 - this.backgroundImage.getHeight() / 2);
			foregroundImage.setPosition(boundaryWidth / 2 - this.foregroundImage.getWidth() / 2, boundaryHeight / 2 - this.foregroundImage.getHeight() / 2);
			if(messageLabel != null) {				
				messageLabel.setPosition(boundaryWidth / 2 - messageLabel.getTextBounds().width / 2, boundaryHeight / 2 - messageLabel.getTextBounds().height / 2);
			}
		} else {
			backgroundImage.setSize(boundaryWidth, boundaryHeight);
			foregroundImage.setSize(boundaryWidth, boundaryHeight);
			if(messageLabel != null) {					
				messageLabel.setPosition(backgroundImage.getWidth() / 2 - messageLabel.getTextBounds().width / 2, backgroundImage.getHeight() / 2 - messageLabel.getTextBounds().height / 2);
			}
		}
		
		foregroundImage.setOrigin(foregroundImage.getWidth() / 2, foregroundImage.getHeight() / 2);
		backgroundImage.setOrigin(backgroundImage.getWidth() / 2, backgroundImage.getHeight() / 2);
		
		addActor(backgroundImage);
		addActor(foregroundImage);
		if(messageLabel != null) {			
			addActor(messageLabel);
		}
		
		hide();
	}
	
	private void resizeActor(Actor actor, float sizeMultiplier) {
		actor.setScale(sizeMultiplier);
	}
	
	@Override
	protected void setStage (Stage stage) {
		super.setStage(stage);
		if(stage != null) {
			this.shapeRenderer = new ShapeRenderer();
		}
	}
	
	public void show() {
		this.showTime = System.currentTimeMillis();
		this.setVisible(true);
	}
	
	public void hide() {
		this.setVisible(false);
	}
	
	@Override
	public void draw (SpriteBatch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		foregroundImage.rotate(5);
		if(fullScreen) {
			batch.end();
			
			Gdx.gl.glEnable(GL10.GL_BLEND);
			Gdx.gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
			
			shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
			shapeRenderer.begin(ShapeType.Filled);
			shapeRenderer.setColor(0f, 0f, 0f, alpha);
			shapeRenderer.rect(0, 0, boundaryWidth, boundaryHeight);
			shapeRenderer.end();
			
			Gdx.gl.glDisable(GL10.GL_BLEND);
			
			batch.begin();
			
			this.backgroundImage.draw(batch, parentAlpha);
			this.foregroundImage.draw(batch, parentAlpha);
			if(messageLabel != null) {				
				this.messageLabel.draw(batch, parentAlpha);
			}
		}
		if (timeOutDuration > 0) {
			if (this.showTime + this.timeOutDuration < System.currentTimeMillis()) {
				this.hide();
			}
		}
	}

	public boolean isFullScreen() {
		return fullScreen;
	}

	public void setFullScreen(boolean fullScreen) {
		this.fullScreen = fullScreen;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
	public void setTimeoutDuration(long duration) {
		this.timeOutDuration = duration;
	}

	@Override
	public void build(Map<String, String> attributes,
			AssetsInterface assetsInterface, ResolutionHelper resolutionHelper,
			LocalizationService localizationService) {
		this.assets = assetsInterface;
		this.setName(attributes.get("name"));
		this.fullScreen = Boolean.parseBoolean(attributes.get("fullScreen"));
		this.alpha = Float.parseFloat(attributes.get("alpha"));
		this.text = attributes.get("text");
		
		if(attributes.get("boundaryWidth") != null) {		
			this.boundaryWidth = Float.parseFloat(attributes.get("boundaryWidth"));
		}
		
		if(attributes.get("boundaryHeight") != null) {
			this.boundaryHeight = Float.parseFloat(attributes.get("boundaryHeight"));
		}
		if(attributes.get("fontName") != null) {			
			this.fontName = attributes.get("fontName");
		}
		
		backgroundFrame = attributes.get("backgroundFrame");
		foregroundFrame = attributes.get("foregroundFrame");
		atlasName = attributes.get("atlasName");
		
		initialize(resolutionHelper);
		
	}

}
