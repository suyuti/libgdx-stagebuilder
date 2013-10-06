package net.peakgames.libgdx.stagebuilder.core.builder;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import net.peakgames.libgdx.stagebuilder.core.assets.AssetsInterface;
import net.peakgames.libgdx.stagebuilder.core.assets.ResolutionHelper;
import net.peakgames.libgdx.stagebuilder.core.model.BaseModel;
import net.peakgames.libgdx.stagebuilder.core.model.GroupModel;
import net.peakgames.libgdx.stagebuilder.core.services.LocalizationService;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class GroupBuilder extends ActorBuilder {
    private final Map<Class<? extends BaseModel>, ActorBuilder> builders;

    public GroupBuilder(Map<Class<? extends BaseModel>, ActorBuilder> builders, AssetsInterface assets, ResolutionHelper resolutionHelper, LocalizationService localizationService) {
        super(assets, resolutionHelper, localizationService);
        this.builders = builders;
    }

    @Override
    public Actor build(BaseModel model) {
        GroupModel groupModel = (GroupModel) model;
        Group group = new Group();
        setBasicProperties(model, group);
        List<BaseModel> children = groupModel.getChildren();
        Collections.sort(children, new ZIndexComparator());
        for (BaseModel child : children) {
            Actor actor = builders.get(child.getClass()).build(child);
            group.addActor(actor);
        }
        return group;
    }
}
