package net.peakgames.libgdx.stagebuilder.core.model;

import java.util.ArrayList;
import java.util.List;

public class SelectBoxModel extends BaseModel {
    private String value;
    private String fontName;
    private String fontColor;



    public String getFontName() {
        return fontName;
    }

    public void setFontName(String fontName) {
        this.fontName = fontName;
    }

    public String getFontColor() {
        return fontColor;
    }

    public void setFontColor(String fontColor) {
        this.fontColor = fontColor;
    }
}
