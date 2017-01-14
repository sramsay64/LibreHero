package com.openthid.librehero;

import java.net.MulticastSocket;
import java.util.function.Consumer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net.HttpRequest;
import com.badlogic.gdx.Net.HttpResponse;
import com.badlogic.gdx.Net.HttpResponseListener;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;

import com.openthid.librehero.entities.Song;
import com.openthid.librehero.entities.SongData;
import com.openthid.librehero.entities.SongData.BarData;
import com.openthid.librehero.entities.SongData.NoteData;

public class SongFile {

	private FileHandle file;
	private SongData songData;
	private float startTime;
	private float duration;
	private Music music;
	/**
	 * If <code>parse()</code> has been called yet.
	 */
	private boolean parsed;

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
		JsonValue audioDataElem = rootElem.get("audioData");
		
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
		music = makeMusic(audioDataElem);
		parsed = true;
	}

	private Music makeMusic(JsonValue audioDataElem) {
		String method = audioDataElem.getString("method");
		if (method.equals("url")) {//TODO Maybe implement this?
//			byte[] data = null;
//			
//			Consumer<byte[]> consumer = bs -> data = bs;
//			
//			HttpRequest httpRequest = new HttpRequest("GET");
//			httpRequest.setUrl(audioDataElem.getString("url"));
//			HttpResponseListener httpResponseListener = new HttpResponseListener() {
//				@Override
//				public void handleHttpResponse(HttpResponse httpResponse) {
//					consumer.accept(httpResponse.getResult());
//				}
//				
//				@Override
//				public void failed(Throwable t) {
//					// LATER Handle error
//				}
//				
//				@Override
//				public void cancelled() {
//					// LATER Handle error
//				}
//			};
//			Gdx.net.sendHttpRequest(httpRequest, httpResponseListener);
//			return Gdx.audio.newMusic(null);
			return null;
		} else if (method.equals("file")) {
			String file = audioDataElem.getString("file");
			return Gdx.audio.newMusic(Gdx.files.absolute(file));
		} else {
			return null;
		}
	}

	public SongData getSongData() {
		return songData;
	}

	public Music getMusic() {
		return music;
	}

	public Song makeSong() {
		if (!parsed) {
			parse();
		}
		return new Song(getMusic(), songData, duration, startTime);
	}
}