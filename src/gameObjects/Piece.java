package gameObjects;

import java.util.Random;

import engine.GameObject;
import engine.Sprite;

public class Piece extends GameObject{
	int dropTo = -1;
	double vy = 0;

	int bounceLeftTime = 0;
	
	public Piece (boolean color) {
		if (color) {
			this.setSprite(new Sprite ("resources/sprites/red piece.png"));
		} else {
			this.setSprite(new Sprite ("resources/sprites/yellow piece.png"));
		}
	}
	
	@Override
	public void frameEvent () {
		if (dropTo != -1) {
			if (vy < 20) {
				vy = vy + 1;
			}
			this.setY(this.getY() + vy);
			if (this.getY() > dropTo && vy > 0) {
				if (vy > 2) {
					vy = -vy * .8;
				} else {
					vy = 0;
					dropTo = -1;
				}
			}
		}
		
		if (bounceLeftTime != 0) {
			Random r = new Random ();
			if (this.direction == -1) {
				this.throwObj(r.nextDouble() * (Math.PI - .314), 20);
			}
		
			super.frameEvent();
			bounceLeftTime = bounceLeftTime - 1;
			if (bounceLeftTime == 0) {
				this.forget();
			}
		}
	}
	
	public void dropTo (int to) {
		dropTo = to;
	}
	
	public void bounceLeft () {
		bounceLeftTime = 50;
	}
}
