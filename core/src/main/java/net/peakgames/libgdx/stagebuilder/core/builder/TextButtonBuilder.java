package net.peakgames.libgdx.stagebuilder.core.builder;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.esotericsoftware.tablelayout.Cell;
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

        TextButton textButton = new TextButton(getLocalizedString(textButtonModel.getText()).replace(ESCAPE_NEW_LINE, String.format("%n")), style);
        normalizeModelSize(textButtonModel, up.getMinWidth(), up.getMinHeight());
        setBasicProperties(textButtonModel, textButton);

        float positionMultiplier = resolutionHelper.getPositionMultiplier();
        textButton.padBottom(textButtonModel.getLabelPaddingBottom() * positionMultiplier);
        textButton.padTop(textButtonModel.getLabelPaddingTop() * positionMultiplier);
        textButton.padRight(textButtonModel.getLabelPaddingRight() * positionMultiplier);
        textButton.padLeft(textButtonModel.getLabelPaddingLeft() * positionMultiplier);

        Label label = textButton.getLabel();
        label.setWrap(textButtonModel.isWrap());
        if(textButtonModel.getAlignment() != null) {
        	int alignment = calculateAlignment(textButtonModel.getAlignment());
        	label.setAlignment(alignment);
        }
        Cell labelCell = textButton.getLabelCell();
        if (textButtonModel.getFontScale() != 1) {
            labelCell.height(textButton.getHeight());
            labelCell.bottom();
            label.setFontScale(font.getScaleX() * textButtonModel.getFontScale());
            label.setAlignment(Align.center);
        }

        return textButton;
    }
}
