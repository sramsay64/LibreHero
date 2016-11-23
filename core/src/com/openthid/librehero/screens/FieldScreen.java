package com.openthid.librehero.screens;

import java.util.HashMap;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.kotcrab.vis.ui.widget.VisSlider;

import com.openthid.librehero.GdxGame;
import com.openthid.librehero.entities.NoteBoard;
import com.openthid.librehero.entities.Song;
import com.openthid.librehero.entities.SongData;
import com.openthid.librehero.systems.RenderSystem;
import com.openthid.librehero.systems.UpdateSystem;

/**
 * Renders the game field
 */
public class FieldScreen extends BaseScreen {
	public static float fff; // TEMP
	public static float fff2; // TEMP
	public static float fff3; // TEMP
	public static float fffx; // TEMP
	public static float fffy; // TEMP
	

	private HashMap<Integer, Boolean> keyCache;

	private Engine engine;

	private RenderSystem renderSystem;
	private UpdateSystem updateSystem;

	private Stage stage;
	private ScalingViewport viewport;

	private Song song;

	private VisSlider zoomSlider;
	private NoteBoard noteBoard;

	public FieldScreen(GdxGame game) {
		super(game);
		keyCache = new HashMap<>(10);
		engine = new Engine();
		viewport = new ScalingViewport(Scaling.fill, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), new OrthographicCamera());
		
		renderSystem = new RenderSystem(getBatch(), getWidth(), getHeight());
		engine.addSystem(renderSystem);
		
		updateSystem = new UpdateSystem();
		engine.addSystem(updateSystem);
		
		stage = new Stage(viewport, getBatch());
		
		zoomSlider = new VisSlider(0, 100, 0.1f, true);
		zoomSlider.setPosition(20, 20);
		zoomSlider.setSize(50, 500);
		zoomSlider.addListener(e -> {
			float amount = zoomSlider.getValue();
			fff = amount/100;
			return true;
		});
		stage.addActor(zoomSlider);
		
		song = new Song(SongData.fromArrays(
				new int[]{	2,1,0,1,2,2,2,1,1, 1, 2, 4, 4},
				new float[]{0,1,2,3,4,5,6,8,9,10,12,13,14},
				new char[]{'q','w','e','r','t'},
				new float[]{-4,0,4,8,12,16,20},
				120
			), 30, -4);
		
		noteBoard = new NoteBoard(400, 100, 500, 600, 1f, 0.8f, song);
		engine.addEntity(noteBoard.getEntity());
		engine.addEntity(song.getEntity());
	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(new InputMultiplexer(this, stage));
	}

	@Override
	public void render(float dt) {
		if (isKeyDown(Input.Keys.LEFT)) {
		} else if (isKeyDown(Input.Keys.RIGHT)) {
		}
		if (isKeyDown(Input.Keys.UP)) {
		} else if (isKeyDown(Input.Keys.DOWN)) {
		}
		getEngine().update(dt);
		stage.draw();
	}

	public Engine getEngine() {
		return engine;
	}

	@Override
	public boolean keyDown(int keycode) {
		keyCache.put(keycode, true);
		noteBoard.keyDown(keycode);
		return true;
	}

	@Override
	public boolean keyUp(int keycode) {
		keyCache.put(keycode, false);
		return true;
	}

	public boolean isKeyDown(int keycode) {
		if (!keyCache.containsKey(keycode)) {
			return false;
		}
		return keyCache.get(keycode);
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		fffx = screenX;
		fffy = 800 - screenY;
		fff2 = fffx/1200;
		fff3 = fffy/800;
		return true;
	}

	@Override
	public boolean scrolled(int amount) {
		zoomSlider.setValue(zoomSlider.getValue() + amount*5);
		return true;
	}

	@Override
	public void resize(int width, int height) {
		viewport.setScreenSize(width, height);
	}
}