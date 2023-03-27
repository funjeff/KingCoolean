package gameObjects;

import engine.GameCode;
import engine.Sprite;
import javafx.scene.media.AudioClip;

public class WeffJiener extends Enemy{
	AudioClip clip = new AudioClip ("file:resources/music/idk3.wav");
	public WeffJiener (Connect c) {
		super (c);
		this.pieceType = 11;
		this.difficulty = 7;
		this.playSound("JeffWeinerIntro2.wav");
		this.setSprite(new Sprite ("resources/sprites/Weff Jeiner.png"));
		this.background = new Sprite ("resources/sprites/SPACE.png");

		this.frameOrNah = false;

		clip.setCycleCount (100);
		clip.play ();
	}
	
	@Override
	public void onDefeat () {
		GameCode.map.declare();

		

		ConnectFourGame.unlockedMoves[0] = true;
		clip.stop ();
		
	}
	@Override
	public void onVictory() {
		
	}

	@Override
	public void onDefeatLine () {
	
	}

	@Override
	public void onVictoryLine () {
	}
	
}
