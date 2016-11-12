package com.openthid.librehero.entities;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.FloatArray;

import com.openthid.librehero.components.SelfRenderedComponent;
import com.openthid.util.TriConsumer;

public class NoteBoard {

	private Entity entity;
	private NoteBoardSprite sprite;

	public NoteBoard(int xPos, int yPos, int xSize, int ySize, float skewFactor) {
		SelfRenderedComponent selfRenderedComponent = new SelfRenderedComponent(this::draw);
		entity = new Entity();
		entity.add(selfRenderedComponent);
		
		sprite = new NoteBoardSprite(xPos, yPos, xSize, ySize, skewFactor);
	}

	private void draw(TriConsumer<Texture, FloatArray, Vector2> consumer) {
		sprite.draw(consumer);
	}

	public Entity getEntity() {
		return entity;
	}
}