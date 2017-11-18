package com.openthid.librehero;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.SerializationException;
import com.openthid.librehero.entities.Song;
import com.openthid.librehero.entities.SongData;
import com.openthid.librehero.entities.SongData.BarData;
import com.openthid.librehero.entities.SongData.NoteData;

public class SongFile {

	@SuppressWarnings("serial")
	public class InvalidSongFileException extends Exception {

		public InvalidSongFileException(URISyntaxException e) {
			super("Invalid URI Syntax", e);
		}

		public InvalidSongFileException(SerializationException e) {
			super("Invalid JSON", e);
		}

		public InvalidSongFileException(IllegalArgumentException e) {
			super("Illegal Argument", e);
		}
	}

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

	public void parse() throws InvalidSongFileException {
		try {
			JsonReader reader = new JsonReader();
			JsonValue rootElem = reader.parse(file);
			JsonValue songDataElem = rootElem.get("songData");
			JsonValue notesDataElem = songDataElem.get("notes");
			JsonValue barsDataElem = songDataElem.get("bars");
			JsonValue keysDataElem = songDataElem.get("keys");
			JsonValue audioDataElem = rootElem.get("audioData");
			
			NoteData[] notes = new NoteData[notesDataElem.size];
			for (int i = 0; i < notesDataElem.size; i++) {
				int points = 10;
				if (notesDataElem.get(i).has("points")) {
					points = notesDataElem.get(i).getInt("points");
				}
				notes[i] = new NoteData(notesDataElem.get(i).getInt("pitch"), notesDataElem.get(i).getFloat("time"), points);
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
			int falseHitPoints = songDataElem.getInt("falseHitPoints");
			
			songData = new SongData(notes, bars, keys, tempo, falseHitPoints);
			music = makeMusic(audioDataElem);
			parsed = true;
		} catch (SerializationException e) {
			throw new InvalidSongFileException(e);
		}
	}

	private Music makeMusic(JsonValue audioDataElem) throws InvalidSongFileException {
		String method = audioDataElem.getString("method");
		if (method.equals("file")) {
			String file = audioDataElem.getString("file");
			return Gdx.audio.newMusic(Gdx.files.absolute(file));
		} else if (method.equals("url")) {
			String url = audioDataElem.getString("url");
//			HttpRequest getRequest = new HttpRequest(HttpMethods.GET);
//			getRequest.setUrl(url);
//			Object waitObj;
//			byte[] data;
//			HttpResponseListener rl;
//			rl = new HttpResponseListener() {
//				
//				@Override
//				public void handleHttpResponse(HttpResponse httpResponse) {
//					data = httpResponse.getResult();
//					waitObj.notify();
//				}
//				
//				@Override
//				public void failed(Throwable t) {
//					// TODO Auto-generated method stub
//				}
//				
//				@Override
//				public void cancelled() {
//					// TODO Auto-generated method stub
//				}
//			};
//			Gdx.net.sendHttpRequest(getRequest, rl);
//			waitObj.wait();
			try {
				return Gdx.audio.newMusic(new FileHandle(new File(new URI(url))));
			} catch (URISyntaxException e) {
				throw new InvalidSongFileException(e);
			} catch (IllegalArgumentException e) {
				throw new InvalidSongFileException(e);
			}
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

	public Song makeSong() throws InvalidSongFileException {
		if (!parsed) {
			parse();
		}
		return new Song(getMusic(), songData, duration, startTime);
	}
}