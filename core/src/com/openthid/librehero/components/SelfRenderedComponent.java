package com.openthid.librehero.components;

import java.util.function.Consumer;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;

import com.openthid.librehero.systems.RenderSystem;

public class SelfRenderedComponent implements Component {
	/**
	 * A function called by {@link RenderSystem}
	 * The object should render itself using the batches given
	 */
	public Consumer<PolygonSpriteBatch> renderCallback;

	public SelfRenderedComponent(Consumer<PolygonSpriteBatch> renderCallback) {
		this.renderCallback = renderCallback;
	}
}