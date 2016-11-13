package com.openthid.librehero.entities;

import java.util.HashMap;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.PolygonRegion;
import com.badlogic.gdx.graphics.g2d.PolygonSprite;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class NoteBoardSprite {

	private static final short[] rectShorts = new short[]{0,1,2,2,3,0};
	private static final HashMap<Character, Texture> keyboardSprites = new HashMap<>();

	private int xPos;
	private int yPos;
	private int xSize;
	private int ySize;
	private float skewFactor;

	private NoteBoard board;
	private TextureRegion whiteDot;

	private PolygonSprite boardSprite;

	public NoteBoardSprite(int xPos, int yPos, int xSize, int ySize, float skewFactor, NoteBoard board) {
		this.xPos = xPos;
		this.yPos = yPos;
		this.xSize = xSize;
		this.ySize = ySize;
		this.skewFactor = skewFactor;
		this.board = board;
		
		Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
		pixmap.setColor(1, 1, 1, 1);
		pixmap.fill();
		whiteDot = new TextureRegion(new Texture(pixmap));
		
		boardSprite = rect(0, 0, xSize, ySize);
		boardSprite.setColor(0, 0.6f, 1, 1);
	}

	public void draw(PolygonSpriteBatch batch) {
		boardSprite.draw(batch);
//		TextureRegion keyATR = new TextureRegion(getKeyTexture('A'));
//		batch.draw(keyATR, 40, 40);
		
		drawTexture(batch, getKeyTexture('A'), 99, 99);
		
		
//		PolygonRegion r = new PolygonRegion(keyATR, new float[]{0,0,90,190,190,190,190,90}, rectShorts);
//		batch.draw(r, 0, 0);
//		PolygonSprite keyA = rect(keyATR, 40, 40, keyATR.getRegionWidth(), keyATR.getRegionHeight());
//		keyA.setPosition(6, 90);
//		keyA.draw(batch);
	}

	private void drawTexture(PolygonSpriteBatch batch, Texture texture, float x, float y) {
//		batch.draw(texture, x, y, originX, originY, width, height, scaleX, scaleY, rotation, srcX, srcY, srcWidth, srcHeight, flipX, flipY);
		batch.draw(
				texture,
				projectX(x, y), projectY(x, y),	// x, y
				0, 0,	// originX, originY
				500, 90,	// width, height
				1, 1, 0,	//scaleX, scaleY, rotation
				0, 0, texture.getWidth(), texture.getHeight(),	// srcX, srcY, srcWidth, srcHeight
				false, false	// flipX, flipY
			);
//		batch.draw(texture, spriteVertices, offset, count);	
	}

	private PolygonSprite rect(float x1, float y1, float x2, float y2) {
		float[] vertices = new float[]{x1,y1,x1,y2,x2,y2,x2,y1};
		project(vertices);
		PolygonRegion polygonRegion = new PolygonRegion(whiteDot, vertices, rectShorts);
		PolygonSprite sprite = new PolygonSprite(polygonRegion);
		return sprite;
	}

//	private float projectWidth(float width, float y) {
//		
//	}

	/**
	 * Projects vector from the space within the sprite to the space of the screen.
	 * It skews the top of the space according to the skew factor
	 */
	private float projectX(float x, float y) {
		float halfWidthPos = xPos + xSize/2;
		float x2 = x - xSize/2;
		float heightRatio = y/ySize;
		return halfWidthPos + x2*(1 + (skewFactor-1)*heightRatio);
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
		if (!keyboardSprites.containsKey(key)) {
//			Texture texture = new Texture("Xelu_FREE_keyboard&controller_prompts_pack/Keyboard & Mouse/Light/Keyboard_White_" + key + ".png");
			Texture texture = new Texture("badlogic.jpg");
			keyboardSprites.put(key, texture);
		}
		return keyboardSprites.get(key);
	}
}