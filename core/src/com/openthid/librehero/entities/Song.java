package com.openthid.librehero.entities;

import com.badlogic.ashley.core.Entity;

import com.openthid.librehero.components.UpdatingComponent;
import com.openthid.librehero.entities.SongData.Bar;
import com.openthid.librehero.entities.SongData.Note;

public class Song {

	private SongData songData;
	private float duration;

	private float currentTime;
	private int currentNoteIndex;
	private int currentBarIndex;

	private Entity entity;

	public Song(SongData songData, float duration) {
		this.songData = songData;
		this.duration = duration;
		
		this.currentTime = 0;
		this.currentNoteIndex = 0;
		this.currentBarIndex = 0;
		
		UpdatingComponent updatingComponent = new UpdatingComponent(this::update);
		entity = new Entity();
		entity.add(updatingComponent);
	}

	/**
	 * @return Playback progress in seconds
	 */
	public float getCurrentTime() {
		return currentTime;
	}

	/**
	 * @return Total song length in seconds
	 */
	public float getDuration() {
		return duration;
	}

	/**
	 * @return Index of the first {@link Note} that hasn't been played yet
	 */
	public int getCurrentNoteIndex() {
		return currentNoteIndex;
	}

	/**
	 * @return Index of the first {@link Bar} that hasn't finished yet
	 */
	public int getCurrentBarIndex() {
		return currentBarIndex;
	}

	public Entity getEntity() {
		return entity;
	}

	private void update(float deltaTime) {
		currentTime += deltaTime;
		while (getNotes()[currentNoteIndex].time < currentTime) {
			currentNoteIndex++;
		}
	}

	public Note[] getNotes() {
		return songData.getNotes();
	}

	public char[] getKeys() {
		return songData.getKeys();
	}

	public Bar[] getBars() {
		return songData.getBars();
	}
}