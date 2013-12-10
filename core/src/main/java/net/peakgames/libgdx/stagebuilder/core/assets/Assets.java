package net.peakgames.libgdx.stagebuilder.core.assets;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

public class Assets implements AssetsInterface {

    private StageBuilderFileHandleResolver fileHandleResolver;
    private final ResolutionHelper resolutionHelper;
    private AssetManager assetManager;
    private AssetLoader assetLoader;

    public Assets(StageBuilderFileHandleResolver fileHandleResolver, ResolutionHelper resolutionHelper) {
        this.fileHandleResolver = fileHandleResolver;
        this.resolutionHelper = resolutionHelper;
        this.assetManager = new AssetManager(fileHandleResolver);
        this.assetLoader = new AssetLoader(this.assetManager);
    }

    @Override
    public BitmapFont getFont(String fontName) {
        BitmapFont bitmapFont = this.assetManager.get(fontName, BitmapFont.class);
        bitmapFont.setScale(resolutionHelper.getSizeMultiplier());
        return bitmapFont;
    }

    @Override
    public TextureAtlas getTextureAtlas(String atlasName) {
        return this.assetManager.get(atlasName, TextureAtlas.class);
    }

    @Override
    public Texture getTexture(String textureName) {
        return this.assetManager.get(textureName, Texture.class);
    }

    @Override
    public void addAssetConfiguration(String key, String fileName, Class<?> type) {
        this.assetLoader.addAssetConfiguration(key, fileName, type);
    }

    @Override
    public void removeAssetConfiguration(String key) {
        this.assetLoader.removeAssetConfiguration(key);
    }

    @Override
    public void loadAssetsSync(String key) {
        this.assetLoader.loadAssetsSync(key);
    }

    @Override
    public void loadAssetsAsync(String key, AssetLoaderListener listener) {
        this.assetLoader.loadAssetsAsync(key, listener);
    }

    @Override
    public void unloadAssets(String key) {
        this.assetLoader.unloadAssets(key);
    }

    @Override
    public AssetManager getAssetMAnager() {
        return this.assetManager;
    }

    @Override
    public Vector2 findBestResolution() {
        return this.fileHandleResolver.findBestResolution();
    }

}
