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
	this.mapConnect.setLeft(mapConnect.getLeftConnectByPosition());
	this.mapConnect.getLeft().setRight(this.mapConnect);
		this.playSound("DeliriousDerekPlayerWins.wav");
		GameCode.map.declare();
		clip.stop ();
	}
	@Override
	public void onVictory() {
		this.playSound("DeliriousDerekPlayerWins.wav");
	}
 }
