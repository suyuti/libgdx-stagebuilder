package net.peakgames.libgdx.stagebuilder.core.assets;

import com.badlogic.gdx.math.Vector2;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class StageBuilderFileHandleResolverTest {

	@Test
	public void desteklenenTekBirCozunurlukVarsa_HerCihazIcin_AyniCozunurluk_Secilmeli() {
		List<Vector2> supportedResolutions = new ArrayList<Vector2>();
		supportedResolutions.add(new Vector2(480, 320));
		
		StageBuilderFileHandleResolver resolver = new StageBuilderFileHandleResolver(480, supportedResolutions);
		assertEquals(new Vector2(480, 320), resolver.findBestResolution());
		
		resolver = new StageBuilderFileHandleResolver(800, supportedResolutions);
		assertEquals(new Vector2(480, 320), resolver.findBestResolution());
		
		resolver = new StageBuilderFileHandleResolver(854, supportedResolutions);
		assertEquals(new Vector2(480, 320), resolver.findBestResolution());
		
		resolver = new StageBuilderFileHandleResolver(1280, supportedResolutions);
		assertEquals(new Vector2(480, 320), resolver.findBestResolution());
		
		resolver = new StageBuilderFileHandleResolver(2560, supportedResolutions);
		assertEquals(new Vector2(480, 320), resolver.findBestResolution());
	}

	@Test
	public void ekranGenisligiEnYakinOlanCozunurlukSecilmeli() {
		List<Vector2> supportedResolutions = new ArrayList<Vector2>();
		supportedResolutions.add(new Vector2(480, 320));
		supportedResolutions.add(new Vector2(800, 480));
		supportedResolutions.add(new Vector2(1280, 800));
		supportedResolutions.add(new Vector2(2560, 1600));

		StageBuilderFileHandleResolver resolver = new StageBuilderFileHandleResolver(480, supportedResolutions);
		assertEquals(new Vector2(480, 320), resolver.findBestResolution());
		
		resolver = new StageBuilderFileHandleResolver(800, supportedResolutions);
		assertEquals(new Vector2(800, 480), resolver.findBestResolution());
		
		resolver = new StageBuilderFileHandleResolver(854, supportedResolutions);
		assertEquals(new Vector2(800, 480), resolver.findBestResolution());
		
		resolver = new StageBuilderFileHandleResolver(1280, supportedResolutions);
		assertEquals(new Vector2(1280, 800), resolver.findBestResolution());
		
		resolver = new StageBuilderFileHandleResolver(1280, supportedResolutions);
		assertEquals(new Vector2(1280, 800), resolver.findBestResolution());
		
		resolver = new StageBuilderFileHandleResolver(2560, supportedResolutions);
		assertEquals(new Vector2(2560, 1600), resolver.findBestResolution());
	}
	
	@Test
	public void filePathDuzgunOlusturulmali() {
		List<Vector2> supportedResolutions = new ArrayList<Vector2>();
		supportedResolutions.add(new Vector2(480, 320));
		supportedResolutions.add(new Vector2(800, 480));

		StageBuilderFileHandleResolver resolver = new StageBuilderFileHandleResolver(480, supportedResolutions);
		assertEquals("images/480x320/test.png", resolver.generateFilePath("test.png"));
		
		resolver = new StageBuilderFileHandleResolver(800, supportedResolutions);
		assertEquals("images/800x480/test.png", resolver.generateFilePath("test.png"));
	}

    @Test
    public void filePathShouldBeCorrectForSupportedSoundFiles() {
        List<Vector2> supportedResolutions = new ArrayList<Vector2>();
        supportedResolutions.add(new Vector2(480, 320));
        supportedResolutions.add(new Vector2(800, 480));
        StageBuilderFileHandleResolver resolutionFileHandleResolver = new StageBuilderFileHandleResolver( 480, supportedResolutions);
        assertEquals("test.mp3", resolutionFileHandleResolver.generateFilePath("test.mp3"));
        assertEquals("audio/sound/test.ogg", resolutionFileHandleResolver.generateFilePath("audio/sound/test.ogg"));
        assertEquals("audio/test.wav", resolutionFileHandleResolver.generateFilePath("audio/test.wav"));
    }
	
}
