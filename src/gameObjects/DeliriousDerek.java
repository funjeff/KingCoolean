package gameObjects;

import java.util.ArrayList;
import java.util.Random;

import engine.GameCode;
import engine.Sprite;
import javafx.scene.media.AudioClip;

public class DeliriousDerek extends Enemy {
	
	AudioClip clip = new AudioClip ("file:resources/music/idk2.wav");
	public DeliriousDerek (Connect connect) {
		
		super(connect);
		this.setSprite(new Sprite ("resources/sprites/config/deliriousDerek.txt"));
		this.getAnimationHandler().setFlipHorizontal(true);
		pieceType = 5;
		this.playSound("DeliriousDerekIntro.wav");
		clip.setCycleCount (100);
		clip.play ();
	}
	
	@Override
	public void onDefeat() {
	this.mapConnect.setAbove(mapConnect.getUpConnectByPosition());
	this.mapConnect.getAbove().setBelow(this.mapConnect);
		
		GameCode.map.declare();
		clip.stop ();
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
