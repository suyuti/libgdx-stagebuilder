package net.peakgames.libgdx.stagebuilder.core;


import net.peakgames.libgdx.stagebuilder.core.assets.AssetsInterface;
import net.peakgames.libgdx.stagebuilder.core.assets.ResolutionHelper;
import net.peakgames.libgdx.stagebuilder.core.services.LocalizationService;

import java.util.Map;

public interface ICustomWidget {
    public void build(Map<String, String> attributes, AssetsInterface assetsInterface, ResolutionHelper resolutionHelper, LocalizationService localizationService);
}
