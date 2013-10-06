package net.peakgames.libgdx.stagebuilder.core.model;

public class ButtonModel extends BaseModel {
    private String atlasName;
    private String frameUp;
    private String frameDown;
    private String frameDisabled;
    private String textureSrcUp;
    private String textureSrcDown;
    private String textureSrcDisabled;

    public String getAtlasName() {
        return atlasName;
    }

    public void setAtlasName(String atlasName) {
        this.atlasName = atlasName;
    }

    public String getFrameUp() {
        return frameUp;
    }

    public void setFrameUp(String frameUp) {
        this.frameUp = frameUp;
    }

    public String getFrameDown() {
        return frameDown;
    }

    public void setFrameDown(String frameDown) {
        this.frameDown = frameDown;
    }

    public String getFrameDisabled() {
        return frameDisabled;
    }

    public void setFrameDisabled(String frameDisabled) {
        this.frameDisabled = frameDisabled;
    }

    public String getTextureSrcUp() {
        return textureSrcUp;
    }

    public void setTextureSrcUp(String textureSrcUp) {
        this.textureSrcUp = textureSrcUp;
    }

    public String getTextureSrcDown() {
        return textureSrcDown;
    }

    public void setTextureSrcDown(String textureSrcDown) {
        this.textureSrcDown = textureSrcDown;
    }

    public String getTextureSrcDisabled() {
        return textureSrcDisabled;
    }

    public void setTextureSrcDisabled(String textureSrcDisabled) {
        this.textureSrcDisabled = textureSrcDisabled;
    }
}
