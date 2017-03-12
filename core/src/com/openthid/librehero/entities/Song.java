package com.openthid.librehero.entities;

import com.badlogic.gdx.audio.Music;

import com.openthid.librehero.entities.SongData.BarData;
import com.openthid.librehero.entities.SongData.NoteData;
import com.openthid.util.FunctionalUtils;

public class Song {

	private Music music;

	private SongData songData;
	private Note[] notes;

	private float duration;
	private float startTime;

	/**
	 * @param music
	 * @param songData
	 * @param duration
	 * @param startTime An offset for when in the {@link Music} the song starts (for songs that contain some silence at the start)
	 */
	public Song(Music music, SongData songData, float duration, float startTime) {
		this.music = music;
		this.songData = songData;
		this.duration = duration;
		this.startTime = startTime;
		
		music.setPosition(startTime);
		
		notes = FunctionalUtils.map(songData.getNotes(), noteData -> new Note(noteData), i -> new Note[i]);
	}

	public float getBeatsPerSec() {
		return songData.getTempo()/60;
	}

	/**
	 * @return Playback progress in seconds
	 */
	public float getPosition() {
		return music.getPosition()-startTime;
	}

	/**
	 * @return Playback progress in beats
	 */
	public float getCurrentBeatsTime() {
		return getPosition()*getBeatsPerSec();
	}

	/**
	 * @return Total song length in seconds
	 */
	public float getDuration() {
		return duration;
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
		return beatsAway > -0.4f && beatsAway < 0.4f; //TWEAK
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

	public void playPause() {
		if (music.isPlaying()) {
			music.pause();
		} else {
			music.play();
		}
		if (music.getPosition() < startTime) {
			music.setPosition(startTime);
		}
	}
}