package com.openthid.librehero.entities;

import com.openthid.librehero.components.UpdatingComponent;
import com.openthid.librehero.entities.SongData.Note;

public class Song {

	private SongData songData;
	private float currentTime;
	private float duration;

	private int currentNoteIndex;

	private UpdatingComponent updatingComponent;

	public Song(SongData songData, float duration) {
		this.songData = songData;
		this.duration = duration;
		this.currentTime = 0;
		this.currentNoteIndex = 0;
		
		updatingComponent = new UpdatingComponent(this::update);
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
	 * @return Index of the first note that hasn't been played yet
	 */
	public int getCurrentNoteIndex() {
		return currentNoteIndex;
	}

	public UpdatingComponent getUpdatingComponent() {
		return updatingComponent;
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
}