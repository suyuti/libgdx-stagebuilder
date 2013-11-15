package net.peakgames.libgdx.stagebuilder.core.builder;

import java.util.Comparator;

import net.peakgames.libgdx.stagebuilder.core.assets.AssetsInterface;
import net.peakgames.libgdx.stagebuilder.core.assets.ResolutionHelper;
import net.peakgames.libgdx.stagebuilder.core.model.BaseModel;
import net.peakgames.libgdx.stagebuilder.core.services.LocalizationService;
import net.peakgames.libgdx.stagebuilder.core.xml.XmlModelBuilder;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.Align;

public abstract class ActorBuilder {

    public static final String TAG = ActorBuilder.class.getSimpleName();
    protected AssetsInterface assets;
    protected ResolutionHelper resolutionHelper;
    protected LocalizationService localizationService;
    public static final int NO_ALIGN = 0;


    public ActorBuilder(AssetsInterface assets, ResolutionHelper resolutionHelper, LocalizationService localizationService) {
        this.localizationService = localizationService;
        this.assets = assets;
        this.resolutionHelper = resolutionHelper;
    }

    public static int calculateAlignment(String s) {
        try {
            String[] sArray = s.split("\\|");
            int result = NO_ALIGN;
            for (String val : sArray) {
                if ("left".equals(val)) {
                    result |= Align.left;
                } else if ("right".equals(val)) {
                    result |= Align.right;
                } else if ("top".equals(val)) {
                    result |= Align.top;
                } else if ("bottom".equals(val)) {
                    result |= Align.bottom;
                } else if ("center".equals(val)) {
                    result |= Align.center;
                }
            }
            return result;
        } catch (Exception e) {
            //ignore
        }
        return Align.left;
    }

    public abstract Actor build(BaseModel model);

    /**
     * Width & height properties are updated by normalizeModelSize method.
     *
     * @param model model
     * @param actor actor
     */
    protected void setBasicProperties(BaseModel model, Actor actor) {
        actor.setBounds(
                model.getX() * resolutionHelper.getPositionMultiplier(),
                model.getY() * resolutionHelper.getPositionMultiplier(),
                model.getWidth(),
                model.getHeight());
        
        if (model.getScale() != 1) {
            actor.setScale(model.getScale(), model.getScale());
        } else {
            actor.setScaleX(model.getScaleX());
            actor.setScaleY(model.getScaleY());
        }
        actor.setZIndex(model.getzIndex());
        actor.setVisible(model.isVisible());

        if (model.getColor() != null) {
            actor.setColor(Color.valueOf(model.getColor()));
        }

        if (model.getRotation() != 0) {
            actor.setOrigin(actor.getWidth() / 2, actor.getHeight() / 2);
            actor.setRotation(model.getRotation());
        }

        if (model.getOriginX() != 0) {
            actor.setOriginX(model.getOriginX());
        }

        if (model.getOriginY() != 0) {
            actor.setOriginY(model.getOriginY());
        }

        actor.setName(model.getName());
        Vector2 screenPos = calculateScreenPosition(model.getScreenAlignment(), model);
        if (screenPos != null) {
            actor.setPosition(screenPos.x, screenPos.y);
        }
    }

    public Vector2 calculateScreenPosition(BaseModel.ScreenAlign screenAlign, BaseModel model) {
        if (screenAlign == null) {
            return null;
        }
        float y = model.getY() * resolutionHelper.getPositionMultiplier();
        float x = model.getX() * resolutionHelper.getPositionMultiplier();

        switch (screenAlign) {
            case top:
                //after building all actors stage position will be set to gameAreaPosition.
                y = resolutionHelper.getScreenHeight() - model.getScaledHeight() - resolutionHelper.getGameAreaPosition().y;
                y = y - model.getScreenPaddingTop() * resolutionHelper.getPositionMultiplier();
                break;
            case bottom:
                y = -resolutionHelper.getGameAreaPosition().y;
                y = y + model.getScreenPaddingBottom() * resolutionHelper.getPositionMultiplier();
                break;

            case left:
                x = -resolutionHelper.getGameAreaPosition().x;
                x = x + model.getScreenPaddingLeft() * resolutionHelper.getPositionMultiplier();
                break;

            case right:
                x = resolutionHelper.getScreenWidth() - model.getScaledWidth() - resolutionHelper.getGameAreaPosition().x;
                x = x - model.getScreenPaddingRight() * resolutionHelper.getPositionMultiplier();
                break;
            default:
                break;
        }
        return new Vector2(x, y);
    }

    /**
     * Target screen resolution(800x480) may be smaller than selected asset resolution(1280x800) for
     * device screen resolution 1280x800. sizeMultiplier in this case will be "1". If there is size
     * information in layout xml file generated for 800x480 target screen resolution, size multiplier value "1" will
     * not work correctly. Position multiplier (1280 / 800 = 1.6) must be used in such cases for providing correct scaling.
     *
     * @param defaultWidth  if width of the actor is not specified in layout file then defaultWidth is multiplied with sizeMultiplier
     * @param defaultHeight if height of the actor is not specified in layout file then defaultHeight is multiplied with sizeMultiplier
     */
    protected void normalizeModelSize(BaseModel model, float defaultWidth, float defaultHeight) {
        float width = model.getWidth();
        float height = model.getHeight();
        if (width == 0) {
            model.setWidth(defaultWidth * resolutionHelper.getSizeMultiplier());
        } else {
            model.setWidth(width * resolutionHelper.getPositionMultiplier());
        }
        if (height == 0) {
            model.setHeight(defaultHeight * resolutionHelper.getSizeMultiplier());
        } else {
            model.setHeight(height * resolutionHelper.getPositionMultiplier());
        }
    }

    public String getLocalizedString(String s) {
        if (s.startsWith(XmlModelBuilder.LOCALIZED_STRING_PREFIX)) {
        	s = s.replace(XmlModelBuilder.LOCALIZED_STRING_PREFIX, "");
            return localizationService.getString(s);
        } else {
            return s;
        }
    }

    public static final class ZIndexComparator implements Comparator<BaseModel> {
        @Override
        public int compare(BaseModel model1, BaseModel model2) {
            return model1.getzIndex() - model2.getzIndex();
        }
    }
}
