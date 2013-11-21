package net.peakgames.libgdx.stagebuilder.core.model;

public class SelectBoxModel extends BaseModel {
    private String name;

    private String value;
    private String fontName;
    private String fontColor;
    private String fontColorSelected;
    private String fontColorUnselected;

    private String atlasName;
    private String background;
    private String selection;
    private String selectionBackground;

    private int paddingLeft;
    private int paddingRight;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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

    public String getFontColorSelected() {
        return fontColorSelected;
    }

    public void setFontColorSelected(String fontColorSelected) {
        this.fontColorSelected = fontColorSelected;
    }

    public String getAtlasName() {
        return atlasName;
    }

    public void setAtlasName(String atlasName) {
        this.atlasName = atlasName;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public String getSelection() {
        return selection;
    }

    public void setSelection(String selection) {
        this.selection = selection;
    }

    public String getSelectionBackground() {
        return selectionBackground;
    }

    public void setSelectionBackground(String selectionBackground) {
        this.selectionBackground = selectionBackground;
    }

    public int getPaddingLeft() {
        return paddingLeft;
    }

    public void setPaddingLeft(int paddingLeft) {
        this.paddingLeft = paddingLeft;
    }

    public int getPaddingRight() {
        return paddingRight;
    }

    public void setPaddingRight(int paddingRight) {
        this.paddingRight = paddingRight;
    }

    public String getFontColorUnselected() {
        return fontColorUnselected;
    }

    public void setFontColorUnselected(String fontColorUnselected) {
        this.fontColorUnselected = fontColorUnselected;
    }
}
