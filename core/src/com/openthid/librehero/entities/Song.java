package com.openthid.librehero.entities;

import com.badlogic.ashley.core.Entity;

import com.openthid.librehero.components.UpdatingComponent;
import com.openthid.librehero.entities.SongData.BarData;
import com.openthid.librehero.entities.SongData.NoteData;
import com.openthid.util.FunctionalUtils;

public class Song {

	private SongData songData;
	private float duration;
	private Note[] notes;

	private float currentTime; // Measured in seconds

	private Entity entity;

	public Song(SongData songData, float duration, float currentTime) {
		this.songData = songData;
		this.duration = duration;
		this.currentTime = currentTime;
		
		notes = FunctionalUtils.map(songData.getNotes(), noteData -> new Note(noteData), i -> new Note[i]);
		
		UpdatingComponent updatingComponent = new UpdatingComponent(this::update);
		entity = new Entity();
		entity.add(updatingComponent);
	}

	public float getBeatsPerSec() {
		return songData.tempo/60;
	}

	/**
	 * @return Playback progress in seconds
	 */
	public float getCurrentTime() {
		return currentTime;
	}

	/**
	 * @return Playback progress in beats
	 */
	public float getCurrentBeatsTime() {
		return getCurrentTime()*getBeatsPerSec();
	}

	/**
	 * @return Total song length in seconds
	 */
	public float getDuration() {
		return duration;
	}

	public Entity getEntity() {
		return entity;
	}

	public char[] getKeys() {
		return songData.getKeys();
	}

	public Note[] getNotes() {
		return notes;
	}

	public BarData[] getBars() {
		return songData.getBars();
	}

	public boolean notePlayable(Note note) {
		float beatsAway = note.getTime()-getCurrentBeatsTime();
		return beatsAway > -0.5f && beatsAway < 0.5f;
	}

	private void update(float deltaTime) {
		currentTime += deltaTime;
	}

	public static class Note {
		private NoteData data;
		private boolean alive;
		private boolean played;

		public Note(NoteData data) {
			this.data = data;
			this.alive = true;
			this.played = false;
		}

		public int getPitch() {
			return data.pitch;
		}

		public float getTime() {
			return data.time;
		}

		public boolean isAlive() {
			return alive;
		}

		public boolean wasPlayed() {
			return played;
		}
		
		public void play() {
			played = true;
		}
	}
}