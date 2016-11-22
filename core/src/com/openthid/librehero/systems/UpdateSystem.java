package com.openthid.librehero.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;

import com.openthid.librehero.components.UpdatingComponent;

public class UpdateSystem extends EntitySystem {

	private ImmutableArray<Entity> updatingComponents;

	public UpdateSystem() {
	}

	@Override
	public void addedToEngine(Engine engine) {
		updatingComponents = engine.getEntitiesFor(Family.all(UpdatingComponent.class).get());
	}

	@Override
	public void update(float deltaTime) {
		updatingComponents.forEach(e -> {
			e.getComponent(UpdatingComponent.class).callback.accept(deltaTime);
		});
	}
}