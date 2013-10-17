package net.peakgames.libgdx.stagebuilder.core.builder;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import net.peakgames.libgdx.stagebuilder.core.assets.AssetsInterface;
import net.peakgames.libgdx.stagebuilder.core.assets.ResolutionHelper;
import net.peakgames.libgdx.stagebuilder.core.model.BaseModel;
import net.peakgames.libgdx.stagebuilder.core.model.ButtonModel;
import net.peakgames.libgdx.stagebuilder.core.services.LocalizationService;

public class ButtonBuilder extends ActorBuilder {

    protected TextureRegionDrawable up;
    protected TextureRegionDrawable down;
    protected TextureRegionDrawable disabled;

    public ButtonBuilder(AssetsInterface assets, ResolutionHelper resolutionHelper, LocalizationService localizationService) {
        super(assets, resolutionHelper, localizationService);
    }

    @Override
    public Actor build(BaseModel model) {
        ButtonModel buttonModel = (ButtonModel) model;
        setTextures(buttonModel);
        Button button = new Button(up, down);
        if (disabled != null) {
            button.getStyle().disabled = disabled;
        }
        normalizeModelSize(buttonModel, up.getMinWidth(), up.getMinHeight());
        setBasicProperties(model, button);
        return button;
    }

    protected void setTextures(ButtonModel buttonModel) {
        if (buttonModel.getTextureSrcUp() != null) {
            this.down = new TextureRegionDrawable(new TextureRegion(new Texture(buttonModel.getTextureSrcDown())));
            this.up = new TextureRegionDrawable(new TextureRegion(new Texture(buttonModel.getTextureSrcUp())));
            if (buttonModel.getTextureSrcDisabled() != null) {
                this.disabled = new TextureRegionDrawable(new TextureRegion(new Texture(buttonModel.getTextureSrcDisabled())));
            }
        } else {
            TextureAtlas textureAtlas = assets.getTextureAtlas(buttonModel.getAtlasName());
            this.down = new TextureRegionDrawable(textureAtlas.findRegion(buttonModel.getFrameDown()));
            this.up = new TextureRegionDrawable(textureAtlas.findRegion(buttonModel.getFrameUp()));
            if (buttonModel.getTextureSrcDisabled() != null) {
                this.disabled = new TextureRegionDrawable(textureAtlas.findRegion(buttonModel.getFrameDisabled()));
            }
        }
    }

}
