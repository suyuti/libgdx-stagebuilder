package net.peakgames.libgdx.stagebuilder.core.builder;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import net.peakgames.libgdx.stagebuilder.core.assets.AssetsInterface;
import net.peakgames.libgdx.stagebuilder.core.assets.ResolutionHelper;
import net.peakgames.libgdx.stagebuilder.core.model.BaseModel;
import net.peakgames.libgdx.stagebuilder.core.model.TextButtonModel;
import net.peakgames.libgdx.stagebuilder.core.services.LocalizationService;

public class TextButtonBuilder extends ButtonBuilder {


    public TextButtonBuilder(AssetsInterface assets, ResolutionHelper resolutionHelper, LocalizationService localizationService) {
        super(assets, resolutionHelper, localizationService);
    }

    @Override
    public Actor build(BaseModel model) {
        TextButtonModel textButtonModel = (TextButtonModel) model;
        setTextures(textButtonModel);

        BitmapFont font = assets.getFont(textButtonModel.getFontName());

        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle(up, down, up, font);
        if (textButtonModel.getFontColor() != null) {
            style.fontColor = Color.valueOf(textButtonModel.getFontColor());
        }

        TextButton textButton = new TextButton(textButtonModel.getText(), style);
        setBasicProperties(model, textButton);
        normalizeActorSize(textButton, up.getMinWidth(), up.getMinHeight());

        float positionMultiplier = resolutionHelper.getPositionMultiplier();
        textButton.padBottom(textButtonModel.getLabelPaddingBottom() * positionMultiplier);
        textButton.padTop(textButtonModel.getLabelPaddingTop() * positionMultiplier);
        textButton.padRight(textButtonModel.getLabelPaddingRight() * positionMultiplier);
        textButton.padLeft(textButtonModel.getLabelPaddingLeft() * positionMultiplier);

        Label label = textButton.getLabel();
        //TODO fontScale buyuk ise text button'un (vertical) ortasinda cikmiyor.
        //setBasicProperties(model, label);
        //normalizeActorSize(label, up.getMinWidth(), up.getMinHeight());
        //label.setWidth(textButton.getWidth());
        //label.setHeight(textButton.getHeight());
        //label.setX(textButton.getX());
        //label.setY(textButton.getY());

        if (textButtonModel.getFontScale() != 1) {
            label.setFontScale(font.getScaleX() * textButtonModel.getFontScale());
        }

        return textButton;
    }
}
