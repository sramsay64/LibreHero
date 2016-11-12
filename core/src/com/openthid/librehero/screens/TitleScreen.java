package com.openthid.librehero.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.openthid.librehero.GdxGame;
import com.openthid.util.Fonts;

public class TitleScreen extends BaseScreen {

	public TitleScreen(GdxGame game) {
		super(game);
	}

	@Override
	public void render(float delta) {//LATER Make nicer
		super.render(delta);
		Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		getBatch().begin();
		Fonts.ubuntuMedium40.draw(getGame().getBatch(), "Press a key to start", getWidth() / 2, getHeight() / 2);
		getBatch().end();
	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(this);
	}

	@Override
	public boolean keyTyped(char character) {
		getGame().setScreen(getGame().getHomeScreen());
		return true;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		getGame().setScreen(getGame().getHomeScreen());
		return true;
	}
}