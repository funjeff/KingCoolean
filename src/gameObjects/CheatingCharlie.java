package gameObjects;

import engine.GameCode;
import engine.Sprite;
import engine.AudioClip;

public class CheatingCharlie extends Enemy {
	AudioClip clip = new AudioClip ("file:resources/music/idk2.wav");
	AudioClip crashSound = new AudioClip ("file:resources/sound/crash.wav");
	public CheatingCharlie (Connect connect) {
		
		super(connect);
		this.setSprite(new Sprite ("resources/sprites/config/cheatingCharlie.txt"));
		this.getAnimationHandler().setFlipHorizontal(true);
		pieceType = 7;
		clip.loop ();
	}
	
	public void onVictory () {
		clip.stop();
		GameCode.map.declare();
	}
	
	public void onDefeat () {
		clip.stop ();
		crashSound.play ();
		try {
			Thread.sleep (2500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.exit (0);
	}
	
}
