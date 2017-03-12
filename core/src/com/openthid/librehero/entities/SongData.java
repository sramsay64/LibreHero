package com.openthid.librehero.entities;

public class SongData {

	private NoteData[] notes;
	private BarData[] bars;
	private char keys[];
	private float tempo;

	/**
	 * @param notes Must be sorted by {@code Note.time}
	 */
	public SongData(NoteData[] notes, BarData[] bars, char[] keys, float tempo) {
		this.notes = notes;
		this.bars = bars;
		this.keys = keys;
		this.tempo = tempo;
	}

	/**
	 * The {@link NoteData}s used to play the song
	 * Sorted by {@code Note.time}
	 */
	public NoteData[] getNotes() {
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
	public BarData[] getBars() {
		return bars;
	}

	/**
	 * @return The tempo in BPM
	 */
	public float getTempo() {
		return tempo;
	}

	public static SongData fromArrays(int[] pitches, float[] times, char[] keys, float[] barTimes, float tempo) {
		NoteData[] notes = new NoteData[pitches.length];
		for (int i = 0; i < pitches.length; i++) {
			notes[i] = new NoteData(pitches[i], times[i]);
		}
		BarData[] bars = new BarData[barTimes.length];
		for (int i = 0; i < bars.length; i++) {
			bars[i] = new BarData(barTimes[i]);
		}
		return new SongData(notes, bars, keys, tempo);
	}

	public static class NoteData {
		/**
		 * Which column the note will be sent down
		 */
		public int pitch;
		/**
		 * Time in the song that the note appears. Measured in seconds
		 */
		public float time;
		
		public NoteData(int pitch, float time) {
			this.pitch = pitch;
			this.time = time;
		}

		@Override
		public String toString() {
			return "NoteData [pitch=" + pitch + ", time=" + time + "]";
		}
	}

	public static class BarData {
		/**
		 * Length of the bar in beats
		 */
		public float time;

		public BarData(float time) {
			this.time = time;
		}
	}
}