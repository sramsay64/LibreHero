package com.openthid.librehero.entities;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;

import com.openthid.librehero.components.SelfRenderedComponent;
import com.openthid.librehero.entities.Song.Note;

public class NoteBoard {

	private Entity entity;
	private NoteBoardSprite sprite;

	private Song song;

	private int[] keyCodes;

	public NoteBoard(int xPos, int yPos, int xSize, int ySize, float skewFactor, float density, Song song) {
		this.song = song;
		
		SelfRenderedComponent selfRenderedComponent = new SelfRenderedComponent(this::draw);
		entity = new Entity();
		entity.add(selfRenderedComponent);
		
		sprite = new NoteBoardSprite(xPos, yPos, xSize, ySize, skewFactor, density, this, song);
		
		keyCodes = new int[song.getKeys().length];
		for (int i = 0; i < song.getKeys().length; i++) {
			keyCodes[i] = Input.Keys.valueOf(("" + song.getKeys()[i]).toUpperCase());
		}
	}

	public char[] getKeys() {
		return song.getKeys();
	}

	private void draw(PolygonSpriteBatch batch) {
		sprite.draw(batch);
	}

	public Entity getEntity() {
		return entity;
	}

	public int getColumns() {
		return getKeys().length;
	}

	public void keyDown(int keycode) {
		for (int i = 0; i < keyCodes.length; i++) {
			if (keycode == keyCodes[i]) {
				playNote(i);
			}
		}
	}

	private void playNote(int pitch) {
//		Arrays.stream(song.getNotes())
//			.filter(Note::isAlive)
//			.filter(note -> {
//				float beatsAway = note.getTime()-song.getCurrentBeatsTime();
//				return beatsAway > -0.5f && beatsAway < 0.5f && !note.wasPlayed();
//			}).filter(note -> note.getPitch() == pitch)
//			.forEach(note -> {
//				note.play();
//				System.out.println(song.getKeys()[note.getPitch()]);
//			}
//		);
		for (int i = 0; i < song.getNotes().length; i++) {
			Note note = song.getNotes()[i];
			if (song.notePlayable(note) && !note.wasPlayed() && note.getPitch() == pitch) {
				note.play();
				System.out.println(song.getKeys()[note.getPitch()]);
			}
		}
	}
}