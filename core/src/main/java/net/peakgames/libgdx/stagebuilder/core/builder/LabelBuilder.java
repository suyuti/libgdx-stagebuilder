package net.peakgames.libgdx.stagebuilder.core.builder;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import net.peakgames.libgdx.stagebuilder.core.assets.AssetsInterface;
import net.peakgames.libgdx.stagebuilder.core.assets.ResolutionHelper;
import net.peakgames.libgdx.stagebuilder.core.model.BaseModel;
import net.peakgames.libgdx.stagebuilder.core.model.LabelModel;
import net.peakgames.libgdx.stagebuilder.core.services.LocalizationService;

public class LabelBuilder extends ActorBuilder {

    public static final Color DEFAULT_LABEL_COLOR = Color.WHITE;

    public LabelBuilder(AssetsInterface assets, ResolutionHelper resolutionHelper, LocalizationService localizationService) {
        super(assets, resolutionHelper, localizationService);
    }

    @Override
    public Actor build(BaseModel model) {
        LabelModel labelModel = (LabelModel) model;
        Color color = labelModel.getFontColor() == null ? DEFAULT_LABEL_COLOR : Color.valueOf(labelModel.getFontColor());
        Label.LabelStyle style = new Label.LabelStyle(assets.getFont(labelModel.getFontName()), color);
        Label label = new Label(labelModel.getText(), style);

        normalizeModelSize(labelModel, 0, 0);
        setBasicProperties(model, label);

        label.setAlignment(calculateAlignment(labelModel.getAlignment()));
        label.setWrap(labelModel.isWrap());
        if (labelModel.getFontScale() != 1) {
            label.setFontScale(label.getStyle().font.getScaleX() * labelModel.getFontScale());
        }

        return label;
    }

}
