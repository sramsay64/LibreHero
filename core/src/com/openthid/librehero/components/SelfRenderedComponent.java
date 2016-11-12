package com.openthid.librehero.components;

import java.util.function.Consumer;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.FloatArray;

import com.openthid.librehero.systems.RenderSystem;
import com.openthid.util.TriConsumer;

public class SelfRenderedComponent implements Component {
	/**
	 * A function called by {@link RenderSystem} that is given a callback
	 * that it can call multiple times to draw different textures.
	 * The first parameter is the texture and the second is an array
	 * as follows {x position, y position, width, height} all using world units.
	 * The array can also have a fifth element which is the rotation (in degrees).
	 * The vector is a measurement in world units of where the center of rotation is.
	 */
	public Consumer<TriConsumer<Texture, FloatArray, Vector2>> renderCallback;

	public SelfRenderedComponent(Consumer<TriConsumer<Texture, FloatArray, Vector2>> renderCallback) {
		this.renderCallback = renderCallback;
	}
}