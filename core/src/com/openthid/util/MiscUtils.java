package com.openthid.util;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.FloatArray;

public class MiscUtils {
	public static void drawCallback(Batch batch, Texture texture, FloatArray floats, Vector2 vec) {
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