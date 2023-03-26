package gameObjects;

import java.util.ArrayList;
import java.util.Random;

import engine.Sprite;

public class DeliriousDerek extends Enemy {
	public DeliriousDerek (Connect connect) {
		
		super(connect);
		this.setSprite(new Sprite ("resources/sprites/config/deliriousDerek.txt"));
		this.getAnimationHandler().setFlipHorizontal(true);
		pieceType = 5;
		this.playSound("DeliriousDerekIntro.wav");
	}
	
	public void onDefeat() {
		this.playSound("DeliriousDerekPlayerWins.wav");
	}
	public void onVictory() {
		this.playSound("DeliriousDerekPlayerWins.wav");
	}
 }
