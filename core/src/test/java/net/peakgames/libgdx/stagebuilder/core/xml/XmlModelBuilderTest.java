package net.peakgames.libgdx.stagebuilder.core.xml;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglFiles;
import net.peakgames.libgdx.stagebuilder.core.model.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class XmlModelBuilderTest {

    private XmlModelBuilder builder;

    @BeforeClass
    public static void beforeClass() {
        Gdx.files = new LwjglFiles();
    }

    @Before
    public void setup() {
        builder = new XmlModelBuilder();
    }

    @Test
    public void testSingleItemThatIsNotAGroup() throws Exception {
        List<BaseModel> models = builder.buildModels("single_image.xml");
        assertEquals(1, models.size());
        ImageModel image = (ImageModel)models.get(0);
        assertEquals("myImage", image.getName());
        assertEquals(50, image.getX(), 0);
        assertEquals(90, image.getY(), 0);
        assertEquals(200, image.getWidth(), 0);
        assertEquals(250, image.getHeight(), 0);
        assertEquals("star", image.getFrame());
        assertEquals("myAtlas.atlas", image.getAtlasName());
    }

    @Test
    public void testSingleGroupLayout() throws Exception {
        List<BaseModel> models = builder.buildModels("single_group.xml");
        assertEquals(1, models.size());
        GroupModel group = (GroupModel)models.get(0);
        List<BaseModel> children = group.getChildren();
        assertEquals(6, children.size());
        assertEquals("image1", children.get(0).getName());
        assertEquals("image2", children.get(1).getName());
        assertEquals("label1", children.get(2).getName());
        assertEquals("button1", children.get(3).getName());
        assertEquals("button2", children.get(4).getName());
        assertEquals("textbutton1", children.get(5).getName());

        LabelModel label = (LabelModel) children.get(2);
        assertEquals("ilkin", label.getText());
        assertEquals("helvetica.fnt", label.getFontName());
        assertEquals("DFDFDF", label.getFontColor());
        assertEquals(true, label.isWrap());
        assertEquals("center", label.getAlignment());
        assertEquals(true, label.isShadow());
        assertEquals("000000", label.getShadowColor());

        ButtonModel atlasButton = (ButtonModel) children.get(3);
        assertEquals("test.atlas", atlasButton.getAtlasName());
        assertEquals("buttonUp", atlasButton.getFrameUp());
        assertEquals("buttonDown", atlasButton.getFrameDown());
        assertEquals("buttonDisabled", atlasButton.getFrameDisabled());

        ButtonModel textureButton = (ButtonModel) children.get(4);
        assertEquals("buttonUpSrc", textureButton.getTextureSrcUp());
        assertEquals("buttonDownSrc", textureButton.getTextureSrcDown());
        assertEquals("buttonDisabledSrc", textureButton.getTextureSrcDisabled());

        TextButtonModel textButton = (TextButtonModel) children.get(5);
        assertEquals("click me", textButton.getText());
        assertEquals("courier.fnt", textButton.getFontName());
        assertEquals("000000", textButton.getFontColor());
        assertEquals(0.8f, textButton.getFontScale(), 0);
        assertEquals(1f, textButton.getLabelPadding(), 0);
        assertEquals(2f, textButton.getLabelPaddingLeft(), 0);
        assertEquals(3f, textButton.getLabelPaddingRight(), 0);
        assertEquals(4f, textButton.getLabelPaddingTop(), 0);
        assertEquals(5f, textButton.getLabelPaddingBottom(), 0);
    }

    @Test
    public void testNestedGroups() throws Exception {
        List<BaseModel> models = builder.buildModels("nested_groups.xml");
        GroupModel root = (GroupModel) models.get(0);
        assertEquals("root_group", root.getName());
        assertEquals(3, root.getChildren().size());

        ImageModel image1 = (ImageModel) root.getChildren().get(0);
        GroupModel group1 = (GroupModel) root.getChildren().get(1);
        ImageModel image2 = (ImageModel) root.getChildren().get(2);

        assertEquals("image1", image1.getName());
        assertEquals("image2", image2.getName());
        assertEquals("group1", group1.getName());

        assertEquals(4, group1.getChildren().size());

        GroupModel innerGroup = (GroupModel) group1.getChildren().get(3);
        assertEquals("inner_group", innerGroup.getName());
        assertEquals(2, innerGroup.getChildren().size());

        ImageModel innerGroupImage = (ImageModel) innerGroup.getChildren().get(0);
        assertEquals("inner_group_image1", innerGroupImage.getName());
    }

    @Test
    public void isCustomWidget() {
        assertTrue(builder.isCustomWidget("net.peakgames.widgets.TestWidget"));
        assertFalse(builder.isCustomWidget("TestWidget"));
    }

    @Test
    public void testCustomWidgets() throws Exception {
        List<BaseModel> models = builder.buildModels("custom_widgets.xml");
        GroupModel root = (GroupModel) models.get(0);
        assertEquals("root_group", root.getName());
        assertEquals(3, root.getChildren().size());

        GroupModel group1 = (GroupModel) root.getChildren().get(0);
        assertEquals(2, group1.getChildren().size());

        CustomWidgetModel sliderModel = (CustomWidgetModel) group1.getChildren().get(0);
        CustomWidgetModel progressBarModel = (CustomWidgetModel) group1.getChildren().get(1);

        assertEquals("0", sliderModel.getAttribute("min"));
        assertEquals("100", sliderModel.getAttribute("max"));
        assertEquals("5", sliderModel.getAttribute("increment"));
        assertEquals("net.peakgames.test.Slider", sliderModel.getKlass());

        assertEquals("@string/loading", progressBarModel.getAttribute("text"));
        assertEquals("black", progressBarModel.getAttribute("backgroundColor"));
        assertEquals("net.peakgames.test.ProgressBar", progressBarModel.getKlass());

        CustomWidgetModel chatBubble = (CustomWidgetModel) root.getChildren().get(2);
        assertEquals("net.peakgames.test.ChatBubble", chatBubble.getKlass());

    }
}
