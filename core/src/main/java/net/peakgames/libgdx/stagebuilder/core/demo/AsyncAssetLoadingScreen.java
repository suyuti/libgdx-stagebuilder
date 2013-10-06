package net.peakgames.libgdx.stagebuilder.core.demo;

import net.peakgames.libgdx.stagebuilder.core.AbstractGame;

public class AsyncAssetLoadingScreen extends DemoScreen {
    public AsyncAssetLoadingScreen(AbstractGame game) {
        super(game);
    }

    @Override
    public void unloadAssets() {
        super.unloadAssets();
        game.getAssetsInterface().unloadAssets("async_asset_loading_test");
    }
}
