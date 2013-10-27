package net.peakgames.libgdx.stagebuilder.core.builder;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import net.peakgames.libgdx.stagebuilder.core.assets.AssetsInterface;
import net.peakgames.libgdx.stagebuilder.core.assets.ResolutionHelper;
import net.peakgames.libgdx.stagebuilder.core.model.BaseModel;
import net.peakgames.libgdx.stagebuilder.core.model.ExternalGroupModel;
import net.peakgames.libgdx.stagebuilder.core.services.LocalizationService;

public class ExternalGroupModelBuilder extends ActorBuilder {

    private final StageBuilder stageBuilder;

    public ExternalGroupModelBuilder(AssetsInterface assets, ResolutionHelper resolutionHelper, LocalizationService localizationService, StageBuilder stageBuilder) {
        super(assets, resolutionHelper, localizationService);
        this.stageBuilder = stageBuilder;
    }

    @Override
    public Actor build(BaseModel model) {
        ExternalGroupModel externalGroupModel = (ExternalGroupModel) model;
        try {
            Group group = stageBuilder.buildGroup(externalGroupModel.getFileName());
            setBasicProperties(model, group);
            return group;
        } catch (Exception e) {
            Gdx.app.log(TAG, "Failed to build group from external file " + externalGroupModel.getFileName());
            return null;
        }
    }
}
