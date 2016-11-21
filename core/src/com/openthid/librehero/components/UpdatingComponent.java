package com.openthid.librehero.components;

import java.util.function.Consumer;

import com.badlogic.ashley.core.Component;

public class UpdatingComponent implements Component {
	/**
	 * A {@link Consumer} of the deltaTime
	 */
	public Consumer<Float> callback;

	public UpdatingComponent(Consumer<Float> callback) {
		this.callback = callback;
	}
}