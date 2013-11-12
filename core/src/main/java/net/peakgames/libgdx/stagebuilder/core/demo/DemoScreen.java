package net.peakgames.libgdx.stagebuilder.core.demo;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import net.peakgames.libgdx.stagebuilder.core.AbstractGame;
import net.peakgames.libgdx.stagebuilder.core.AbstractScreen;

public abstract class DemoScreen extends AbstractScreen {
    private ShapeRenderer debugRenderer = new ShapeRenderer();

    public DemoScreen(final AbstractGame game) {
        super(game);
        addBackButtonListener(game);

    }

	private void addBackButtonListener(final AbstractGame game) {
		Button backButton = findButton("backButton");
        if (backButton != null) {
            backButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    game.backToPreviousScreen();
                }
            });
        }
	}

    @Override
    public void unloadAssets() {

    }

    @Override
    public void render(float delta) {
        super.render(delta);

        debugRenderer.setProjectionMatrix(camera.combined);

        debugRenderer.begin(ShapeRenderer.ShapeType.Line);
        debugRenderer.setColor(Color.YELLOW);
        Vector2 gameAreaBounds = game.getResolutionHelper().getGameAreaBounds();
        Vector2 gameAreaPosition= game.getResolutionHelper().getGameAreaPosition();
        debugRenderer.rect(gameAreaPosition.x, gameAreaPosition.y, gameAreaBounds.x, gameAreaBounds.y);
        debugRenderer.rect(gameAreaPosition.x+1, gameAreaPosition.y+1, gameAreaBounds.x-2, gameAreaBounds.y-2);
        debugRenderer.end();


        Table.drawDebug(stage);

    }

	@Override
	public void onStageReloaded() {
		addBackButtonListener(game);
	}
}
