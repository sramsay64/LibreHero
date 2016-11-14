package com.openthid.librehero.entities;

import java.util.HashMap;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.IntUnaryOperator;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.PolygonRegion;
import com.badlogic.gdx.graphics.g2d.PolygonSprite;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;

import com.openthid.librehero.screens.FieldScreen;

public class NoteBoardSprite {

	private static final short[] rectShorts = new short[]{0,1,2,2,3,0};
	private static final HashMap<Character, Texture> keyboardSprites = new HashMap<>();

	private int xPos;
	private int yPos;
	private int xSize;
	private int ySize;
	private float skewFactor;

	int columns;
	float width;
	float unit;

	private NoteBoard board;

	private TextureRegion whiteDot;
	private PolygonSprite[] boardSprites;

	public NoteBoardSprite(int xPos, int yPos, int xSize, int ySize, float skewFactor, NoteBoard board) {
		this.xPos = xPos;
		this.yPos = yPos;
		this.xSize = xSize;
		this.ySize = ySize;
		this.skewFactor = skewFactor;
		this.board = board;
		
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
		sprites[columns+3] = rect(0, unit, xSize, unit+width);
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
		
		Texture a = getKeyTexture('A');
//		drawTexture(batch, a, FieldScreen.fff*xSize, (ySize-a.getHeight())/2);
//		drawTexture(batch, a, FieldScreen.fff*xSize, (ySize+10+a.getHeight())/2);
		
		IntFunction<Float> notePos = i -> (i+0.5f)*unit+(i+1)*width;
		
		drawTexture(batch, a, notePos.apply(0), 0*FieldScreen.fff*ySize+70*4, 0.8f);
		drawTexture(batch, a, notePos.apply(1), 0*FieldScreen.fff*ySize+70*3, 0.8f);
		drawTexture(batch, a, notePos.apply(2), 0*FieldScreen.fff*ySize+70*2, 0.8f);
		drawTexture(batch, a, notePos.apply(3), 0*FieldScreen.fff*ySize+70*7, 0.8f);
		drawTexture(batch, a, notePos.apply(4), 0*FieldScreen.fff*ySize+70*1, 0.8f);
		
		int y = (int) (FieldScreen.fff*ySize);
		PolygonSprite lineSprite = rect(0, y, xSize, y+10);
		lineSprite.setColor(0.8f, 0, 0.3f, 1);
		lineSprite.draw(batch);
	}

	private void drawTexture(PolygonSpriteBatch batch, Texture texture, float x, float y) {
		drawTexture(batch, texture, x, y, 1);
	}

	private void drawTexture(PolygonSpriteBatch batch, Texture texture, float x, float y, float scale) {
		float angle = projectZeroAngle(x);
		float width = projectWidth(texture.getWidth(), y);
		float height = projectWidth(texture.getHeight(), y);
		batch.draw(
				texture,
				projectX(x-texture.getWidth()/2, y),	// x
				projectY(x-texture.getWidth()/2, y),	// y
				width/2, height/2,	// originX, originY
				width, height,	// width, height
				scale, scale, angle,	//scaleX, scaleY, rotation
				0, 0, texture.getWidth(), texture.getHeight(),	// srcX, srcY, srcWidth, srcHeight
				false, false	// flipX, flipY
			);
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
		if (!keyboardSprites.containsKey(key)) {
			Texture texture = new Texture("Xelu_FREE_keyboard&controller_prompts_pack/Keyboard & Mouse/Light/Keyboard_White_" + key + ".png");
//			Texture texture = new Texture("badlogic.jpg");
			keyboardSprites.put(key, texture);
		}
		return keyboardSprites.get(key);
	}
}