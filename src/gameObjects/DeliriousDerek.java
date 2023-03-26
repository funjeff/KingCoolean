package gameObjects;

import java.util.ArrayList;
import java.util.Random;

import engine.GameCode;
import engine.Sprite;

public class DeliriousDerek extends Enemy {
	public DeliriousDerek (Connect connect) {
		
		super(connect);
		this.setSprite(new Sprite ("resources/sprites/config/deliriousDerek.txt"));
		this.getAnimationHandler().setFlipHorizontal(true);
		pieceType = 5;
		this.playSound("DeliriousDerekIntro.wav");
	}
	
	@Override
	public void onDefeat() {
	this.mapConnect.setAbove(mapConnect.getUpConnectByPosition());
	this.mapConnect.getAbove().setBelow(this.mapConnect);
		
		GameCode.map.declare();
	}
	@Override
	public void onVictory() {
		GameCode.map.declare();
	}
	
	@Override
	public void onDefeatLine () {
		this.playSound("DeliriousDerekPlayerWins.wav");
	}
	

	
	
 }
