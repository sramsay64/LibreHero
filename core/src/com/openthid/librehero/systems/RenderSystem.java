package com.openthid.librehero.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;

import com.openthid.librehero.components.SelfRenderedComponent;

public class RenderSystem extends EntitySystem {

	private ImmutableArray<Entity> selfRenderedEntities;

	private int screenX;
	private int screenY;

	private PolygonSpriteBatch batch;

	public RenderSystem(PolygonSpriteBatch batch, int screenX, int screenY) {
		this.batch = batch;
		this.screenX = screenX;
		this.screenY = screenY;
	}

	@Override
	public void addedToEngine(Engine engine) {
		selfRenderedEntities = engine.getEntitiesFor(Family.all(SelfRenderedComponent.class).get());
	}

	/**
	 * Method that draws the field
	 */
	@Override
	public void update(float deltaTime) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		for (int i = 0; i < selfRenderedEntities.size(); i++) {
			Entity entity = selfRenderedEntities.get(i);
			SelfRenderedComponent selfRenderedComponent = entity.getComponent(SelfRenderedComponent.class);
			selfRenderedComponent.renderCallback.accept(batch);
		}
		batch.end();
	}
}