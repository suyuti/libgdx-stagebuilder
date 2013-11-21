package net.peakgames.libgdx.stagebuilder.core.builder;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import net.peakgames.libgdx.stagebuilder.core.assets.AssetsInterface;
import net.peakgames.libgdx.stagebuilder.core.assets.ResolutionHelper;
import net.peakgames.libgdx.stagebuilder.core.model.BaseModel;
import net.peakgames.libgdx.stagebuilder.core.model.SelectBoxModel;
import net.peakgames.libgdx.stagebuilder.core.services.LocalizationService;

public class SelectBoxBuilder extends ActorBuilder {

    public static final Color DEFAULT_COLOR = Color.BLACK;
    public static final String DELIMITER = ";";
    public static final int DEFAULT_PADDING_LEFT = 5;
    public static final int DEFAULT_PADDING_RIGHT = 5;

    public SelectBoxBuilder(AssetsInterface assets, ResolutionHelper resolutionHelper, LocalizationService localizationService) {
        super(assets, resolutionHelper, localizationService);
    }

    @Override
    public Actor build(BaseModel model) {
        SelectBoxModel selectBoxModel = (SelectBoxModel)model;

        TextureAtlas textureAtlas = assets.getTextureAtlas(selectBoxModel.getAtlasName());

        TextureRegionDrawable hScroll = new TextureRegionDrawable(textureAtlas.findRegion(selectBoxModel.getSelection()));
        TextureRegionDrawable hScrollKnob = new TextureRegionDrawable(textureAtlas.findRegion(selectBoxModel.getSelection()));
        TextureRegionDrawable vScroll = new TextureRegionDrawable(textureAtlas.findRegion(selectBoxModel.getSelection()));
        TextureRegionDrawable vScrollKnob = new TextureRegionDrawable(textureAtlas.findRegion(selectBoxModel.getSelection()));

        TextureRegionDrawable selection = new TextureRegionDrawable(textureAtlas.findRegion(selectBoxModel.getSelection()));

        TextureRegionDrawable selectBoxBackground = new TextureRegionDrawable(textureAtlas.findRegion(selectBoxModel.getSelectionBackground()));

        NinePatchDrawable drawable = new NinePatchDrawable();
        NinePatch n = new NinePatch(textureAtlas.findRegion(selectBoxModel.getBackground()), 17, 17, 17, 17);
        drawable.setPatch(n);
        ScrollPane.ScrollPaneStyle scrollPaneStyle = new ScrollPane.ScrollPaneStyle(drawable, hScroll, hScrollKnob, vScroll, vScrollKnob);

        BitmapFont font = assets.getFont(selectBoxModel.getFontName());
        Color fontColor = selectBoxModel.getFontColor()==null ? DEFAULT_COLOR : Color.valueOf(selectBoxModel.getFontColor());
        Color fontColorSelected = selectBoxModel.getFontColorSelected()==null ? DEFAULT_COLOR : Color.valueOf(selectBoxModel.getFontColorSelected());
        Color fontColorUnselected = selectBoxModel.getFontColorUnselected()==null ? DEFAULT_COLOR : Color.valueOf(selectBoxModel.getFontColorUnselected());

        selection.setLeftWidth(selectBoxModel.getPaddingLeft()==0 ? DEFAULT_PADDING_LEFT : selectBoxModel.getPaddingLeft());
        selection.setRightWidth(selectBoxModel.getPaddingRight()==0 ? DEFAULT_PADDING_RIGHT : selectBoxModel.getPaddingRight());
        selection.setTopHeight(5);
        selection.setBottomHeight(5);
        com.badlogic.gdx.scenes.scene2d.ui.List.ListStyle listStyle = new com.badlogic.gdx.scenes.scene2d.ui.List.ListStyle(font, fontColorSelected, fontColorUnselected, selection);

        selectBoxBackground.setLeftWidth(selectBoxModel.getPaddingLeft());
        selectBoxBackground.setRightWidth(selectBoxModel.getPaddingRight());
        SelectBox.SelectBoxStyle style = new SelectBox.SelectBoxStyle(font, fontColor, selectBoxBackground, scrollPaneStyle, listStyle);

        String[] values = new String[0];
        if (selectBoxModel.getValue() != null && !selectBoxModel.getValue().isEmpty()) {
            values = selectBoxModel.getValue().split(DELIMITER);
        }

        SelectBox selectBox = new SelectBox(values, style);
        selectBox.setName(selectBoxModel.getName());

        normalizeModelSize(selectBoxModel, 0, 0);
        setBasicProperties(selectBoxModel, selectBox);

        selectBox.setBounds(selectBoxModel.getX(), selectBoxModel.getY(), selectBoxBackground.getRegion().getRegionWidth(), selectBoxBackground.getRegion().getRegionHeight());

        return selectBox;
    }

}
