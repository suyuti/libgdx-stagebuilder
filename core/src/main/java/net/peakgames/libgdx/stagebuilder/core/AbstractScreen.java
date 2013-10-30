package net.peakgames.libgdx.stagebuilder.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import net.peakgames.libgdx.stagebuilder.core.builder.StageBuilder;
import net.peakgames.libgdx.stagebuilder.core.demo.DemoLocalizationService;

public abstract class AbstractScreen implements Screen {

    private static final boolean keepAspectRatio = true;
    public final String TAG = getClass().getSimpleName();
    protected Graphics graphics;
    protected SpriteBatch spriteBatch;
    protected Stage stage;
    protected AbstractGame game;
    protected OrthographicCamera camera;
    private AssetManager assetManager;

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
        StageBuilder stageBuilder = new StageBuilder(game.getAssetsInterface(), game.getResolutionHelper(), new DemoLocalizationService());
        stage = stageBuilder.build(getFileName(), width, height, keepAspectRatio);

        Gdx.input.setInputProcessor(this.stage);

        camera = new OrthographicCamera();

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

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        this.stage.act(Gdx.graphics.getDeltaTime());
        this.stage.draw();

        this.assetManager.update();
    }

    @Override
    public void resize(int newWidth, int newHeight) {
        Gdx.app.log(TAG, "resize " + newWidth + " x " + newHeight);
        createStage(this.game);
        //TODO orientation degisimini burada yakaliyoruz. stage yeniden build edildigi icin listener'lari yeniden vermek lazim.
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(this.stage);
        Gdx.app.log(TAG, "show");
        stage.getRoot().getColor().a = 0;
        stage.addAction(Actions.fadeIn(0.3f));
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

    public Image findImage(String name) {
        return (Image) stage.getRoot().findActor(name);
    }

    public Button findButton(String name) {
        return (Button) stage.getRoot().findActor(name);
    }

    public Actor findActor(String name) {
        return stage.getRoot().findActor(name);
    }

    public TextButton findTextButton(String name) {
        return (TextButton) stage.getRoot().findActor(name);
    }
}
