package com.openthid.librehero.screens;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;

import com.openthid.librehero.GdxGame;

public class BaseScreen extends ScreenAdapter implements InputProcessor {
	private GdxGame game;

	public BaseScreen(GdxGame game) {
		this.game = game;
	}

	public GdxGame getGame() {
		return game;
	}

	public PolygonSpriteBatch getBatch() {
		return getGame().getBatch();
	}

	public int getHeight() {
		return getGame().getHeight();
	}

	public int getWidth() {
		return getGame().getWidth();
	}

	@Override
	public boolean keyDown(int keycode) {//Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {//Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {//Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {//Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {//Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {//Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {//Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {//Auto-generated method stub
		return false;
	}
}