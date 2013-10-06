package net.peakgames.libgdx.stagebuilder.core.demo.widgets;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import net.peakgames.libgdx.stagebuilder.core.ICustomWidget;
import net.peakgames.libgdx.stagebuilder.core.assets.AssetsInterface;
import net.peakgames.libgdx.stagebuilder.core.assets.ResolutionHelper;
import net.peakgames.libgdx.stagebuilder.core.services.LocalizationService;

import java.util.Map;

public class ProgressBarWidget extends WidgetGroup implements ICustomWidget {

    private String atlasName;
    private String backgroundImageFrame;
    private String progressImageFrame;
    private int ninePatchLeft;
    private int ninePatchRight;
    private float progressImagePaddingLeft;
    private float progressImagePaddingRight;
    private String text;
    private String fontName;
    private Image backgroundImage;
    private TextButton progressBarNinePatch;
    private Label progressText;
    private float progressBarLength;
    private float progressBarOriginalWidth;
    /**
     * bir tam tur donmesi icin gecen sure.
     */
    private float completeDuration = 4;
    private float duration = 0f;

    @Override
    public void build(Map<String, String> attributes, AssetsInterface assetsInterface, ResolutionHelper resolutionHelper, LocalizationService localizationService) {
        float sizeMult = resolutionHelper.getSizeMultiplier();
        float posMult = resolutionHelper.getPositionMultiplier();

        readConfiguration(attributes, sizeMult);

        prepareBackgroundImage(assetsInterface, sizeMult);

        prepareNinepatch(assetsInterface, sizeMult);

        prepareLabel(assetsInterface);

        this.setName(attributes.get("name"));
        float x = Float.valueOf(attributes.get("x")) * posMult;
        float y = Float.valueOf(attributes.get("y")) * posMult;
        this.setPosition(x, y);

        this.progressBarLength = this.backgroundImage.getWidth() - (this.progressImagePaddingLeft + this.progressImagePaddingRight);

        addActor(this.backgroundImage);
        addActor(this.progressBarNinePatch);
        addActor(this.progressText);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (!isVisible()) {
            return;
        }
        duration = duration + delta;
        float width = (duration * progressBarLength) / completeDuration;
        this.progressBarNinePatch.setWidth(width);
        if (this.progressBarNinePatch.getWidth() > this.progressBarLength) {
            this.progressBarNinePatch.setWidth(progressBarOriginalWidth);
            duration = 0f;
        }
    }

    private void prepareLabel(AssetsInterface assets) {
        Label.LabelStyle labelStyle = new Label.LabelStyle(assets.getFont(this.fontName), Color.WHITE);
        if (this.text != null) {
            this.progressText = new Label(this.text, labelStyle);
        } else {
            this.progressText = new Label("", labelStyle);
        }
        this.progressText.setWidth(this.backgroundImage.getWidth());
        this.progressText.setAlignment(Align.center);
        this.progressText.setPosition(this.backgroundImage.getX(), (this.backgroundImage.getHeight() - this.progressText.getHeight()) * 0.5f);
    }

    private void prepareNinepatch(AssetsInterface assets, float sizeMult) {
        TextureAtlas atlas = assets.getTextureAtlas(this.atlasName);
        TextureAtlas.AtlasRegion ninePatchTexture = atlas.findRegion(progressImageFrame);
        NinePatch patch = new NinePatch(ninePatchTexture, this.ninePatchLeft, this.ninePatchRight, 0, 0);
        NinePatchDrawable ninePatchDrawable = new NinePatchDrawable(patch);
        ninePatchDrawable.setMinWidth(ninePatchDrawable.getMinWidth() * sizeMult);
        ninePatchDrawable.setMinHeight(ninePatchDrawable.getMinHeight() * sizeMult);
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle(ninePatchDrawable, ninePatchDrawable, ninePatchDrawable, assets.getFont(this.fontName));
        this.progressBarNinePatch = new TextButton("", textButtonStyle);
        this.progressBarNinePatch.setWidth(ninePatchTexture.originalWidth);
        this.progressBarOriginalWidth = ninePatchTexture.originalWidth;
        this.progressBarNinePatch.setX(this.progressImagePaddingLeft);
    }

    private void prepareBackgroundImage(AssetsInterface assets, float sizeMult) {
        TextureAtlas atlas = assets.getTextureAtlas(this.atlasName);
        this.backgroundImage = new Image(atlas.findRegion(this.backgroundImageFrame));
        this.backgroundImage.setWidth(this.backgroundImage.getWidth() * sizeMult);
        this.backgroundImage.setHeight(this.backgroundImage.getHeight() * sizeMult);
    }

    private void readConfiguration(Map<String, String> attributeMap, float sizeMult) {
        this.atlasName = attributeMap.get("atlas");
        this.backgroundImageFrame = attributeMap.get("bgImageFrame");
        this.progressImageFrame = attributeMap.get("progressImageFrame");
        this.ninePatchLeft = Integer.valueOf(attributeMap.get("ninePatchLeft"));
        this.ninePatchRight = Integer.valueOf(attributeMap.get("ninePatchRight"));
        this.progressImagePaddingLeft = Float.valueOf(attributeMap.get("progressImagePaddingLeft")) * sizeMult;
        this.progressImagePaddingRight = Float.valueOf(attributeMap.get("progressImagePaddingRight")) * sizeMult;
        this.text = attributeMap.get("text");
        this.completeDuration = Float.valueOf(attributeMap.get("duration"));
        this.fontName = attributeMap.get("fontName");
    }

}
