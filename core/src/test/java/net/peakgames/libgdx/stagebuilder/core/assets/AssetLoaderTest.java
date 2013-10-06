package net.peakgames.libgdx.stagebuilder.core.assets;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.SoundLoader;
import com.badlogic.gdx.audio.Sound;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class AssetLoaderTest {

    private boolean allAssetsAreLoaded = false;

    @BeforeClass
    public static void beforeClass() {
        GdxTestApp.launchApp();
    }

    @Test
    public void sync_loading() {
        LoadedCallbackManager manager = new LoadedCallbackManager();

        AssetManager assetManager = new AssetManager();
        SoundLoader.SoundParameter soundParameter = new SoundLoader.SoundParameter();
        soundParameter.loadedCallback = manager;

        assetManager.load("sound/1.mp3", Sound.class, soundParameter);
        assetManager.load("sound/2.mp3", Sound.class, soundParameter);
        assetManager.load("sound/3.mp3", Sound.class, soundParameter);

        manager.addFile("sound/1.mp3");
        manager.addFile("sound/2.mp3");
        manager.addFile("sound/3.mp3");
        this.allAssetsAreLoaded = false;
        manager.addAssetsLoadedListener(new AssetLoaderListener() {
            @Override
            public void onAssetsLoaded() {
                allAssetsAreLoaded = true;

            }
        });
        assetManager.finishLoading();
        assertTrue(allAssetsAreLoaded);
    }

    @Test
    public void async_loading() throws Exception {
        LoadedCallbackManager manager = new LoadedCallbackManager();

        final AssetManager assetManager = new AssetManager();
        SoundLoader.SoundParameter soundParameter = new SoundLoader.SoundParameter();
        soundParameter.loadedCallback = manager;

        assetManager.load("sound/1.mp3", Sound.class, soundParameter);
        assetManager.load("sound/2.mp3", Sound.class, soundParameter);
        assetManager.load("sound/3.mp3", Sound.class, soundParameter);

        manager.addFile("sound/1.mp3");
        manager.addFile("sound/2.mp3");
        manager.addFile("sound/3.mp3");
        this.allAssetsAreLoaded = false;
        manager.addAssetsLoadedListener(new AssetLoaderListener() {
            @Override
            public void onAssetsLoaded() {
                allAssetsAreLoaded = true;

            }
        });
        this.allAssetsAreLoaded = false;
        final CountDownLatch latch = new CountDownLatch(1);
        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean loaded = false;
                while (!loaded) {
                    assertFalse(allAssetsAreLoaded);
                    loaded = assetManager.update();
                }

                latch.countDown();
            }
        }).start();

        latch.await();
        // update thread has finished loading assets.
        assertTrue(allAssetsAreLoaded);
    }

    @Test
    public void load_assets_sync() {
        AssetManager assetManager = new AssetManager();
        AssetLoader assetLoader = new AssetLoader(assetManager);

        assetLoader.addAssetConfiguration("TestScreen", "sound/1.mp3", Sound.class);
        assetLoader.addAssetConfiguration("TestScreen", "sound/2.mp3", Sound.class);
        assetLoader.addAssetConfiguration("TestScreen", "sound/3.mp3", Sound.class);

        assertFalse(assetManager.isLoaded("sound/1.mp3"));
        assertFalse(assetManager.isLoaded("sound/2.mp3"));
        assertFalse(assetManager.isLoaded("sound/3.mp3"));

        assetLoader.loadAssetsSync("TestScreen");

        assertTrue(assetManager.isLoaded("sound/1.mp3"));
        assertTrue(assetManager.isLoaded("sound/2.mp3"));
        assertTrue(assetManager.isLoaded("sound/3.mp3"));
    }

    @Test
    public void load_assets_async() throws Exception {
        AssetManager assetManager = new AssetManager();
        final AssetLoader assetLoader = new AssetLoader(assetManager);

        assetLoader.addAssetConfiguration("TestScreen", "sound/1.mp3", Sound.class);
        assetLoader.addAssetConfiguration("TestScreen", "sound/2.mp3", Sound.class);
        assetLoader.addAssetConfiguration("TestScreen", "sound/3.mp3", Sound.class);

        assertFalse(assetManager.isLoaded("sound/1.mp3"));
        assertFalse(assetManager.isLoaded("sound/2.mp3"));
        assertFalse(assetManager.isLoaded("sound/3.mp3"));

        final CountDownLatch latch = new CountDownLatch(1);
        assetLoader.loadAssetsAsync("TestScreen", new AssetLoaderListener() {
            @Override
            public void onAssetsLoaded() {
                latch.countDown();
            }
        });
        assertFalse(assetManager.isLoaded("sound/1.mp3"));
        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean loaded = false;
                while (!loaded) {
                    loaded = assetLoader.getAssetManager().update();
                }
            }
        }).start();

        latch.await();
        assertTrue(assetManager.isLoaded("sound/1.mp3"));
        assertTrue(assetManager.isLoaded("sound/2.mp3"));
        assertTrue(assetManager.isLoaded("sound/3.mp3"));
    }

    @Test
    public void unload() {
        AssetManager assetManager = new AssetManager();
        AssetLoader assetLoader = new AssetLoader(assetManager);

        assetLoader.addAssetConfiguration("TestScreen", "sound/1.mp3", Sound.class);
        assertFalse(assetManager.isLoaded("sound/1.mp3"));
        assetLoader.loadAssetsSync("TestScreen");
        assertTrue(assetManager.isLoaded("sound/1.mp3"));

        assetLoader.unloadAssets("TestScreen");
        assertFalse(assetManager.isLoaded("sound/1.mp3"));
    }

}
