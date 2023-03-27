package gameObjects;

import java.util.Random;

import engine.GameObject;
import engine.Sprite;
import javafx.scene.media.AudioClip;

public class Piece extends GameObject{
	int dropTo = -1;
	double vy = 0;

	int bounceLeftTime = 0;
	
	int curPosX;
	int curPosY;

	
	public Piece (int color) {
		this.setRenderPriority(-3);
		
		setColor (color);
		
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
					AudioClip clip = new AudioClip("file:resources/sound/" + "peiceDrop.wav");
					clip.play();
					vy = -vy * .8;
				} else {
					vy = 0;
					this.setY(dropTo);
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
	
	public void setCurPosX(int curPosX) {
		this.curPosX = curPosX;
	}
	
	public void setCurPosY(int curPosY) {
		this.curPosY = curPosY;
	}
	
	public int getCurPosX () {
		return this.curPosX;
	}
	
	public int getCurPosY () {
		return this.curPosY;
	}
	
	
	public void setColor (int color) {
		switch (color) {
		case 0:
			this.setSprite(new Sprite ("resources/sprites/red piece.png"));
			break;
		case 1:
			this.setSprite(new Sprite ("resources/sprites/Horizonjtal Henry Piece.png"));
			break;
		case 2:
			this.setSprite(new Sprite ("resources/sprites/Left Larry Piece.png"));
			break;
		case 3:
			this.setSprite(new Sprite ("resources/sprites/Mirror Merryl Piece.png"));
			break;
		case 4:
			this.setSprite(new Sprite ("resources/sprites/Random Randy Piece.png"));
			break;
		case 5:
			this.setSprite(new Sprite ("resources/sprites/Delerious Derryl.png"));
			break;
		case 6:
			this.setSprite(new Sprite ("resources/sprites/Dark Coolean Piece.png"));
			break;
		case 7:
			this.setSprite(new Sprite ("resources/sprites/Cheating Charlie coin.png"));
			break;
		case 8:
			this.setSprite(new Sprite ("resources/sprites/imagamer coin.png"));
			break;
		case 9:
			this.setSprite(new Sprite ("resources/sprites/jeff weiner coin.png"));
			break;
		case 10:
			this.setSprite(new Sprite ("resources/sprites/Jerry the Jragon Piece.png"));
			break;
		case 11:
			this.setSprite(new Sprite ("resources/sprites/weff geiner piece.png"));
			break;
		}
	}
}
