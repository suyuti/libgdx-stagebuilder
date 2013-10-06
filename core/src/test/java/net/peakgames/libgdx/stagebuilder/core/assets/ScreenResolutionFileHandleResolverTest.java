package net.peakgames.libgdx.stagebuilder.core.assets;

import com.badlogic.gdx.math.Vector2;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ScreenResolutionFileHandleResolverTest {

	@Test
	public void desteklenenTekBirCozunurlukVarsa_HerCihazIcin_AyniCozunurluk_Secilmeli() {
		List<Vector2> supportedResolutions = new ArrayList<Vector2>();
		supportedResolutions.add(new Vector2(480, 320));
		
		ScreenResolutionFileHandleResolver resolver = new ScreenResolutionFileHandleResolver(480, supportedResolutions);
		assertEquals(new Vector2(480, 320), resolver.findBestResolution());
		
		resolver = new ScreenResolutionFileHandleResolver(800, supportedResolutions);
		assertEquals(new Vector2(480, 320), resolver.findBestResolution());
		
		resolver = new ScreenResolutionFileHandleResolver(854, supportedResolutions);
		assertEquals(new Vector2(480, 320), resolver.findBestResolution());
		
		resolver = new ScreenResolutionFileHandleResolver(1280, supportedResolutions);
		assertEquals(new Vector2(480, 320), resolver.findBestResolution());
		
		resolver = new ScreenResolutionFileHandleResolver(2560, supportedResolutions);
		assertEquals(new Vector2(480, 320), resolver.findBestResolution());
	}

	@Test
	public void ekranGenisligiEnYakinOlanCozunurlukSecilmeli() {
		List<Vector2> supportedResolutions = new ArrayList<Vector2>();
		supportedResolutions.add(new Vector2(480, 320));
		supportedResolutions.add(new Vector2(800, 480));
		supportedResolutions.add(new Vector2(1280, 800));
		supportedResolutions.add(new Vector2(2560, 1600));

		ScreenResolutionFileHandleResolver resolver = new ScreenResolutionFileHandleResolver(480, supportedResolutions);
		assertEquals(new Vector2(480, 320), resolver.findBestResolution());
		
		resolver = new ScreenResolutionFileHandleResolver(800, supportedResolutions);
		assertEquals(new Vector2(800, 480), resolver.findBestResolution());
		
		resolver = new ScreenResolutionFileHandleResolver(854, supportedResolutions);
		assertEquals(new Vector2(800, 480), resolver.findBestResolution());
		
		resolver = new ScreenResolutionFileHandleResolver(1280, supportedResolutions);
		assertEquals(new Vector2(1280, 800), resolver.findBestResolution());
		
		resolver = new ScreenResolutionFileHandleResolver(1280, supportedResolutions);
		assertEquals(new Vector2(1280, 800), resolver.findBestResolution());
		
		resolver = new ScreenResolutionFileHandleResolver(2560, supportedResolutions);
		assertEquals(new Vector2(2560, 1600), resolver.findBestResolution());
	}
	
	@Test
	public void filePathDuzgunOlusturulmali() {
		List<Vector2> supportedResolutions = new ArrayList<Vector2>();
		supportedResolutions.add(new Vector2(480, 320));
		supportedResolutions.add(new Vector2(800, 480));

		ScreenResolutionFileHandleResolver resolver = new ScreenResolutionFileHandleResolver(480, supportedResolutions);
		assertEquals("images/480x320/test.png", resolver.generateFilePath("test.png"));
		
		resolver = new ScreenResolutionFileHandleResolver(800, supportedResolutions);
		assertEquals("images/800x480/test.png", resolver.generateFilePath("test.png"));
	}
	
}
