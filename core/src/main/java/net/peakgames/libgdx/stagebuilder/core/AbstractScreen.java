package net.peakgames.libgdx.stagebuilder.core;

import java.util.Map;

import net.peakgames.libgdx.stagebuilder.core.builder.StageBuilder;
import net.peakgames.libgdx.stagebuilder.core.util.Utils;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public abstract class AbstractScreen implements Screen {

	public static final long SCREEN_REFRESH_CHECK_PERIOD_MS = 1000;
    private static final boolean keepAspectRatio = true;
    public final String TAG = getClass().getSimpleName();
    protected Graphics graphics;
    protected SpriteBatch spriteBatch;
    protected Stage stage;
    protected AbstractGame game;
    protected OrthographicCamera camera;
    private AssetManager assetManager;
    private long lastScreenRefreshCheckTimestamp = System.currentTimeMillis();
    private String layoutFileChecksum;
    
    /**
     * parameters map that is used to pass configuration data for screen.
     */
    protected Map<String, String> parameters;
	private StageBuilder stageBuilder;

    public AbstractScreen(AbstractGame game) {
        if (game == null) {
            return;
        }

        this.game = game;
        graphics = Gdx.graphics;

        createStage(game);

        this.assetManager = game.getAssetsInterface().getAssetMAnager();
    }

    private void createStage(AbstractGame game) {
        float width = game.getWidth();
        float height = game.getHeight();
        camera = new OrthographicCamera();
        
        stageBuilder = new StageBuilder(game.getAssetsInterface(), game.getResolutionHelper(), game.getLocalizationService());
        stage = stageBuilder.build(getFileName(), width, height, keepAspectRatio);

        Gdx.input.setInputProcessor(this.stage);


        stage.setCamera(camera);
        this.stage.setViewport(width, height, keepAspectRatio);
        Gdx.input.setInputProcessor(stage);

        spriteBatch = stage.getSpriteBatch();
        spriteBatch.setProjectionMatrix(camera.combined);
    }

    private String getFileName() {
        return this.getClass().getSimpleName() + ".xml";
    }

    public abstract void unloadAssets();
    /**
     * Stage is replaced with a new one, listeners should be updated.
     */
    public abstract void onStageReloaded();

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        this.stage.act(Gdx.graphics.getDeltaTime());
        this.stage.draw();

        this.assetManager.update();
        
        refreshScreenIfNecessary();
    }

    private void refreshScreenIfNecessary() {
    	if (Gdx.app.getType() == ApplicationType.Desktop) {
    		long now = System.currentTimeMillis();
    		if (now - lastScreenRefreshCheckTimestamp > SCREEN_REFRESH_CHECK_PERIOD_MS) {
    			//check file modified date
    			String currentChecksum = calculateLayoutFileChecksum();
    			if ( ! currentChecksum.equals(this.layoutFileChecksum)) {
    				//file changed, refresh screen...
    				Gdx.app.log(TAG, "Layout file updated. Reloading stage...");
    				reloadStage();
    				this.layoutFileChecksum = currentChecksum;
    			}
    			lastScreenRefreshCheckTimestamp = now;
    		}
    	}		
	}

	@Override
    public void resize(int newWidth, int newHeight) {
        Gdx.app.log(TAG, "resize " + newWidth + " x " + newHeight);
        reloadStage();
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(this.stage);
        Gdx.app.log(TAG, "show");
        stage.getRoot().getColor().a = 0;
        stage.addAction(Actions.fadeIn(0.3f));
        layoutFileChecksum = calculateLayoutFileChecksum();
    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
        Gdx.app.log(TAG, "hide");
    }

    @Override
    public void pause() {
        Gdx.app.log(TAG, "pause");
    }

    @Override
    public void resume() {
        Gdx.app.log(TAG, "resume");
    }

    @Override
    public void dispose() {
        Gdx.app.log(TAG, "dispose");
    }

    void setParameters(Map<String, String> parameters) {
        this.parameters = parameters;
    }

    public Image findImage(String name) {
        return (Image)findActor(name);
    }

    public Button findButton(String name) {
        return (Button)findActor(name);
    }
    
    public Label findLabel(String name) {
        return (Label)findActor(name);
    }

    public Actor findActor(String name) {
        return stage.getRoot().findActor(name);
    }

    public TextButton findTextButton(String name) {
        return (TextButton) stage.getRoot().findActor(name);
    }

    public StageBuilder getStageBuilder() {
        return stageBuilder;
    }

    private String calculateLayoutFileChecksum() {
		FileHandle fileHandle = stageBuilder.getLayoutFile(getFileName());
		return Utils.calculateMD5(fileHandle.read());		
    }
    
    private void reloadStage() {
    	createStage(game);
    	onStageReloaded();
    	
    }
}
