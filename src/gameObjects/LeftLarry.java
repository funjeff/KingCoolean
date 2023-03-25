package gameObjects;

import engine.Sprite;

public class LeftLarry extends Enemy {
	
	public LeftLarry () {
		this.setSprite(new Sprite ("resources/sprites/config/leftLarry.txt"));
		this.getAnimationHandler().setFlipHorizontal(true);
	}
	
	public int getMove (int [] [] boardState) {
		return 0;
	}

}
