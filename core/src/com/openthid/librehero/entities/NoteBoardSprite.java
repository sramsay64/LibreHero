package com.openthid.librehero.entities;

import java.util.HashMap;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.PolygonRegion;
import com.badlogic.gdx.graphics.g2d.PolygonSprite;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;

import com.openthid.librehero.entities.SongData.Bar;
import com.openthid.librehero.entities.SongData.Note;
import com.openthid.librehero.screens.FieldScreen;

public class NoteBoardSprite {

	private static final short[] rectShorts = new short[]{0,1,2,2,3,0};
	private static final HashMap<Character, Texture> keyboardSprites = new HashMap<>();

	private int xPos;
	private int yPos;
	private int xSize;
	private int ySize;
	private float skewFactor;

	int columns; // Number of columns that notes can be in
	float width; // Width of each line separating the columns and on the edges
	float unit; // The width of each columns

	private NoteBoard board;
	private Song song;

	private TextureRegion whiteDot;
	private PolygonSprite[] boardSprites;

	public NoteBoardSprite(int xPos, int yPos, int xSize, int ySize, float skewFactor, NoteBoard board, Song song) {
		this.xPos = xPos;
		this.yPos = yPos;
		this.xSize = xSize;
		this.ySize = ySize;
		this.skewFactor = skewFactor;
		this.board = board;
		this.song = song;
		
		columns = board.getColumns();
		width = 10;
		unit = (xSize-(columns+1)*width)/columns;
		
		Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
		pixmap.setColor(1, 1, 1, 1);
		pixmap.fill();
		whiteDot = new TextureRegion(new Texture(pixmap));
		
		boardSprites = makeBoardSprites();
	}

	private PolygonSprite[] makeBoardSprites() {
		PolygonSprite[] sprites = new PolygonSprite[columns+4];
		
		sprites[0] = rect(0, 0, xSize, ySize);
		sprites[0].setColor(0, 0.6f, 1, 1);
		
		for (int i = 0; i < columns+1; i++) {
			float x = (xSize-width)/columns*i;
			sprites[i+1] = rect(x, 0, x+width, ySize);
			sprites[i+1].setColor(0.1f, 0, 1, 1);
		}
		
		sprites[columns+2] = rect(0, 0, xSize, width);
		sprites[columns+2].setColor(0.1f, 0, 1, 1);
		sprites[columns+3] = rect(0, unit+width, xSize, unit+width*2);
		sprites[columns+3].setColor(0.1f, 0, 1, 1);
		
		return sprites;
	}

	public void draw(PolygonSpriteBatch batch) {
		skewFactor = FieldScreen.fff;
		boardSprites = makeBoardSprites();
		
		for (int i = 0; i < boardSprites.length; i++) {
			boardSprites[i].draw(batch);
		}
		batch.draw(getKeyTexture('A'), 40, 40);
		
		for (int i = 0; i < song.getKeys().length; i++) {
			for (int j = 0; j < 9; j++) {
				Note note = new Note(i, j);
				drawNote(batch, note);
			}
		}
		Note note = new Note(3, 3.5f);
		drawNote(batch, note);
		
		for (int i = 0; i < song.getBars().length; i++) {
			drawBar(batch, song.getBars()[i]);
		}
	}

	private void drawBar(PolygonSpriteBatch batch, Bar bar) {
		float y = bar.time;
		PolygonSprite lineSprite = rect(0, y, xSize, y+10);
		lineSprite.setColor(0.8f, 0, 0.3f, 1);
		lineSprite.draw(batch);
	}

	private void drawNote(PolygonSpriteBatch batch, Note note) {
		Texture texture = getKeyTexture(song.getKeys()[note.pitch]);
		float beatsAway = (note.time-song.getCurrentTime()); //+ FieldScreen.fff3*8-4; // TEMP
		float y = unit*beatsAway + unit/2 + width;
		int pitch = note.pitch;
		float x = (pitch+1)*(width+unit)  -0.5f*unit;
		
		float scale = 0.8f;
//		drawTexture(batch, texture, x, y, 0.8f);
//	}
//
//	private void drawTexture(PolygonSpriteBatch batch, Texture texture, float x, float y, float scale) {
		float angle = projectZeroAngle(x);
		float width = projectWidth(texture.getWidth(), y);
		float height = projectWidth(texture.getHeight(), y);
		
		float x2 = projectX(x, y);
		float y2 = projectY(x, y);
//		float yp = projectY(preProject(x), preProject(y));
		batch.draw(
				texture,
				x2-width/2, // x
				y2-height/2, // y
				width/2, height/2, // originX, originY
				width, height, // width, height
				scale, scale, angle, //scaleX, scaleY, rotation
				0, 0, texture.getWidth(), texture.getHeight(), // srcX, srcY, srcWidth, srcHeight
				false, false // flipX, flipY
			);
//		float beatsAway2 = beatsAway;
//		while (beatsAway2 > 0) {
//			projectWidth(0, y);
//			beatsAway2--;
//		}
		
//		PolygonSprite sprite2 = rect(x-2, yp-2, x+2, yp+2);
//		sprite2.setColor(0.3f, 1, 0, 1);
//		sprite2.draw(batch);
	}

	private float preProject(float value) { // TODO Make this actually work
		return value*skewFactor + ((float) Math.sqrt(value)*10)*(1-skewFactor);
	}

	private PolygonSprite rect(float x1, float y1, float x2, float y2) {
		float[] vertices = new float[]{x1,y1,x1,y2,x2,y2,x2,y1};
		project(vertices);
		PolygonRegion polygonRegion = new PolygonRegion(whiteDot, vertices, rectShorts);
		PolygonSprite sprite = new PolygonSprite(polygonRegion);
		return sprite;
	}

	private float projectWidth(float width, float y) {
		float heightRatio = y/ySize;
		return width*(1 + (skewFactor-1)*heightRatio);
	}

	/**
	 * Projects the angle 0 degrees to the screen
	 * @param x x in the sprite space
	 * @return the projected angle
	 */
	private float projectZeroAngle(float x) {
		float xRatio = (2*x - xSize)/xSize;
		float maxAngle = 90-MathUtils.atan2(ySize, xSize/2*(1-skewFactor))*MathUtils.radiansToDegrees;
		float angle = xRatio * maxAngle;
		return angle;
	}

	/**
	 * Projects the x coordinate from the space within the sprite to the space of the screen.
	 * It skews the top of the space according to the skew factor
	 */
	private float projectX(float x, float y) {
		float halfWidthPos = xPos + xSize/2;
		float x2 = x - xSize/2;
		return halfWidthPos + projectWidth(x2, y);
	}

	/**
	 * Projects vector from the space within the sprite to the space of the screen.
	 * It skews the top of the space according to the skew factor
	 */
	private float projectY(float x, float y) {
		return y + yPos;
	}

	/**
	 * Projects vectors from the space within the sprite to the space of the screen.
	 * It skews the top of the space according to the skew factor.
	 * All modifications are done in place
	 */
	private void project(float[] vertices) {
		if (vertices.length%2 == 1) {
			throw new IllegalArgumentException("vertices.length must be even");
		}
		for (int i = 0; i < vertices.length/2; i++) {
			float x = vertices[2*i];
			float y = vertices[2*i+1];
			vertices[2*i] = projectX(x, y);
			vertices[2*i+1] = projectY(x, y);
		}
	}

	private Texture getKeyTexture(char key) {
		if (Character.isLowerCase(key)) {
			key = Character.toUpperCase(key);
		}
		if (!keyboardSprites.containsKey(key)) {
			Texture texture = new Texture("Xelu_FREE_keyboard&controller_prompts_pack/Keyboard & Mouse/Light/Keyboard_White_" + key + ".png");
			keyboardSprites.put(key, texture);
		}
		return keyboardSprites.get(key);
	}
}