package com.openthid.librehero.screens;

import java.util.HashMap;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.kotcrab.vis.ui.widget.VisSlider;

import com.openthid.librehero.GdxGame;
import com.openthid.librehero.SongFile;
import com.openthid.librehero.entities.NoteBoard;
import com.openthid.librehero.entities.Song;
import com.openthid.librehero.entities.SongData;
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
		
		SongFile songFile = new SongFile(Gdx.files.absolute("/home/scott/git/LibreHero-Songs/Evan-LE-NY/La Chenille.json"));
		songFile.parse();
		
//		Music music = Gdx.audio.newMusic(Gdx.files.internal("music/09_-_Brad_Sucks_-_Out_of_It.mp3"));
		Music music = Gdx.audio.newMusic(Gdx.files.absolute("/home/scott/Music/Track 2.wav"));
		song = new Song(music, SongData.fromArrays(
				new int[]{2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 3, 3, 3, 3, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 3, 2, 3, 2, 3, 3, 3, 3, 2, 2, 3, 2, 3, 4, 3, 3, 3, 2, 3, 3, 4, 3, 1, 2, 3, 2, 2, 2, 1, 1, 2, 1, 0, 1, 2, 1, 0},
				new float[]{8.79829f, 10.104959f, 12.195653f, 13.589451f, 16.33348f, 17.770842f, 20.166445f, 21.734455f, 24.086494f, 25.567373f, 28.22432f, 29.661682f, 32.100803f, 33.538162f, 36.0644f, 37.50176f, 40.028f, 41.683132f, 44.078728f, 45.64673f, 48.17297f, 49.61033f, 52.180134f, 53.661053f, 56.013035f, 57.624603f, 60.107327f, 61.54468f, 64.02736f, 67.598976f, 72.172386f, 75.61332f, 80.23029f, 81.275665f, 82.23391f, 83.1486f, 84.10679f, 85.15216f, 86.06685f, 87.15574f, 87.63486f, 88.15755f, 89.1158f, 94.08124f, 95.03944f, 95.60569f, 95.99768f, 96.563934f, 97.13014f, 98.044815f, 103.01026f, 103.57651f, 104.09915f, 104.970314f, 106.14633f, 107.19167f, 108.5419f, 111.02465f, 111.59085f, 112.28775f, 113.02822f, 115.598045f, 116.25139f, 116.730515f, 117.68876f},
				new char[]{'a','s','d','f','g'},
				new float[]{},
//				131.589f
				122.5f
			), 30, 0.24f);
		
		noteBoard = new NoteBoard(400, 100, 500, 600, 1f, 2f, song);
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