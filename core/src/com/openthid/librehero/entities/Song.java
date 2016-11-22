package com.openthid.librehero.entities;

import java.util.function.Consumer;

import com.badlogic.ashley.core.Entity;

import com.openthid.librehero.components.UpdatingComponent;
import com.openthid.librehero.entities.SongData.BarData;
import com.openthid.librehero.entities.SongData.NoteData;
import com.openthid.util.ArrayDropQueue;

public class Song {

	private SongData songData;
	private float duration;

	private float currentTime; // Measured in seconds
	private ArrayDropQueue<NoteData> noteQueue;
	private ArrayDropQueue<BarData> barQueue;

	private Entity entity;

	public Song(SongData songData, float duration, float currentTime) {
		this.songData = songData;
		this.duration = duration;
		this.currentTime = currentTime;
		
		this.noteQueue = new ArrayDropQueue<>(songData.getNotes());
		this.barQueue = new ArrayDropQueue<>(songData.getBars());
		
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

	public void forEachRemainingNote(Consumer<NoteData> consumer) {
		noteQueue.forEachRemaining(consumer);
	}

	public void forEachRemainingBar(Consumer<BarData> consumer) {
		barQueue.forEachRemaining(consumer);
	}

	private void update(float deltaTime) {
		currentTime += deltaTime;
		noteQueue.predicatedDrop(note -> {
			return note.time+getBeatsPerSec()/2 < getCurrentBeatsTime();
		});
		barQueue.predicatedDrop(bar -> {
			return bar.time+getBeatsPerSec()/2 < getCurrentBeatsTime();
		});
	}

	public static class Note {
		private NoteData data;
		
	}
}