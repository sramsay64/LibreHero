package com.openthid.librehero.entities;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.PolygonRegion;
import com.badlogic.gdx.graphics.g2d.PolygonSprite;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.FloatArray;

import com.openthid.util.TriConsumer;

public class NoteBoardSprite {

	private static final short[] rectShorts = new short[]{0,1,2,2,3,0};
	private int xPos;
	private int yPos;
	private int xSize;
	private int ySize;
	private float skewFactor;

	private ShapeRenderer renderer;
	private PolygonSpriteBatch batch;
	private Texture whiteDot;

	public NoteBoardSprite(int xPos, int yPos, int xSize, int ySize, float skewFactor) {
		this.xPos = xPos;
		this.yPos = yPos;
		this.xSize = xSize;
		this.ySize = ySize;
		this.skewFactor = skewFactor;
		renderer = new ShapeRenderer();
		batch = new PolygonSpriteBatch();

		Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
		pixmap.setColor(1, 1, 1, 1);
		pixmap.fill();
		whiteDot = new Texture(pixmap);
	}

	public void draw(TriConsumer<Texture, FloatArray, Vector2> consumer) {
//		renderer.begin(ShapeType.Filled);
		renderer.setColor(0.2f, 0.2f, 0, 1);
		rect(0, 0, xSize, ySize);
		rect(50, 40, 210, 700);
//		renderer.end();
	}

	@SuppressWarnings("unused")
	@Deprecated
	private void line(float x1, float y1, float x2, float y2) {
		Vector2 v1 = project(new Vector2(x1,y1));
		Vector2 v2 = project(new Vector2(x2,y2));
		renderer.line(v1, v2);
	}

	private void rect(float x1, float y1, float x2, float y2) {
		Vector2[] vectors = new Vector2[4];
		vectors[0] = project(new Vector2(x1,y1));
		vectors[1] = project(new Vector2(x1,y2));
		vectors[2] = project(new Vector2(x2,y2));
		vectors[3] = project(new Vector2(x2,y1));
		float[] vertices = new float[8];
		for (int i = 0; i < vectors.length; i++) {
			vertices[2*i] = vectors[i].x;
			vertices[2*i+1] = vectors[i].y;
		}
		
		PolygonRegion polygonRegion = new PolygonRegion(new TextureRegion(whiteDot), vertices, rectShorts);
		PolygonSprite sprite = new PolygonSprite(polygonRegion);
		batch.begin();
		sprite.setColor(0, 0.6f, 1, 1);
		sprite.draw(batch);
		batch.end();
	}

	/**
	 * Projects vectors from the space within the sprite to the space of the screen.
	 * It skews the top of the space according to the skew factor
	 * @param v vector to project
	 * @return Projected vector
	 */
	private Vector2 project(Vector2 v) {
		float halfWidthPos = xPos + xSize/2;
		float x = v.x - xSize/2;
		float heightRatio = v.y/ySize;
		return new Vector2(halfWidthPos+x*(1-heightRatio+skewFactor*heightRatio), v.y+yPos);
	}
}