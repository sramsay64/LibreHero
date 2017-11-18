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
import com.openthid.librehero.SongFile;
import com.openthid.librehero.entities.NoteBoard;
import com.openthid.librehero.entities.ScoreBoard;
import com.openthid.librehero.entities.Song;
import com.openthid.librehero.systems.RenderSystem;
import com.openthid.librehero.systems.UpdateSystem;

/**
 * Renders the game field
 */
public class FieldScreen extends BaseScreen {

	private HashMap<Integer, Boolean> keyCache;

	private Engine engine;

	private RenderSystem renderSystem;
	private UpdateSystem updateSystem;

	private Stage stage;
	private ScalingViewport viewport;

	private Song song;

	private VisSlider zoomSlider;
	private NoteBoard noteBoard;
	private ScoreBoard scoreBoard;

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
			return true;
		});
		stage.addActor(zoomSlider);
		
//		SongFile songFile = new SongFile(Gdx.files.absolute("/home/scott/git/LibreHero-Songs/Evan-LE-NY/La Chenille.json"));
		SongFile songFile = new SongFile(Gdx.files.absolute("/home/scott/git/LibreHero-Songs/Brad-Sucks/Brad-Sucks-Dirtbag.json"));
		songFile.parse();
		
		scoreBoard = new ScoreBoard(0, getHeight()-100, 300, 100);
		engine.addEntity(scoreBoard.getEntity());
		
		song = songFile.makeSong();
		noteBoard = new NoteBoard(400, 100, 500, 600, 1f, 2f, song, scoreBoard);
		engine.addEntity(noteBoard.getEntity());
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