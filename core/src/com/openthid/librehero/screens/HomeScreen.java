package com.openthid.librehero.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.kotcrab.vis.ui.widget.VisTextButton;
import com.openthid.librehero.GdxGame;
import com.openthid.util.FunctionalUtils;

public class HomeScreen extends BaseScreen {

	private Stage stage;

	private VisTextButton btnStart;
	private VisTextButton btnFiles;
	private VisTextButton btnSettings;
	private VisTextButton btnBack;
	private VisTextButton btnQuit;

	public HomeScreen(GdxGame game) {
		super(game);
		btnStart = new VisTextButton("Start", FunctionalUtils.makeChangeListener((e, a) -> {
			getGame().setScreen(getGame().getFieldScreen());
		}));
		btnFiles = new VisTextButton("Files", FunctionalUtils.makeChangeListener((e, a) -> {
			Gdx.app.log("TODO", "Files Screen");
		}));
		btnSettings = new VisTextButton("Settings", FunctionalUtils.makeChangeListener((e, a) -> {
			Gdx.app.log("TODO", "Settings Screen");
		}));
		btnBack = new VisTextButton("Back", FunctionalUtils.makeChangeListener((e, a) -> {
			getGame().setScreen(getGame().getTitleScreen());
		}));
		btnQuit = new VisTextButton("Quit", FunctionalUtils.makeChangeListener((e, a) -> {
			Gdx.app.log("Core", "Exiting Game. Heap size: " + Gdx.app.getNativeHeap() + ", " + Gdx.app.getJavaHeap());
			Gdx.app.exit();
		}));
		
		arrangeButtons(getWidth(), getHeight(), 100, 30, 0.5f, 0.8f, 10, btnQuit, btnBack, btnSettings, btnFiles, btnStart);
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
		stage.addActor(btnStart);
		stage.addActor(btnFiles);
		stage.addActor(btnSettings);
		stage.addActor(btnBack);
		stage.addActor(btnQuit);
	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(stage);
	}

	@Override
	public void render(float delta) {
		super.render(delta);
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		getGame().getBatch().begin();
		btnStart.draw(getBatch(), 1);
		btnFiles.draw(getBatch(), 1);
		btnSettings.draw(getBatch(), 1);
		btnBack.draw(getBatch(), 1);
		btnQuit.draw(getBatch(), 1);
		getGame().getBatch().end();
	}

	@Override
	public boolean keyTyped(char character) {
//		getGame().setScreen(getGame().getHomeScreen());
//		return true;
		return false;
	}

	public static void arrangeButtons(int screenWidth, int screenHeight, int minX, int minY, float xRatio, float yRatio,
			int boarder, VisTextButton... buttons) {
		float finalX = minX;
		if (screenWidth*xRatio > minX) {
			finalX = screenWidth*xRatio;
		}
		if (finalX > screenWidth - boarder*2) {
			finalX = screenWidth - boarder*2;
		}
		float finalY = minY;
		if (screenHeight*yRatio > minY) {
			finalY = screenHeight*yRatio;
		}
		if (finalY > screenHeight - boarder*2) {
			finalY = screenHeight - boarder*2;
		}
		float finalSingularY = (finalY - boarder*(buttons.length-1)) / buttons.length;
		for (int i = 0; i < buttons.length; i++) {
			buttons[i].setPosition((screenWidth-finalX)/2, (screenHeight-finalY)/2 + i*boarder + i*finalSingularY);
			buttons[i].setWidth(finalX);
			buttons[i].setHeight(finalSingularY);
		}
	}
}