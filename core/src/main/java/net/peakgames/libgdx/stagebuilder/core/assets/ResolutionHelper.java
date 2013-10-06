package net.peakgames.libgdx.stagebuilder.core.assets;

import com.badlogic.gdx.math.Vector2;

/**
 * Utility class for aspect ratio and game area bounds calculations.
 * This class is intended to be used with Stage Builder.
 * @see net.peakgames.libgdx.stagebuilder.core.builder.StageBuilder
 */
public class ResolutionHelper {
    private float targetAspectRatio;
    private float screenWidth;
    private float screenHeight;
    private Vector2 gameAreaBounds;
    private Vector2 gameAreaPosition;
    private float targetWidth;
    private float targetHeight;
    private float targetAssetSizeRatio;

    /**
     * @param targetWidth  virtual width of the game area.
     * @param targetHeight virtual height of the game area.
     * @param screenWidth
     * @param screenHeight
     * @param selectedResolutionWidth used for auto-scaling assets. If your stage configuration is done for 800x480 and the assets are
     *                                created for both 800x480 and 1280x800 then if the device screen width is greater than or equals to 1280
     *                                selectedResolutionWidth should be 1280.
     */
    public ResolutionHelper(float targetWidth, float targetHeight, float screenWidth, float screenHeight, float selectedResolutionWidth) {
        super();
        this.targetWidth = targetWidth;
        this.targetHeight = targetHeight;
        this.targetAspectRatio = targetWidth / targetHeight;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.gameAreaBounds = calculateGameAreaBounds(targetAspectRatio, screenWidth, screenHeight);
        this.gameAreaPosition = calculateGameAreaPosition(targetAspectRatio, screenWidth, screenHeight);
        this.targetAssetSizeRatio = gameAreaBounds.x / selectedResolutionWidth;
    }

    /**
     * @param targetAspectRatio
     * @param screenWidth       device screen width in pixels
     * @param screenHeight      device screen height in pixels
     * @return width and height of max area having aspect ratio of targetAspectRatio
     */
    private Vector2 calculateGameAreaBounds(float targetAspectRatio, float screenWidth, float screenHeight) {
        Vector2 result = new Vector2();
        float deviceAspectRatio = screenWidth / screenHeight;
        if (targetAspectRatio > deviceAspectRatio) {
            result.x = screenWidth;
            result.y = screenWidth / targetAspectRatio;
        } else {
            result.x = targetAspectRatio * screenHeight;
            result.y = screenHeight;
        }
        return result;
    }

    /**
     * @param targetAspectRatio
     * @param screenWidth
     * @param screenHeight
     * @return x, y coordinates where the game area will be placed on screen. bottom left corner is (0,0)
     */
    private Vector2 calculateGameAreaPosition(float targetAspectRatio, float screenWidth, float screenHeight) {
        Vector2 area = calculateGameAreaBounds(targetAspectRatio, screenWidth, screenHeight);
        Vector2 pos = new Vector2();
        pos.x = (screenWidth - area.x) * 0.5f;
        pos.y = (screenHeight - area.y) * 0.5f;
        return pos;
    }

    /**
     * Converts virtual coordinates to screen coordinates.
     *
     * @param x x pos on virtual stage
     * @param y y pos on virtual stage
     * @return screen coordinates
     */
    public Vector2 toScreenCoordinates(float x, float y) {
        Vector2 result = new Vector2();
        result.x = x + this.gameAreaPosition.x;
        result.y = y + this.gameAreaPosition.y;
        return result;
    }

    /**
     * Background images may not have the same aspect ratio or size as the screen.
     * This method calculates the min bounds for a background image that covers the screen in fullscreen.
     *
     * @param backgroundWidth
     * @param backgroundHeight
     * @return fullscreen image bounds with respecting background aspect ratio.
     */
    public Vector2 calculateBackgroundSize(float backgroundWidth, float backgroundHeight) {
        Vector2 result = new Vector2();
        float backgroundAspectRatio = backgroundWidth / backgroundHeight;
        float screenAspectRatio = this.screenWidth / this.screenHeight;
        if (backgroundAspectRatio > screenAspectRatio) {
            result.x = this.screenHeight * backgroundAspectRatio;
            result.y = this.screenHeight;
        } else {
            result.x = this.screenWidth;
            result.y = this.screenWidth / backgroundAspectRatio;
        }
        return result;
    }

    public Vector2 calculateBackgroundPosition(float backgroundWidth, float backgroundHeight) {
        Vector2 bgSize = calculateBackgroundSize(backgroundWidth, backgroundHeight);
        Vector2 result = new Vector2();
        result.x = (this.screenWidth - bgSize.x) * 0.5f;
        result.y = (this.screenHeight - bgSize.y) * 0.5f;
        return result;
    }

    public float getPositionMultiplier() {
        return this.gameAreaBounds.x / this.targetWidth;
    }

    public float getSizeMultiplier() {
        return targetAssetSizeRatio;
    }

    public Vector2 getGameAreaBounds() {
        return gameAreaBounds;
    }

    public Vector2 getGameAreaPosition() {
        return gameAreaPosition;
    }

    public float getTargetAspectRatio() {
        return targetAspectRatio;
    }

    public float getScreenWidth() {
        return screenWidth;
    }

    public float getScreenHeight() {
        return screenHeight;
    }

    public float getTargetWidth() {
        return targetWidth;
    }

    public float getTargetHeight() {
        return targetHeight;
    }
}
