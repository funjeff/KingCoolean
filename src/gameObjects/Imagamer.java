package gameObjects;

import engine.GameCode;
import engine.Sprite;
import javafx.scene.media.AudioClip;

public class Imagamer extends Enemy{
	
	AudioClip clip = new AudioClip ("file:resources/music/idk2.wav");
	public Imagamer (Connect c) {
		super (c);

		this.pieceType = 8;

		this.playSound("ImagamerIntro.wav");


		this.difficulty = 7;
		this.setSprite(new Sprite ("resources/sprites/config/imagamer.txt"));
		clip.setCycleCount (100);
		clip.play ();
	}
	
	@Override
	public void onDefeat () {
		GameCode.defeatedImagamer = true;
		if (GameCode.defeatedJerry && GameCode.defeatedImagamer && GameCode.defeatedDarkCoolean) {
			ConnectFourGame g = new ConnectFourGame();
			g.setEnemy(new JeffWeiner(new Connect ()));
			g.declare();
		} else {
			GameCode.map.declare();
		}
		clip.stop();
	}
	
	@Override
	public void onVictory () {
		GameCode.map.declare();
		clip.stop ();
	}

}
