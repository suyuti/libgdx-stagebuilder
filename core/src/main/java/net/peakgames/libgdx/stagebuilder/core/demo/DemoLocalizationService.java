package net.peakgames.libgdx.stagebuilder.core.demo;

import net.peakgames.libgdx.stagebuilder.core.services.LocalizationService;

public class DemoLocalizationService implements LocalizationService {
    @Override
    public String getString(String s) {
        return s;
    }

	@Override
	public String getString(String s, Object... args) {
		return s;
	}
}
