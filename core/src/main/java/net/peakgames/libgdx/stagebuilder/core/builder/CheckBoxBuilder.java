package net.peakgames.libgdx.stagebuilder.core.builder;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import net.peakgames.libgdx.stagebuilder.core.assets.AssetsInterface;
import net.peakgames.libgdx.stagebuilder.core.assets.ResolutionHelper;
import net.peakgames.libgdx.stagebuilder.core.model.BaseModel;
import net.peakgames.libgdx.stagebuilder.core.model.ButtonModel;
import net.peakgames.libgdx.stagebuilder.core.model.CheckBoxModel;
import net.peakgames.libgdx.stagebuilder.core.services.LocalizationService;

public class CheckBoxBuilder extends TextButtonBuilder {

    protected TextureRegionDrawable checkBoxOff;
    protected TextureRegionDrawable checkBoxOn;
    protected TextureRegionDrawable checkBoxOver;

    public CheckBoxBuilder(AssetsInterface assets, ResolutionHelper resolutionHelper, LocalizationService localizationService) {
        super(assets, resolutionHelper, localizationService);
    }

    @Override
    public Actor build(BaseModel model) {
        CheckBoxModel checkBoxModel = (CheckBoxModel) model;
        setTextures( checkBoxModel);
        String text = ( checkBoxModel.getText() == null) ? "" : getLocalizedString( checkBoxModel.getText()).replace("\\n", String.format("%n"));
        BitmapFont font = assets.getFont( checkBoxModel.getFontName());
        Color fontColor = Color.valueOf( checkBoxModel.getFontColor());
        CheckBox.CheckBoxStyle style = new CheckBox.CheckBoxStyle( checkBoxOff, checkBoxOn, font, fontColor);
        if ( this.checkBoxOver != null){
            style.checkboxOver = checkBoxOver;
        }
        CheckBox checkBox = new CheckBox( text, style);
        normalizeModelSize( checkBoxModel, checkBoxOff.getMinWidth(), checkBoxOff.getMinHeight());
        setBasicProperties( checkBoxModel, checkBox);
        setTextButtonProperties( checkBoxModel, font, checkBox);

        return checkBox;

    }

    @Override
    protected void setTextures(ButtonModel buttonModel) {
        CheckBoxModel checkBoxModel = (CheckBoxModel)buttonModel;
        if ( checkBoxModel.getTextureSrcCheckboxOff() != null){
            this.checkBoxOff = new TextureRegionDrawable( new TextureRegion( new Texture( checkBoxModel.getTextureSrcCheckboxOff())));
            this.checkBoxOn = new TextureRegionDrawable( new TextureRegion( new Texture( checkBoxModel.getTextureSrcCheckboxOn())));
            if ( checkBoxModel.getTextureSrcCheckboxOver() != null){
                this.checkBoxOver = new TextureRegionDrawable( new TextureRegion( new Texture( checkBoxModel.getTextureSrcCheckboxOver())));
            }

        }else{
            TextureAtlas textureAtlas = assets.getTextureAtlas( checkBoxModel.getAtlasName());
            this.checkBoxOff = new TextureRegionDrawable( textureAtlas.findRegion( checkBoxModel.getFrameCheckboxOff()));
            this.checkBoxOn = new TextureRegionDrawable( textureAtlas.findRegion( checkBoxModel.getFrameCheckboxOn()));
            if ( checkBoxModel.getFrameCheckboxOver() != null){
                this.checkBoxOver = new TextureRegionDrawable( textureAtlas.findRegion( checkBoxModel.getFrameCheckboxOver()));
            }

        }
    }
}
