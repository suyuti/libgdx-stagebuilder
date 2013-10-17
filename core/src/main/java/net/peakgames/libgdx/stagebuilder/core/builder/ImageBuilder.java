package net.peakgames.libgdx.stagebuilder.core.builder;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import net.peakgames.libgdx.stagebuilder.core.assets.AssetsInterface;
import net.peakgames.libgdx.stagebuilder.core.assets.ResolutionHelper;
import net.peakgames.libgdx.stagebuilder.core.model.BaseModel;
import net.peakgames.libgdx.stagebuilder.core.model.ImageModel;
import net.peakgames.libgdx.stagebuilder.core.services.LocalizationService;

public class ImageBuilder extends ActorBuilder {


    public ImageBuilder(AssetsInterface assets, ResolutionHelper resolutionHelper, LocalizationService localizationService) {
        super(assets, resolutionHelper, localizationService);
    }

    @Override
    public Actor build(BaseModel model) {
        ImageModel imageModel = (ImageModel) model;
        Image image;
        if (imageModel.getTextureSrc() != null) {
            image = createFromTexture(imageModel);
        } else {
            image = createFromTextureAtlas(imageModel);
        }

        normalizeModelSize(imageModel,
                image.getDrawable().getMinWidth(),
                image.getDrawable().getMinHeight());


        setBasicProperties(model, image);

        if (ImageModel.TYPE_BACKGROUND.equals(imageModel.getType())) {
            updateBackgroundImagePosition(image);
        }

        return image;
    }

    private void updateBackgroundImagePosition(Image image) {
        Vector2 selectedResolution = assets.findBestResolution();
        Vector2 backGroundSize = resolutionHelper.calculateBackgroundSize(selectedResolution.x, selectedResolution.y);
        Vector2 backGroundPosition = resolutionHelper.calculateBackgroundPosition(image.getWidth(), image.getHeight());
        Vector2 gameAreaPosition = resolutionHelper.getGameAreaPosition();
          /*
  		 * stage root position is always set to gameAreaPosition.
  		 * Since the bg image is also inside the root group, bg image position should be updated.
		 */
        image.setPosition(backGroundPosition.x - gameAreaPosition.x, backGroundPosition.y - gameAreaPosition.y);
        image.setSize(backGroundSize.x, backGroundSize.y);
    }

    private Image createFromTexture(ImageModel imageModel) {
        TextureRegion textureRegion = new TextureRegion(assets.getTexture(imageModel.getTextureSrc()));
        return new Image(textureRegion);
    }

    private Image createFromTextureAtlas(ImageModel imageModel) {
        TextureAtlas textureAtlas = assets.getTextureAtlas(imageModel.getAtlasName());
        TextureAtlas.AtlasRegion atlasRegion = textureAtlas.findRegion(imageModel.getFrame());
        return new Image(atlasRegion);
    }

}
