package com.openthid.librehero.entities;

public class SongData {

	private Note[] notes;
	private char keys[];
	float tempo;

	/**
	 * @param notes Must be sorted by {@code Note.time}
	 */
	public SongData(Note[] notes, char[] keys, float tempo) {
		this.notes = notes;
		this.keys = keys;
		this.tempo = tempo;
	}

	/**
	 * The {@link Note}s used to play the song
	 * Sorted by {@code Note.time}
	 */
	public Note[] getNotes() {
		return notes;
	}

	/**
	 * @return The keys on a keyboard to assign to each pitch
	 */
	public char[] getKeys() {
		return keys;
	}

	/**
	 * @return Song length in beats
	 */
	public float duration() {
		return notes[notes.length-1].time;
	}

	/**
	 * @return The tempo in BPM
	 */
	public float tempo() {
		return tempo;
	}
	public static SongData fromArrays(int[] pitches, float[] times, char[] keys, float tempo) {
		Note[] notes = new Note[pitches.length];
		for (int i = 0; i < pitches.length; i++) {
			notes[i] = new Note(pitches[i], times[i]);
		}
		return new SongData(notes, keys, tempo);
	}

	public static class Note {
		public int pitch;
		/**
		 * 
		 */
		public float time;
		
		public Note(int pitch, float time) {
			this.pitch = pitch;
			this.time = time;
		}
	}
}