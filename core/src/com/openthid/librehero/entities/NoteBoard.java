package com.openthid.librehero.entities;

import java.util.ArrayList;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;

import com.openthid.librehero.components.SelfRenderedComponent;
import com.openthid.librehero.entities.Song.Note;
import com.openthid.librehero.entities.SongData.NoteData;

public class NoteBoard {

	private Entity entity;
	private NoteBoardSprite sprite;
	private ScoreBoard scoreBoard;

	private Song song;

	private int[] keyCodes;

	public NoteBoard(int xPos, int yPos, int xSize, int ySize, float skewFactor, float density, Song song, ScoreBoard scoreBoard) {
		this.song = song;
		this.scoreBoard = scoreBoard;
		
		SelfRenderedComponent selfRenderedComponent = new SelfRenderedComponent(this::draw);
		entity = new Entity();
		entity.add(selfRenderedComponent);
		
		sprite = new NoteBoardSprite(xPos, yPos, xSize, ySize, skewFactor, density, this, song);
		
		keyCodes = new int[song.getKeys().length];
		for (int i = 0; i < song.getKeys().length; i++) {
			keyCodes[i] = Input.Keys.valueOf(("" + song.getKeys()[i]).toUpperCase());
		}
	}

	public Entity getEntity() {
		return entity;
	}

	public char[] getKeys() {
		return song.getKeys();
	}

	public float getPosition() {
		return song.getPosition();
	}

	public int getColumns() {
		return getKeys().length;
	}

	private void draw(PolygonSpriteBatch batch) {
		sprite.draw(batch);
	}

	private ArrayList<Float> floats = new ArrayList<>();
	private ArrayList<NoteData> notes = new ArrayList<>();
	public void keyDown(int keycode) {
		for (int i = 0; i < keyCodes.length; i++) {
			if (keycode == keyCodes[i]) {
				playNote(i);
				NoteData data = new NoteData(i, song.getCurrentBeatsTime(), 10);
				notes.add(data);
			}
		}
		if (keycode == Input.Keys.SPACE) {
			floats.add(getPosition());
		}
		if (keycode == Input.Keys.ENTER) {
			System.out.println("===== Times =====");
			floats.forEach(System.out::println);
			System.out.println("===== Notes =====");
			notes.forEach(note -> System.out.println("\t\t{\n\t\t\t\"pitch\": " + note.pitch + ",\n\t\t\t\"points\": " + note.points + ",\n\t\t\t\"time\": " + note.time + "\n\t\t},"));
			System.out.println("=====");
		}
		if (keycode == Input.Keys.ALT_RIGHT) {
			song.playPause();
		}
	}

	private void playNote(int pitch) {
		for (int i = 0; i < song.getNotes().length; i++) {
			Note note = song.getNotes()[i];
			if (song.notePlayable(note) && !note.wasPlayed() && note.getPitch() == pitch) {
				note.play();
				scoreBoard.score(note);
				System.out.println(song.getKeys()[note.getPitch()]);
				return; // Only play one note per key press (solves the playing one late and one early note at the same time bug)
			}
		}
		// If the execution reaches here then no notes were played
		scoreBoard.addToScore(song.getFalseHitPoints());
	}
}