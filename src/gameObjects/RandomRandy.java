package gameObjects;

import java.util.Random;

import engine.Sprite;

public class RandomRandy extends Enemy {
	
	public RandomRandy () {
		this.setSprite(new Sprite ("resources/sprites/config/randomRandy.txt"));
		this.getAnimationHandler().setFlipHorizontal(true);
	}
	
	public int getMove (int [] [] boardState) {
		Random rand = new Random();
		int curr = rand.nextInt(7);
		while (boardState[curr][0] != 0) {
			curr = rand.nextInt(7);
		}
		return curr;
	}
}
