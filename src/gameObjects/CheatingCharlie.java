package gameObjects;

import java.util.ArrayList;
import java.util.Random;

import engine.GameCode;
import engine.Sprite;
import javafx.scene.media.AudioClip;

public class CheatingCharlie extends Enemy {
	AudioClip clip = new AudioClip ("file:resources/music/idk2.wav");
	public CheatingCharlie (Connect connect) {
		
		super(connect);
		this.setSprite(new Sprite ("resources/sprites/config/cheatingCharlie.txt"));
		this.getAnimationHandler().setFlipHorizontal(true);
		pieceType = 7;
		clip.setCycleCount (100);
		clip.play ();
	}
	
	
	
	public void onVictory () {
		clip.stop();
		GameCode.map.declare();
	}
}
