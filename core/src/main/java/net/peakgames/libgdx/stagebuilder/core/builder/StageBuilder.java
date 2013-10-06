package net.peakgames.libgdx.stagebuilder.core.builder;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import net.peakgames.libgdx.stagebuilder.core.assets.AssetsInterface;
import net.peakgames.libgdx.stagebuilder.core.assets.ResolutionHelper;
import net.peakgames.libgdx.stagebuilder.core.model.*;
import net.peakgames.libgdx.stagebuilder.core.services.LocalizationService;
import net.peakgames.libgdx.stagebuilder.core.xml.XmlModelBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StageBuilder {
    public static final String ROOT_GROUP_NAME = "AbsoluteLayoutRootGroup";
    public static final String TAG = StageBuilder.class.getSimpleName();
    private Map<Class<? extends BaseModel>, ActorBuilder> builders = new HashMap<Class<? extends BaseModel>, ActorBuilder>();
    private AssetsInterface assets;
    private ResolutionHelper resolutionHelper;
    private LocalizationService localizationService;

    public StageBuilder(AssetsInterface assets, ResolutionHelper resolutionHelper, LocalizationService localizationService) {
        this.assets = assets;
        this.resolutionHelper = resolutionHelper;
        this.localizationService = localizationService;

        registerWidgetBuilders(assets);

    }

    /**
     * There must be a widget builder for every type of widget model. Models represents widget data and builders use this data to create scene2d actors.
     * @param assets assets interface.
     */
    private void registerWidgetBuilders(AssetsInterface assets) {
        builders.put(ImageModel.class, new ImageBuilder(this.assets, this.resolutionHelper, this.localizationService));
        builders.put(GroupModel.class, new GroupBuilder(builders, assets, this.resolutionHelper, this.localizationService));
        builders.put(ButtonModel.class, new ButtonBuilder(this.assets, this.resolutionHelper, this.localizationService));
        builders.put(TextButtonModel.class, new TextButtonBuilder(this.assets, this.resolutionHelper, this.localizationService));
        builders.put(LabelModel.class, new LabelBuilder(this.assets, this.resolutionHelper, this.localizationService));
        builders.put(CustomWidgetModel.class, new CustomWidgetBuilder(this.assets, this.resolutionHelper, this.localizationService));
        builders.put(ExternalGroupModel.class, new ExternalGroupModelBuilder(this.assets, this.resolutionHelper, this.localizationService, this));
    }

    public Group buildGroup(String filePath) throws Exception {
        XmlModelBuilder xmlModelBuilder = new XmlModelBuilder();
        List<BaseModel> modelList = xmlModelBuilder.buildModels(filePath);
        GroupModel groupModel = (GroupModel) modelList.get(0);
        Group group = new Group();
        for (BaseModel model : groupModel.getChildren()) {
            ActorBuilder builder = builders.get(model.getClass());
            group.addActor(builder.build(model));
        }
        return group;
    }

    public Stage build(String filePath, float width, float height, boolean keepAspectRatio) {
        try {
            XmlModelBuilder xmlModelBuilder = new XmlModelBuilder();
            List<BaseModel> modelList = xmlModelBuilder.buildModels(filePath);
            GroupModel groupModel = (GroupModel) modelList.get(0);
            Stage stage = new Stage(width, height, keepAspectRatio);
            addActorsToStage(stage, groupModel.getChildren());
            stage.getRoot().setX(resolutionHelper.getGameAreaPosition().x);
            stage.getRoot().setY(resolutionHelper.getGameAreaPosition().y);
            stage.getRoot().setName(ROOT_GROUP_NAME);
            return stage;
        } catch (Exception e) {
            Gdx.app.log(TAG, "Failed to build stage.", e);
        }
        return null;
    }

    private void addActorsToStage(Stage stage, List<BaseModel> models) {
        for (BaseModel model : models) {
            ActorBuilder builder = builders.get(model.getClass());
            stage.getRoot().addActor(builder.build(model));
        }
    }
}
