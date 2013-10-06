package net.peakgames.libgdx.stagebuilder.core.model;

/**
 * An image's texture can be loaded in two different ways:
 * 1. From an atlas file with the specified frame name
 * 2. From file system (individual file)
 */
public class ImageModel extends BaseModel {

    public static final String TYPE_BACKGROUND = "background";
    private String atlasName;
    private String frame;
    //TODO textureSrc relative mi? relative ise nereye gore relative? javadoc'u netlestir.
    private String textureSrc;
    private String type;

    public String getAtlasName() {
        return atlasName;
    }

    public void setAtlasName(String atlasName) {
        this.atlasName = atlasName;
    }

    public String getFrame() {
        return frame;
    }

    public void setFrame(String frame) {
        this.frame = frame;
    }

    public String getTextureSrc() {
        return textureSrc;
    }

    public void setTextureSrc(String textureSrc) {
        this.textureSrc = textureSrc;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
