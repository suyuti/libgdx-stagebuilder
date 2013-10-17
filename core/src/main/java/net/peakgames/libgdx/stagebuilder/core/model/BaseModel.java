package net.peakgames.libgdx.stagebuilder.core.model;


public abstract class BaseModel {

    private String name;
    private float x;
    private float y;
    private float width;
    private float height;
    private float originX;
    private float originY;
    private float scaleX;
    private float scaleY;
    private float scale;
    private int zIndex;
    private boolean isVisible;
    private String color;
    private float rotation;
    private ScreenAlign screenAlignment = null;
    /**
     * used only if screen alignment is "top"
     */
    private float screenPaddingTop;
    /**
     * used only if screen alignment is "bottom"
     */
    private float screenPaddingBottom;
    /**
     * used only if screen alignment is "right"
     */
    private float screenPaddingRight;
    /**
     * used only if screen alignment is "left"
     */
    private float screenPaddingLeft;

    @Override
    public String toString() {
        return this.name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public int getzIndex() {
        return zIndex;
    }

    public void setzIndex(int zIndex) {
        this.zIndex = zIndex;
    }

    public float getScaleX() {
        return scaleX;
    }

    public void setScaleX(float scaleX) {
        this.scaleX = scaleX;
    }

    public float getScaleY() {
        return scaleY;
    }

    public void setScaleY(float scaleY) {
        this.scaleY = scaleY;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public float getRotation() {
        return rotation;
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }

    public float getOriginX() {
        return originX;
    }

    public void setOriginX(float originX) {
        this.originX = originX;
    }

    public float getOriginY() {
        return originY;
    }

    public void setOriginY(float originY) {
        this.originY = originY;
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public ScreenAlign getScreenAlignment() {
        return screenAlignment;
    }

    public void setScreenAlignment(String s) {
        if (s != null) {
            this.screenAlignment = ScreenAlign.valueOf(s.toLowerCase());
        }
    }

    public void setScreenAlignment(ScreenAlign screenAlignment) {
        this.screenAlignment = screenAlignment;
    }

    public float getScreenPaddingTop() {
        return screenPaddingTop;
    }

    public void setScreenPaddingTop(float screenPaddingTop) {
        this.screenPaddingTop = screenPaddingTop;
    }

    public float getScreenPaddingBottom() {
        return screenPaddingBottom;
    }

    public void setScreenPaddingBottom(float screenPaddingBottom) {
        this.screenPaddingBottom = screenPaddingBottom;
    }

    public float getScreenPaddingRight() {
        return screenPaddingRight;
    }

    public void setScreenPaddingRight(float screenPaddingRight) {
        this.screenPaddingRight = screenPaddingRight;
    }

    public float getScreenPaddingLeft() {
        return screenPaddingLeft;
    }

    public void setScreenPaddingLeft(float screenPaddingLeft) {
        this.screenPaddingLeft = screenPaddingLeft;
    }

    public float getScaledHeight() {
        if (this.scaleY != 1) {
            return this.height * scaleY;
        }

        if (this.scale != 1) {
            return this.height * scale;
        }
        return this.height;
    }

    public float getScaledWidth() {
        if (this.scaleX != 1) {
            return this.width * scaleX;
        }

        if (this.scale != 1) {
            return this.width * scale;
        }
        return this.width;
    }

    public enum ScreenAlign {
        top, bottom, left, right
    }
}
