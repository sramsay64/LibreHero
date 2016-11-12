package com.openthid.librehero.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.FloatArray;

import com.openthid.librehero.components.SelfRenderedComponent;

public class RenderSystem extends EntitySystem {

	private ImmutableArray<Entity> selfRenderedEntities;

	private int screenX;
	private int screenY;

	private Batch batch;

	public RenderSystem(Batch batch, int screenX, int screenY) {
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
			selfRenderedComponent.renderCallback.accept(this::drawCallback);
		}
		batch.end();
	}

	/**
	 * Passed to {@link SelfRenderedComponent}s for them to draw to
	 */
	private void drawCallback(Texture texture, FloatArray floats, Vector2 vec) {
		float rotationA = floats.items[4];
		float rotationB = floats.items[5];
		//batch.draw(texture, x, y, originX, originY, width, height, scaleX, scaleY, rotation, srcX, srcY, srcWidth, srcHeight, flipX, flipY);
		batch.draw(texture,
				floats.items[0] + vec.x*MathUtils.cosDeg(rotationB) - vec.y*MathUtils.sinDeg(rotationB) - floats.items[2]/2,
				floats.items[1] + vec.y*MathUtils.cosDeg(rotationB) + vec.x*MathUtils.sinDeg(rotationB) - floats.items[3]/2,
				floats.items[2]/2,
				floats.items[3]/2,
				floats.items[2],
				floats.items[3],
				1,1, //No scaling
				rotationA + rotationB,
				0,0,
				texture.getWidth(),
				texture.getHeight(),
				false,false
			);
	};
}