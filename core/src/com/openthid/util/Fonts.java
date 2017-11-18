package com.openthid.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class Fonts {
	public static final BitmapFont ubuntuMono40 = getFont("fonts/ubuntu-mono-40.fnt");
	public static final BitmapFont ubuntuMono25 = getFont("fonts/ubuntu-mono-25.fnt");
	public static final BitmapFont ubuntuMedium40 = getFont("fonts/ubuntu-medium-40.fnt");

	private static BitmapFont getFont(String filename) {
		return new BitmapFont(Gdx.files.internal(filename));
	}
}