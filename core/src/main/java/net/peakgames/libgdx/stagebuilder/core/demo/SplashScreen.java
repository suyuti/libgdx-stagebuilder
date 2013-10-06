package net.peakgames.libgdx.stagebuilder.core.demo;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import net.peakgames.libgdx.stagebuilder.core.AbstractGame;
import net.peakgames.libgdx.stagebuilder.core.assets.AssetLoaderListener;
import net.peakgames.libgdx.stagebuilder.core.assets.AssetsInterface;

public class SplashScreen implements Screen {

    private static final float ANIMATION_DURATION = 1.5f;// seconds
    private static final float SPLASH_LOGO_MIN_SEEN_DURATION = 2000; //miliseconds
    private AbstractGame game;
    private SpriteBatch spriteBatch;
    private Texture peakLogo;
    private float logoX;
    private float logoY;
    private AssetManager assetManager;
    private boolean loadAssets = false;
    private boolean hideSplashScreen = false;
    private float elapsedTime;
    private float logoMoveAnimationDistance;
    private float originalLogoX;
    private long startTime;

    public SplashScreen(AbstractGame game) {
        this.game = game;
        this.assetManager = game.getAssetsInterface().getAssetMAnager();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        spriteBatch.begin();
        spriteBatch.draw(peakLogo, logoX, logoY);
        spriteBatch.end();
        if (this.loadAssets) {
            this.assetManager.update();
        }
        if (hideSplashScreen && splashLogoSeenEnough()) {
            elapsedTime = elapsedTime + delta;
            float percentage = elapsedTime / ANIMATION_DURATION;
            percentage = Interpolation.swing.apply(percentage);

            if (elapsedTime > ANIMATION_DURATION) {
                MenuScreen menuScreen = new MenuScreen(game);
                game.setScreen(menuScreen);
                percentage = 1;
            }

            logoX = originalLogoX + logoMoveAnimationDistance * percentage;
        }
    }

    private boolean splashLogoSeenEnough() {
        return (System.currentTimeMillis() - startTime) > SPLASH_LOGO_MIN_SEEN_DURATION;
    }

    @Override
    public void show() {
        AssetsInterface assets = game.getAssetsInterface();
        spriteBatch = new SpriteBatch();
        loadPeakLogo();
        String assetsKey = "SplashScreen";
        assets.addAssetConfiguration(assetsKey, "libgdx-logo.png", Texture.class);
        assets.addAssetConfiguration(assetsKey, "common.atlas", TextureAtlas.class);
        assets.addAssetConfiguration(assetsKey, "default_font.fnt", BitmapFont.class);

        assets.loadAssetsAsync(assetsKey, new AssetLoaderListener() {
            @Override
            public void onAssetsLoaded() {
                hideSplashScreen = true;
            }
        });
        this.loadAssets = true;
        startTime = System.currentTimeMillis();
    }

    private void loadPeakLogo() {
        String path = getImagesPath();
        peakLogo = new Texture(Gdx.files.internal(path + "/libgdx-logo.png"));
        logoX = (Gdx.graphics.getWidth() - peakLogo.getWidth()) * 0.5f;
        logoY = (Gdx.graphics.getHeight() - peakLogo.getHeight()) * 0.5f;
        logoMoveAnimationDistance = Gdx.graphics.getWidth() - logoX;
        originalLogoX = logoX;
    }

    private String getImagesPath() {
        Vector2 res = game.getBestResolution();
        return "images/" + (int) res.x + "x" + (int) res.y + "/";
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
    }
}
