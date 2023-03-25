package gameObjects;

import engine.GameObject;
import engine.Sprite;

public class Piece extends GameObject{
	int dropTo = -1;
	double vy = 0;
	
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
	}
	
	public void dropTo (int to) {
		dropTo = to;
	}
}
