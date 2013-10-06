package net.peakgames.libgdx.stagebuilder.core.demo;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import net.peakgames.libgdx.stagebuilder.core.AbstractGame;
import net.peakgames.libgdx.stagebuilder.core.assets.AssetLoaderListener;


public class MenuScreen extends DemoScreen {

    public MenuScreen(AbstractGame game) {
        super(game);
        addButtonListeners();
    }

    private void addButtonListeners() {
        findButton("button_screen").addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ButtonScreen buttonScreen = new ButtonScreen(game);
                game.addScreen(buttonScreen);
            }
        });

        findButton("image_screen").addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ImageScreen imageScreen = new ImageScreen(game);
                game.addScreen(imageScreen);
            }
        });

        findButton("label_screen").addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                LabelScreen labelScreen = new LabelScreen(game);
                game.addScreen(labelScreen);
            }
        });

        findButton("custom_widget_screen").addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                CustomWidgetScreen customWidgetScreen = new CustomWidgetScreen(game);
                game.addScreen(customWidgetScreen);
            }
        });


        findButton("asenkron_asset_loading_screen").addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                findActor("loading_widget").setVisible(true);
                String key = "async_asset_loading_test";
                game.getAssetsInterface().removeAssetConfiguration(key);
                game.getAssetsInterface().addAssetConfiguration(key, "large.atlas", TextureAtlas.class);
                game.getAssetsInterface().loadAssetsAsync(key, new AssetLoaderListener() {
                    @Override
                    public void onAssetsLoaded() {
                        findActor("loading_widget").setVisible(false);
                        AsyncAssetLoadingScreen screen = new AsyncAssetLoadingScreen(game);
                        game.addScreen(screen);
                    }
                });
            }
        });

        findButton("external_group_screen").addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ExternalGroupScreen externalGroupScreen = new ExternalGroupScreen(game);
                game.addScreen(externalGroupScreen);
            }
        });

    }

}
