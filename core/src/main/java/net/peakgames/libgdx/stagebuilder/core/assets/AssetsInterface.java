package net.peakgames.libgdx.stagebuilder.core.assets;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

public interface AssetsInterface {

    public BitmapFont getFont(String fontName);

    public TextureAtlas getTextureAtlas(String atlasName);

    public Texture getTexture(String textureName);

    public void addAssetConfiguration(String key, String fileName, Class<?> type);

    public void removeAssetConfiguration(String key);

    public void loadAssetsSync(String key);

    public void loadAssetsAsync(String key, AssetLoaderListener listener);

    public void unloadAssets(String key);

    public AssetManager getAssetMAnager();

    public Vector2 findBestResolution();

}
