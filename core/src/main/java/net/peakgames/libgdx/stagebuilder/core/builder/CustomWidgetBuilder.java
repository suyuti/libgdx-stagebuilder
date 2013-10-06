package net.peakgames.libgdx.stagebuilder.core.builder;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import net.peakgames.libgdx.stagebuilder.core.assets.AssetsInterface;
import net.peakgames.libgdx.stagebuilder.core.assets.ResolutionHelper;
import net.peakgames.libgdx.stagebuilder.core.model.BaseModel;
import net.peakgames.libgdx.stagebuilder.core.model.CustomWidgetModel;
import net.peakgames.libgdx.stagebuilder.core.services.LocalizationService;

import java.lang.reflect.Method;
import java.util.Map;

public class CustomWidgetBuilder extends ActorBuilder {

    public CustomWidgetBuilder(AssetsInterface assets, ResolutionHelper resolutionHelper, LocalizationService localizationService) {
        super(assets, resolutionHelper, localizationService);
    }

    @Override
    public Actor build(BaseModel model) {
        try {
            CustomWidgetModel customWidgetModel = (CustomWidgetModel) model;
            Class<?> klass = Class.forName(customWidgetModel.getKlass());
            Object customWidget = klass.newInstance();
            Class<?>[] buildMethodParameterTypes = {
                    Map.class,
                    AssetsInterface.class,
                    ResolutionHelper.class,
                    LocalizationService.class
            };

            Method buildMethod = klass.getMethod("build", buildMethodParameterTypes);
            setBasicProperties(model, (Actor) customWidget);
            buildMethod.invoke(
                    customWidget,
                    customWidgetModel.getAttributeMap(),
                    this.assets,
                    this.resolutionHelper,
                    this.localizationService);

            return (Actor) customWidget;
        } catch (Exception e) {
            Gdx.app.log("GdxWidgets", "Failed to create custom widget.", e);
            return null;
        }

    }


}
