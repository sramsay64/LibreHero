package com.openthid.librehero.entities;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;

import com.openthid.librehero.components.SelfRenderedComponent;

public class NoteBoard {

	private Entity entity;
	private NoteBoardSprite sprite;

	public NoteBoard(int xPos, int yPos, int xSize, int ySize, float skewFactor) {
		SelfRenderedComponent selfRenderedComponent = new SelfRenderedComponent(this::draw);
		entity = new Entity();
		entity.add(selfRenderedComponent);
		
		sprite = new NoteBoardSprite(xPos, yPos, xSize, ySize, skewFactor, this);
	}

	private void draw(PolygonSpriteBatch batch) {
		sprite.draw(batch);
	}

	public Entity getEntity() {
		return entity;
	}
}