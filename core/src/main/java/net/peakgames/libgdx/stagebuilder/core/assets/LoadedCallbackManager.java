package net.peakgames.libgdx.stagebuilder.core.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetLoaderParameters.LoadedCallback;
import com.badlogic.gdx.assets.AssetManager;

import java.util.ArrayList;
import java.util.List;

public class LoadedCallbackManager implements LoadedCallback {
	
	private static final String TAG = LoadedCallbackManager.class.getSimpleName();
	
	private List<AssetLoaderListener> listeners = new ArrayList<AssetLoaderListener>();
	private List<String> files = new ArrayList<String>();

	public void addAssetsLoadedListener(AssetLoaderListener listener) {
		if (this.listeners.contains(listener)) {
			return;
		}
		this.listeners.add(listener);
	}

	public void addFile(String fileName) {
		if (files.contains(fileName)) {
			return;
		}
		Gdx.app.log(TAG, "Tracking " + fileName + " loading...");
		this.files.add(fileName);
	}

	@Override
	public void finishedLoading(AssetManager assetManager, String fileName, Class type) {
		Gdx.app.log(TAG, "finishedLoading asset " + fileName);
		this.files.remove(fileName);
		if (this.files.size() == 0) {
			for (AssetLoaderListener listener : this.listeners) {
				listener.onAssetsLoaded();
			}
		}
	}
}
