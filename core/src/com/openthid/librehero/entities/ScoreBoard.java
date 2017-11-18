package com.openthid.librehero.entities;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.PolygonRegion;
import com.badlogic.gdx.graphics.g2d.PolygonSprite;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import com.openthid.librehero.components.SelfRenderedComponent;
import com.openthid.librehero.entities.Song.Note;
import com.openthid.util.Fonts;

public class ScoreBoard {

	private int xPos;
	private int yPos;
	private int xSize;
	private int ySize;

	private Entity entity;

	private int score = 0;

	public ScoreBoard(int xPos, int yPos, int xSize, int ySize) {
		this.xPos = xPos;
		this.yPos = yPos;
		this.xSize = xSize;
		this.ySize = ySize;
		
		SelfRenderedComponent selfRenderedComponent = new SelfRenderedComponent(this::draw);
		entity = new Entity();
		entity.add(selfRenderedComponent);
	}

	public void score(Note note) {
		addToScore(note.getPoints());
	}

	public void addToScore(int points) {
		score += points;
	}

	public Entity getEntity() {
		return entity;
	}

	public int getScore() {
		return score;
	}

	private void draw(PolygonSpriteBatch batch) {
		PolygonRegion region = makeRect(xPos, yPos, xPos+xSize, yPos+ySize);
		PolygonSprite box = new PolygonSprite(region);
		
		box.draw(batch);
		
		Fonts.ubuntuMono25.draw(batch, "Score: " + score, xPos + 10, yPos + ySize - 10);
	}

	private static PolygonRegion makeRect(float x1, float y1, float x2, float y2) {
		Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
		pixmap.setColor(1, 1, 1, 0.3f);
		pixmap.fill();
		TextureRegion whiteDot = new TextureRegion(new Texture(pixmap));
		
		float[] vertices = new float[]{x1,y1,x1,y2,x2,y2,x2,y1};
		PolygonRegion region = new PolygonRegion(whiteDot, vertices, new short[]{0,1,2,2,3,0});
		return region;
	}
}