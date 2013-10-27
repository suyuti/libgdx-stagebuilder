package net.peakgames.libgdx.stagebuilder.core.builder;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglFiles;
import com.badlogic.gdx.files.FileHandle;
import net.peakgames.libgdx.stagebuilder.core.assets.AssetsInterface;
import net.peakgames.libgdx.stagebuilder.core.assets.ResolutionHelper;
import net.peakgames.libgdx.stagebuilder.core.services.LocalizationService;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class StageBuilderTest {

    private AssetsInterface assets = Mockito.mock(AssetsInterface.class);
    private ResolutionHelper resolutionHelper = Mockito.mock(ResolutionHelper.class);
    private LocalizationService localizationService = Mockito.mock(LocalizationService.class);

    @BeforeClass
    public static void beforeClass() throws Exception {
        Gdx.files = new LwjglFiles();
    }

    @Test
    public void getCorrectLayoutFileInLandscapeMode() {
        float width = 800;
        float height = 480;
        StageBuilder stageBuilder = new StageBuilder(assets, resolutionHelper, localizationService);
        Mockito.when(resolutionHelper.getScreenWidth()).thenReturn(width);
        Mockito.when(resolutionHelper.getScreenHeight()).thenReturn(height);

        FileHandle layoutFile = stageBuilder.getLayoutFile("landscape_layout.xml");
        assertTrue(layoutFile.exists());

        FileHandle nonExistingFile = stageBuilder.getLayoutFile("non-existing-file.xml");
        assertFalse(nonExistingFile.exists());

        //in landscape mode layout-land and layout folders will be searched (in order) for files
        FileHandle existingFileInWrongFolder= stageBuilder.getLayoutFile("portrait_layout.xml");
        assertFalse(existingFileInWrongFolder.exists());
    }

    @Test
    public void getCorrectLayoutFileInPortraitMode() {
        float width = 480;
        float height = 800;
        StageBuilder stageBuilder = new StageBuilder(assets, resolutionHelper, localizationService);
        Mockito.when(resolutionHelper.getScreenWidth()).thenReturn(width);
        Mockito.when(resolutionHelper.getScreenHeight()).thenReturn(height);

        FileHandle layoutFile = stageBuilder.getLayoutFile("portrait_layout.xml");
        assertTrue(layoutFile.exists());

        FileHandle nonExistingFile = stageBuilder.getLayoutFile("non-existing-file.xml");
        assertFalse(nonExistingFile.exists());

        //in portrait mode layout-port and layout folders will be searched (in order) for files
        FileHandle existingFileInWrongFolder= stageBuilder.getLayoutFile("landscape_layout.xml");
        assertFalse(existingFileInWrongFolder.exists());
    }

    @Test
    public void usingDefaultLayoutFile() {
        float width = 480;
        float height = 800;
        StageBuilder stageBuilder = new StageBuilder(assets, resolutionHelper, localizationService);
        Mockito.when(resolutionHelper.getScreenWidth()).thenReturn(width);
        Mockito.when(resolutionHelper.getScreenHeight()).thenReturn(height);

        FileHandle file = stageBuilder.getLayoutFile("TestScreenA.xml");
        assertTrue(file.exists());
    }

}
