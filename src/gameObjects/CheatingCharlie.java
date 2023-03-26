package gameObjects;

import java.util.ArrayList;
import java.util.Random;

import engine.GameCode;
import engine.Sprite;

public class CheatingCharlie extends Enemy {
	public CheatingCharlie (Connect connect) {
		
		super(connect);
		this.setSprite(new Sprite ("resources/sprites/config/cheatingCharlie.txt"));
		this.getAnimationHandler().setFlipHorizontal(true);
		pieceType = 7;
	}
	
	
}
