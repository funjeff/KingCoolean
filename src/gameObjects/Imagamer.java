package gameObjects;

import engine.GameCode;
import engine.Sprite;
import javafx.scene.media.AudioClip;

public class Imagamer extends Enemy{
	
	AudioClip clip = new AudioClip ("file:resources/music/idk2.wav");
	public Imagamer (Connect c) {
		super (c);
		this.playSound("ImagamerIntro.wav");
		this.pieceType = 6;
		this.difficulty = 7;
		this.setSprite(new Sprite ("resources/sprites/config/imagamer.txt"));
		clip.setCycleCount (100);
		clip.play ();
	}
	
	@Override
	public void onDefeat () {
		GameCode.defeatedImagamer = true;
		GameCode.map.declare();
		clip.stop ();
	}

}
