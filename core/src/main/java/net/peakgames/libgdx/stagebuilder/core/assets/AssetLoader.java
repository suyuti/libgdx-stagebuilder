package net.peakgames.libgdx.stagebuilder.core.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.BitmapFontLoader;
import com.badlogic.gdx.assets.loaders.SoundLoader;
import com.badlogic.gdx.assets.loaders.TextureAtlasLoader;
import com.badlogic.gdx.assets.loaders.TextureLoader;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class AssetLoader {

    final private AssetManager assetManager;
    /**
     * Key is used for querying loaded assets.
     * Multiple asset files can be mapped to a single key.
     */
    private Map<String, List<AssetConfig>> assetsConfiguration = new HashMap<String, List<AssetConfig>>();

    public AssetLoader(AssetManager assetManager) {
        this.assetManager = assetManager;
    }

    /**
     * @param assetKey this key is used to load mapped assets.
     * @param fileName asset file name
     * @param type     asset type
     */
    public void addAssetConfiguration(String assetKey, String fileName, Class<?> type) {
        AssetConfig config = new AssetConfig(fileName, type);
        List<AssetConfig> types = assetsConfiguration.get(assetKey);
        if (types == null) {
            types = new LinkedList<AssetConfig>();
            assetsConfiguration.put(assetKey, types);
        }
        types.add(config);
    }

    public void loadAssetsAsync(String assetKey, AssetLoaderListener listener) {
        LoadedCallbackManager loadedCallbackManager = new LoadedCallbackManager();
        loadedCallbackManager.addAssetsLoadedListener(listener);
        loadAssets(assetKey, loadedCallbackManager);
    }

    public void loadAssetsSync(String assetKey) {
        //loadedCallbackmanager does not listen asset loading.
        loadAssets(assetKey, new LoadedCallbackManager() /*dummy*/);
        this.assetManager.finishLoading();
    }

    private void loadAssets(String screenName, LoadedCallbackManager loadedCallbackManager) {
        List<AssetConfig> list = assetsConfiguration.get(screenName);
        if (list != null) {
            for (AssetConfig config : list) {
                Class<?> type = config.getType();
                String fileName = config.getFileName();
                loadedCallbackManager.addFile(fileName);
                if (assetManager.isLoaded(fileName)) {
                    loadedCallbackManager.finishedLoading(assetManager, fileName, type);
                } else {
                    if (TextureAtlas.class.equals(type)) {
                        TextureAtlasLoader.TextureAtlasParameter param = new TextureAtlasLoader.TextureAtlasParameter();
                        param.loadedCallback = loadedCallbackManager;
                        assetManager.load(fileName, TextureAtlas.class, param);

                    } else if (Texture.class.equals(type)) {
                        TextureLoader.TextureParameter param = new TextureLoader.TextureParameter();
                        param.loadedCallback = loadedCallbackManager;
                        assetManager.load(fileName, Texture.class, param);

                    } else if (BitmapFont.class.equals(type)) {
                        BitmapFontLoader.BitmapFontParameter param = new BitmapFontLoader.BitmapFontParameter();
                        param.maxFilter = Texture.TextureFilter.Linear;
                        param.minFilter = Texture.TextureFilter.Linear;
                        param.loadedCallback = loadedCallbackManager;
                        assetManager.load(fileName, BitmapFont.class, param);

                    } else if (Sound.class.equals(type)) {
                        SoundLoader.SoundParameter param = new SoundLoader.SoundParameter();
                        param.loadedCallback = loadedCallbackManager;
                        assetManager.load(fileName, Sound.class, param);

                    } else {
                        Gdx.app.log("AssetLoader", "Unrecognized asset type.");
                    }
                }
            }
        }
    }

    public AssetManager getAssetManager() {
        return assetManager;
    }

    public void unloadAssets(String screenName) {
        List<AssetConfig> list = assetsConfiguration.get(screenName);
        if (list != null) {
            for (AssetConfig config : list) {
                assetManager.unload(config.getFileName());
            }
        }
    }

    public boolean isLoaded(String fileName) {
        return this.assetManager.isLoaded(fileName);
    }

    public void removeAssetConfiguration(String key) {
        this.assetsConfiguration.remove(key);
    }

    private static class AssetConfig {
        private String fileName;
        private Class<?> type;

        public AssetConfig(String fileName, Class<?> type) {
            super();
            this.fileName = fileName;
            this.type = type;
        }

        public String getFileName() {
            return fileName;
        }

        public Class<?> getType() {
            return type;
        }

        @Override
        public String toString() {
            return "AssetConfig [fileName=" + fileName + ", type=" + type + "]";
        }
    }
}
