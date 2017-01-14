package com.openthid.librehero;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;

import com.openthid.librehero.entities.SongData;
import com.openthid.librehero.entities.SongData.BarData;
import com.openthid.librehero.entities.SongData.NoteData;

public class SongFile {

	private FileHandle file;
	private SongData songData;
	private Music music;

	public SongFile(FileHandle file) {
		this.file = file;
	}

	public void parse(){
		JsonReader reader = new JsonReader();
		JsonValue rootElem = reader.parse(file);
		JsonValue songDataElem = rootElem.get("songData");
		JsonValue notesDataElem = songDataElem.get("notes");
		JsonValue barsDataElem = songDataElem.get("bars");
		JsonValue keysDataElem = songDataElem.get("keys");
		
		NoteData[] notes = new NoteData[notesDataElem.size];
		for (int i = 0; i < notesDataElem.size; i++) {
			notes[i] = new NoteData(notesDataElem.get(i).getInt("pitch"), notesDataElem.get(i).getFloat("time"));
		}
		
		BarData[] bars = new BarData[barsDataElem.size];
		for (int i = 0; i < barsDataElem.size; i++) {
			BarData bar = new BarData(barsDataElem.get(i).getFloat("time"));
			bars[i] = bar;
		}
		
		char[] keys = new char[keysDataElem.size];
		for (int i = 0; i < keysDataElem.size; i++) {
			keys[i] = keysDataElem.getChar(i);
		}
		
		float tempo = songDataElem.getFloat("tempo");
		
		songData = new SongData(notes, bars, keys, tempo);
//		music = Gdx.audio.newMusic(Gdx.))
	}

	public SongData getSongData() {
		return songData;
	}

	public Music getMusic() {
		return music;
	}
}