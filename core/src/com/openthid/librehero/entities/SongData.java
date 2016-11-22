package com.openthid.librehero.entities;

public class SongData {

	private Note[] notes;
	private Bar[] bars;
	private char keys[];
	float tempo;

	/**
	 * @param notes Must be sorted by {@code Note.time}
	 */
	public SongData(Note[] notes, Bar[] bars, char[] keys, float tempo) {
		this.notes = notes;
		this.bars = bars;
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
	 * @return The bars
	 */
	public Bar[] getBars() {
		return bars;
	}

	/**
	 * @return The tempo in BPM
	 */
	public float getTempo() {
		return tempo;
	}

	public static SongData fromArrays(int[] pitches, float[] times, char[] keys, float[] barTimes, float tempo) {
		Note[] notes = new Note[pitches.length];
		for (int i = 0; i < pitches.length; i++) {
			notes[i] = new Note(pitches[i], times[i]);
		}
		Bar[] bars = new Bar[barTimes.length];
		for (int i = 0; i < bars.length; i++) {
			bars[i] = new Bar(barTimes[i]);
		}
		return new SongData(notes, bars, keys, tempo);
	}

	public static class Note {
		/**
		 * Which column the note will be sent down
		 */
		public int pitch;
		/**
		 * Time in the song that the note appears. Measured in beats
		 */
		public float time;
		
		public Note(int pitch, float time) {
			this.pitch = pitch;
			this.time = time;
		}
	}

	public static class Bar {
		/**
		 * Length of the bar in beats
		 */
		public float time;

		public Bar(float time) {
			this.time = time;
		}
	}
}