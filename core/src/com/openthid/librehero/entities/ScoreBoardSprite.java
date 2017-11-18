package com.openthid.librehero.entities;

import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;

public class ScoreBoardSprite {

	private int xPos;
	private int yPos;
	private int xSize;
	private int ySize;

	public ScoreBoardSprite(int xPos, int yPos, int xSize, int ySize, ScoreBoard scoreBoard) {
		this.xPos = xPos;
		this.yPos = yPos;
		this.xSize = xSize;
		this.ySize = ySize;
	}

	public void draw(PolygonSpriteBatch batch) {
		
	}
}