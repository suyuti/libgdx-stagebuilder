package net.peakgames.libgdx.stagebuilder.core.demo.widgets;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import net.peakgames.libgdx.stagebuilder.core.ICustomWidget;
import net.peakgames.libgdx.stagebuilder.core.assets.AssetsInterface;
import net.peakgames.libgdx.stagebuilder.core.assets.ResolutionHelper;
import net.peakgames.libgdx.stagebuilder.core.services.LocalizationService;

import java.util.Map;

public class LoadingWidget extends WidgetGroup implements ICustomWidget {
    private Image foreground;

    @Override
    public void build(Map<String, String> attributes, AssetsInterface assetsInterface, ResolutionHelper resolutionHelper, LocalizationService localizationService) {
        TextureAtlas atlas = assetsInterface.getTextureAtlas("common.atlas");
        Image background = new Image(atlas.findRegion("loading_circle_background"));
        foreground = new Image(atlas.findRegion("loading_circle_foreground"));
        foreground.setOrigin(foreground.getWidth() / 2, foreground.getHeight() / 2);

        Label.LabelStyle labelStyle = new Label.LabelStyle(assetsInterface.getFont("default_font.fnt"), Color.YELLOW);
        Label label = new Label("Loading...", labelStyle);
        label.setFontScale(0.75f);
        label.setWidth(background.getWidth());
        label.setHeight(background.getHeight());
        label.setAlignment(Align.center);

        addActor(background);
        addActor(foreground);
        addActor(label);

        setX((resolutionHelper.getGameAreaBounds().x - foreground.getWidth()) * 0.5f);
        setY((resolutionHelper.getGameAreaBounds().y - foreground.getHeight()) * 0.5f);
    }

    @Override
    public void act(float delta) {
        if (isVisible()) {
            //360" in a second
            foreground.rotate(delta * 360);
        }
    }
}
