package gameObjects;

import engine.GameCode;
import engine.Sprite;
import javafx.scene.media.AudioClip;

public class Jerry extends Enemy{
	
	AudioClip clip = new AudioClip ("file:resources/music/idk2.wav");
	
	public Jerry (Connect c) {
		super (c);
		this.pieceType = 10;
		this.difficulty = 7;
		this.setSprite(new Sprite ("resources/sprites/Jerry the Jragon.png"));
		this.background = new Sprite ("resources/sprites/Office.png");

		this.frameOrNah = false;

		clip.setCycleCount (100);
		clip.play ();

	}
	
	@Override
	public void onDefeat () {
		GameCode.defeatedJerry = true;
		
		
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
