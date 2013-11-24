package net.peakgames.libgdx.stagebuilder.core.builder;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import net.peakgames.libgdx.stagebuilder.core.assets.AssetsInterface;
import net.peakgames.libgdx.stagebuilder.core.assets.ResolutionHelper;
import net.peakgames.libgdx.stagebuilder.core.model.BaseModel;
import net.peakgames.libgdx.stagebuilder.core.model.SliderModel;
import net.peakgames.libgdx.stagebuilder.core.services.LocalizationService;

public class SliderBuilder extends ActorBuilder{

    private TextureRegionDrawable background;
    private TextureRegionDrawable knob;

    public SliderBuilder(AssetsInterface assets, ResolutionHelper resolutionHelper, LocalizationService localizationService) {
        super(assets, resolutionHelper, localizationService);
    }

    @Override
    public Actor build(BaseModel model) {
        SliderModel sliderModel = (SliderModel) model;
        setTextures(sliderModel);
        Slider slider = new Slider( sliderModel.getMinValue(),
                sliderModel.getMaxValue(),
                sliderModel.getStepSize(),
                sliderModel.isVertical(),
                new Slider.SliderStyle( background, knob));
        normalizeModelSize( sliderModel, background.getMinWidth(), background.getMinHeight());
        updateDrawableSize( knob);
        setBasicProperties( model, slider);
        return slider;
    }

    protected void setTextures( SliderModel sliderModel){
        if ( sliderModel.getTextureSrcBackground() != null) {
            this.background = new TextureRegionDrawable( new TextureRegion (new Texture( sliderModel.getTextureSrcBackground())));
            this.knob = new TextureRegionDrawable( new TextureRegion( new Texture( sliderModel.getTextureSrcKnob())));
        } else {
            TextureAtlas textureAtlas = assets.getTextureAtlas(sliderModel.getAtlasName());
            this.background = new TextureRegionDrawable(textureAtlas.findRegion( sliderModel.getFrameBackground()));
            this.knob = new TextureRegionDrawable(textureAtlas.findRegion(sliderModel.getFrameKnob()));
        }
    }

    protected void updateDrawableSize( TextureRegionDrawable textureRegionDrawable){
        float sizeMultiplier = resolutionHelper.getSizeMultiplier();
        textureRegionDrawable.setMinWidth( textureRegionDrawable.getMinWidth() * sizeMultiplier);
        textureRegionDrawable.setMinHeight( textureRegionDrawable.getMinHeight() * sizeMultiplier);

    }
}
