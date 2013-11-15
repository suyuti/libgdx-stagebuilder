package net.peakgames.libgdx.stagebuilder.core.widgets;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.peakgames.libgdx.stagebuilder.core.ICustomWidget;
import net.peakgames.libgdx.stagebuilder.core.assets.AssetsInterface;
import net.peakgames.libgdx.stagebuilder.core.assets.ResolutionHelper;
import net.peakgames.libgdx.stagebuilder.core.services.LocalizationService;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 * Text and graphic based resource monitor widget.
 * Keeps track of frames per second, java heap size and native heap size.
 *
 * Updates the information every second by default.
 * {@code ResourceMonitorWidget} can be constructed with {@code updateIntervalInSecs} parameter to customize the update interval.
 * Update method can be called to update the information when desired.
 * Graphic can be changed by clicking on labels.
 * Time interval of graphic is changed between 1-16 minutes if you keep clicking labels.
 */
public class ResourceMonitorWidget extends WidgetGroup implements ICustomWidget {

	public enum GraphicType {
		JAVA_HEAP, NATIVE_HEAP, FPS
	}
	
    private static final int ONE_KILOBYTE = 1024;
    private static final String FPS_LABEL = "FramePerSecond: ";
    private static final String JAVA_HEAP_LABEL = "JavaHeap: ";
    private static final String NATIVE_HEAP_LABEL = "NativeHeap: ";
    private static final String KB = " KB";
    private static final float UPDATE_INTERVAL_IN_SECS = 1f;
    private static final int DEFAULT_EMPTY_SPACE_HEIGHT = 10;
    private final String fontName;
    private AssetsInterface assets;
    private ResolutionHelper resolutionHelper;
    private String atlasName;
    private String backgroundFrame;
    private Label fpsLabel;
    private Label nativeHeapLabel;
    private Label javaHeapLabel;
    private Label intervalLabel;
    private Image backgroundImage;
    private ShapeRenderer shapeRenderer;
    private GraphicType graphicType = GraphicType.JAVA_HEAP;
    private Color graphicColor = Color.GREEN;

    private float timeSinceLastUpdate;
    private float updateIntervalInSecs;
    
    private float touchedX;
    private float touchedY;
    private float maxWidth;
    
    private List<Long> javaHeapSizesPerSecondList;
    private List<Long> nativeHeapSizesPerSecondList;
    private List<Long> fpsList;
    private final int MAX_VALUES_STORED_IN_LISTS = 960; // 16 minutes
    private final int DEFAULT_VISIBLE_VALUE_COUNT = 60;
    private int visibleValueCount = 60;
    private final int INCREASE_COEFFICIENT = 2;
    private final float NORMALIZATION_COEFFICIENT = 1.1f;
    private float graphicHeight;
    
    private long maxJavaHeap = 0;
    private long maxNativeHeap = 0;
    private long maxFps = 0;
    
    private Vector2 leftTopGraphicBound = new Vector2();
    private Vector2 rightTopGraphicBound = new Vector2();
    private Vector2 leftBottomGraphicBound = new Vector2();
    private Vector2 rightBottomGraphicBound = new Vector2();
    private Vector2 graphicPointBeginning = new Vector2();
    private Vector2 graphicPointEnding = new Vector2();
    
    public static class Builder{

    	private String atlasName = null;
    	private AssetsInterface assets;
    	private String backGroundFrame;
    	private String fontName;
    	private ResolutionHelper resolutionHelper;
    	
		public ResourceMonitorWidget build() {
			ResourceMonitorWidget popupWidget = new ResourceMonitorWidget(this);
			return popupWidget;
		}
		
		public Builder atlasName(String atlasName) {
			this.atlasName = atlasName;
			return this;
		}

		public Builder assets(AssetsInterface assets) {
			this.assets = assets;
			return this;
		}

		public Builder backGroundFrame(String backGroundFrame) {
			this.backGroundFrame = backGroundFrame;
			return this;
		}
		
		public Builder resolutionHelper(ResolutionHelper resolutionHelper) {
			this.resolutionHelper = resolutionHelper;
			return this;
		}
		
		public Builder fontName(String fontName) {
			this.fontName = fontName;
			return this;
		}

	}
    
    private ResourceMonitorWidget(Builder builder) {
		this.fontName = builder.fontName;
		this.assets = builder.assets;
		this.backgroundFrame = builder.backGroundFrame;
		this.atlasName = builder.atlasName;
		this.resolutionHelper = builder.resolutionHelper;
		this.updateIntervalInSecs = UPDATE_INTERVAL_IN_SECS;
		initialize();
	}

    public ResourceMonitorWidget( AssetsInterface assets, String atlasName, String backgroundFrame, String fontName){
        this.assets = assets;
        this.atlasName = atlasName;
        this.backgroundFrame = backgroundFrame;
        this.fontName = fontName;
        this.updateIntervalInSecs = UPDATE_INTERVAL_IN_SECS;
        initialize();
    }

    public ResourceMonitorWidget( AssetsInterface assets, String atlasName, String backgroundFrame, String fontName, float updateIntervalInSecs){
        this.assets = assets;
        this.atlasName = atlasName;
        this.backgroundFrame = backgroundFrame;
        this.fontName = fontName;
        this.updateIntervalInSecs = updateIntervalInSecs;
        initialize();
    }

    private void initialize(){
        Label.LabelStyle fpsLabelStyle = new Label.LabelStyle( assets.getFont(fontName), Color.RED);
        Label.LabelStyle javaHeapLabelStyle = new Label.LabelStyle( assets.getFont(fontName), Color.GREEN);
        Label.LabelStyle nativeHeapLabelStyle = new Label.LabelStyle( assets.getFont(fontName), Color.BLUE);
        Label.LabelStyle intervalLabelStyle = new Label.LabelStyle(assets.getFont(fontName), Color.WHITE);
        fpsLabel = new Label( FPS_LABEL, fpsLabelStyle);
        nativeHeapLabel = new Label( NATIVE_HEAP_LABEL, nativeHeapLabelStyle);
        javaHeapLabel = new Label( JAVA_HEAP_LABEL, javaHeapLabelStyle);
        intervalLabel = new Label("1m", intervalLabelStyle);
        
        addLabelListeners();

        float sizeMultiplier = resolutionHelper.getSizeMultiplier();
        float emptySpaceHeight = DEFAULT_EMPTY_SPACE_HEIGHT * sizeMultiplier;

        nativeHeapLabel.setPosition(0,0);
        javaHeapLabel.setPosition(0, nativeHeapLabel.getY() + nativeHeapLabel.getTextBounds().height + emptySpaceHeight);
        fpsLabel.setPosition(0, javaHeapLabel.getY() + javaHeapLabel.getTextBounds().height + emptySpaceHeight);

        maxWidth = findMaxWidth();
        backgroundImage = new Image( assets.getTextureAtlas(atlasName).findRegion(backgroundFrame));
        backgroundImage.setSize(maxWidth, fpsLabel.getTextBounds().height * NORMALIZATION_COEFFICIENT + fpsLabel.getY());
        this.setSize(backgroundImage.getWidth(), backgroundImage.getHeight());
        this.addActor(backgroundImage);
        this.addActor(fpsLabel);
        this.addActor(nativeHeapLabel);
        this.addActor(javaHeapLabel);
        this.addActor(intervalLabel);
        this.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            	touchedX = x;
            	touchedY = y;
                return true;
            }

            @Override
            public void touchDragged(InputEvent event, float x, float y, int pointer) {
                setPosition(getX() + x - touchedX, getY() + y - touchedY);
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {

            }
        });
        
        timeSinceLastUpdate = 0;
        graphicHeight = resolutionHelper.getScreenHeight() / 6;
        shapeRenderer = new ShapeRenderer();
        javaHeapSizesPerSecondList = new ArrayList<Long>(MAX_VALUES_STORED_IN_LISTS);
        nativeHeapSizesPerSecondList = new ArrayList<Long>(MAX_VALUES_STORED_IN_LISTS);
        fpsList = new ArrayList<Long>(MAX_VALUES_STORED_IN_LISTS);
        intervalLabel.setPosition(0, - graphicHeight - intervalLabel.getTextBounds().height * NORMALIZATION_COEFFICIENT);
        
        this.setPosition(0, intervalLabel.getY() * -1);
    }

    private void addLabelListeners() {
    	fpsLabel.addListener(new ClickListener() {
    		@Override
    		public void clicked(InputEvent event, float x, float y) {
    			super.clicked(event, x, y);
    			setGraphicProperties(GraphicType.FPS, Color.RED);
    		}
    	});
    	
    	javaHeapLabel.addListener(new ClickListener() {
    		@Override
    		public void clicked(InputEvent event, float x, float y) {
    			super.clicked(event, x, y);
    			setGraphicProperties(GraphicType.JAVA_HEAP, Color.GREEN);
    		}
    	});
    	
    	nativeHeapLabel.addListener(new ClickListener() {
    		@Override
    		public void clicked(InputEvent event, float x, float y) {
    			super.clicked(event, x, y);
    			setGraphicProperties(GraphicType.NATIVE_HEAP, Color.BLUE);
    		}
    	});
		
	}
    
    private void setGraphicProperties(GraphicType graphicTypeParam, Color graphicColorParam) {
    	if(graphicType == graphicTypeParam) {
			visibleValueCount *= INCREASE_COEFFICIENT;
			if(visibleValueCount > MAX_VALUES_STORED_IN_LISTS) {
				visibleValueCount = DEFAULT_VISIBLE_VALUE_COUNT;
			}
		} else {
			visibleValueCount = DEFAULT_VISIBLE_VALUE_COUNT;
		}
    	intervalLabel.setText((visibleValueCount / DEFAULT_VISIBLE_VALUE_COUNT) + "m");
		graphicType = graphicTypeParam;
		graphicColor = graphicColorParam;
    }

	private float findMaxWidth() {
        float maxWidth = fpsLabel.getTextBounds().width;
        if ( javaHeapLabel.getTextBounds().width > maxWidth){
            maxWidth = javaHeapLabel.getTextBounds().width;
        }
        if ( nativeHeapLabel.getTextBounds().width > maxWidth){
            maxWidth = nativeHeapLabel.getTextBounds().width;
        }
        return maxWidth * NORMALIZATION_COEFFICIENT;
    }

    public void update(){
	    float widthBefore = findMaxWidth();
	    int fps = Gdx.graphics.getFramesPerSecond();
	    long javaHeapSize = Gdx.app.getJavaHeap() / ONE_KILOBYTE;
	    long nativeHeapSize = Gdx.app.getNativeHeap() / ONE_KILOBYTE;
	    fpsLabel.setText(FPS_LABEL + fps);
	    javaHeapLabel.setText(JAVA_HEAP_LABEL + javaHeapSize + KB);
	    nativeHeapLabel.setText(NATIVE_HEAP_LABEL + nativeHeapSize + KB);
	    maxWidth = findMaxWidth();
	    if ( maxWidth != widthBefore) {
	        float height = backgroundImage.getHeight();
	        setSize( maxWidth, height);
	        backgroundImage.setSize( maxWidth, height);
	        backgroundImage.invalidate();
	    }
	    updateResourceList(javaHeapSize, nativeHeapSize, fps);
    }

    public void show(){
        this.setVisible(true);
    }

    public void hide(){
        this.setVisible(false);
    }
    
    @Override
    public void draw(SpriteBatch batch, float parentAlpha) {
    	super.draw(batch, parentAlpha);
    	timeSinceLastUpdate += Gdx.graphics.getDeltaTime();
        if ( timeSinceLastUpdate >= updateIntervalInSecs){
            update();
            timeSinceLastUpdate = 0;
        }
        
        batch.end();
		
		Gdx.gl.glEnable(GL10.GL_BLEND);
		Gdx.gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		
		shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
		shapeRenderer.begin(ShapeType.Line);
		
		float baseY = getY() - graphicHeight;
		drawGraphicBoundaries(shapeRenderer, baseY);
		shapeRenderer.setColor(graphicColor);
		
		switch (graphicType) {
		case JAVA_HEAP:
			drawResourceGraphic(baseY, javaHeapSizesPerSecondList, maxJavaHeap);
			break;
		case NATIVE_HEAP:
			drawResourceGraphic(baseY, nativeHeapSizesPerSecondList, maxNativeHeap);
			break;
		case FPS:
			drawResourceGraphic(baseY, fpsList, maxFps);
			break;

		default:
			break;
		}
		
		shapeRenderer.end();
		Gdx.gl.glDisable(GL10.GL_BLEND);
		batch.begin();
        
    }
    
    private void drawResourceGraphic(float baseY, List<Long> resourcesList, float maxVal) {
    	int startIndex = Math.max(resourcesList.size() - visibleValueCount, 0);
		float xCoord = 0;
		float xInterval = maxWidth / visibleValueCount;
		float yInterval = graphicHeight / maxVal * (0.75f);
		for(int i=startIndex;i<resourcesList.size() - 1;i++) {
			xCoord+=xInterval;
			graphicPointBeginning.set(getX() + xCoord + resolutionHelper.getGameAreaPosition().x,baseY + resourcesList.get(i) * yInterval + resolutionHelper.getGameAreaPosition().y);
			graphicPointEnding.set(getX() + xCoord + xInterval + resolutionHelper.getGameAreaPosition().x,baseY + resourcesList.get(i + 1) * yInterval + resolutionHelper.getGameAreaPosition().y);
			shapeRenderer.line(graphicPointBeginning, graphicPointEnding);
		}
	}
    
	private void drawGraphicBoundaries(ShapeRenderer shapeRenderer, float baseY) {

    	shapeRenderer.setColor(Color.YELLOW);
    	
    	float normalizedY = getY();
    	leftTopGraphicBound.set(getX() + resolutionHelper.getGameAreaPosition().x, normalizedY + resolutionHelper.getGameAreaPosition().y);
    	leftBottomGraphicBound.set(getX() + resolutionHelper.getGameAreaPosition().x, baseY + resolutionHelper.getGameAreaPosition().y);
    	rightTopGraphicBound.set(getX() + resolutionHelper.getGameAreaPosition().x + maxWidth, normalizedY + resolutionHelper.getGameAreaPosition().y);
    	rightBottomGraphicBound.set(getX() + resolutionHelper.getGameAreaPosition().x + maxWidth, baseY + resolutionHelper.getGameAreaPosition().y);
    	
		shapeRenderer.line(leftTopGraphicBound, leftBottomGraphicBound);
		shapeRenderer.line(leftBottomGraphicBound, rightBottomGraphicBound);
		shapeRenderer.line(rightBottomGraphicBound, rightTopGraphicBound);
		shapeRenderer.line(rightTopGraphicBound, leftTopGraphicBound);
		
    }

	@Override
	public void build(
			Map<String, String> attributes,
			AssetsInterface assetsInterface,
			net.peakgames.libgdx.stagebuilder.core.assets.ResolutionHelper resolutionHelper,
			LocalizationService localizationService) {

	}
	
	private void updateResourceList(long javaHeapSize, long nativeHeapSize, long fps) {
		if(javaHeapSizesPerSecondList.size() == MAX_VALUES_STORED_IN_LISTS) {
			javaHeapSizesPerSecondList.remove(0);
			nativeHeapSizesPerSecondList.remove(0);
			fpsList.remove(0);
		}
		javaHeapSizesPerSecondList.add(javaHeapSize);
		nativeHeapSizesPerSecondList.add(nativeHeapSize);
		fpsList.add(fps);
		
		if(javaHeapSize > maxJavaHeap) {
			maxJavaHeap = javaHeapSize;
		}
		if(nativeHeapSize > maxNativeHeap) {
			maxNativeHeap = nativeHeapSize;
		}
		if(fps > maxFps) {
			maxFps = fps;
		}
	}
}
