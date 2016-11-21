package com.openthid.librehero.entities;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;

import com.openthid.librehero.components.SelfRenderedComponent;

public class NoteBoard {

	private Entity entity;
	private NoteBoardSprite sprite;

	private Song song;

	public NoteBoard(int xPos, int yPos, int xSize, int ySize, float skewFactor, Song song) {
		this.song = song;
		
		SelfRenderedComponent selfRenderedComponent = new SelfRenderedComponent(this::draw);
		entity = new Entity();
		entity.add(selfRenderedComponent);
		
		sprite = new NoteBoardSprite(xPos, yPos, xSize, ySize, skewFactor, this, song);
	}

	public char[] getKeys() {
		return song.getKeys();
	}

	private void draw(PolygonSpriteBatch batch) {
		sprite.draw(batch);
	}

	public Entity getEntity() {
		return entity;
	}

	public int getColumns() {
		return getKeys().length;
	}
}