package net.peakgames.libgdx.stagebuilder.core.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.Vector2;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class StageBuilderFileHandleResolver implements FileHandleResolver {

	public static final String IMAGES_DIR = "images";
	/**
	 * Screen width in pixels
	 */
	private int screenWidth;
	
	/**
	 * List of supported resolutions. e.g. 480x320, 800x480
	 */
	private List<Vector2> supportedResolutions;
	
	/**
	 * 
	 * @param width screen width in pixels
	 * @param supportedResolutions list of supported resolutions
	 */
	public StageBuilderFileHandleResolver(int width, List<Vector2> supportedResolutions) {
		this.screenWidth = width;
		this.supportedResolutions = supportedResolutions;
		
		Collections.sort(this.supportedResolutions, new Comparator<Vector2>() {
			@Override
			public int compare(Vector2 o1, Vector2 o2) {
				return (int) (o1.x - o2.x);
			}
		});
	}
	
	@Override
	public FileHandle resolve(String fileName) {
		//TODO gdx atlas uretirken bu metodu bu metodun urettigi filenamePath ile tekrar tekrar cagiriyor.
		//Sebebini henuz bulamadim, work-around olarak asagidaki kontrolu ekledim.
		if (fileName.startsWith(IMAGES_DIR+"/")) {
			return Gdx.files.internal(fileName);
		}
		String path = generateFilePath(fileName);
		return Gdx.files.internal(path);
	}

	String generateFilePath(String fileName) {
        StringBuilder sb = new StringBuilder();
        if ( !isSoundFile( fileName)){
            Vector2 bestRes = findBestResolution();
            sb.append(IMAGES_DIR);
            sb.append("/");
            sb.append((int)bestRes.x);
            sb.append("x");
            sb.append((int)bestRes.y);
            sb.append("/");
        }
        sb.append(fileName);
		return sb.toString();
	}
	
	/**
	 * 
	 * @return the resolution which has the the closest width value.
	 */
	public Vector2 findBestResolution() {
		int minDiff = Integer.MAX_VALUE;
		int bestResIndex = 0;
		for (int i=0; i<supportedResolutions.size(); i++) {
			int diff = Math.abs(screenWidth - (int)supportedResolutions.get(i).x);
			if (diff < minDiff) {
				minDiff = diff;
				bestResIndex = i;
			}
		}
		return supportedResolutions.get(bestResIndex);
	}

    boolean isSoundFile(String fileName){
        String lowerCaseFileName = fileName.toLowerCase();
        if ( lowerCaseFileName.endsWith(".mp3") || lowerCaseFileName.endsWith( ".ogg") || lowerCaseFileName.endsWith(".wav")){
            return true;
        }else{
            return false;
        }
    }

}
