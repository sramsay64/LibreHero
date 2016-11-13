package com.openthid.librehero;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;

import com.kotcrab.vis.ui.VisUI;
import com.openthid.librehero.screens.FieldScreen;
import com.openthid.librehero.screens.HomeScreen;
import com.openthid.librehero.screens.TitleScreen;

public class GdxGame extends Game {
	private PolygonSpriteBatch batch;

	private int width;
	private int height;

	private HomeScreen homeScreen;
	private TitleScreen titleScreen;
	private FieldScreen fieldScreen;

	public GdxGame(int width, int height) {
		this.height = height;
		this.width = width;
	}

	public void create() {
		batch = new PolygonSpriteBatch();
		VisUI.load();
		setScreen(getTitleScreen());
	}

	public PolygonSpriteBatch getBatch() {
		return batch;
	}

	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}

	public HomeScreen getHomeScreen() {
		if (homeScreen == null) {
			homeScreen = new HomeScreen(this);
		}
		return homeScreen;
	}

	public TitleScreen getTitleScreen() {
		if (titleScreen == null) {
			titleScreen = new TitleScreen(this);
		}
		return titleScreen;
	}

	public FieldScreen getFieldScreen() {
		if (fieldScreen == null) {
			fieldScreen = new FieldScreen(this);
		}
		return fieldScreen;
	}
}