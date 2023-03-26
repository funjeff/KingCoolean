package gameObjects;

import engine.GameCode;
import engine.Sprite;
import javafx.scene.media.AudioClip;

public class JeffWeiner extends Enemy{
	AudioClip clip = new AudioClip ("file:resources/music/idk2.wav");
	public JeffWeiner (Connect c) {
		super (c);
		this.pieceType = 6;
		this.difficulty = 9;
		this.playSound("JeffWeinerIntro.wav");
		this.setSprite(new Sprite ("resources/sprites/jeff weiner.png"));
		this.background = new Sprite ("resources/sprites/SPACE.png");
		this.playSound("JeffWeinerIntro.wav");
		clip.setCycleCount (100);
		clip.play ();
	}
	
	@Override
	public void onDefeat () {
		ConnectFourGame g = new ConnectFourGame();
		g.setEnemy(new WeffJiener(new Connect ()));
		g.declare();
		this.playSound("JeffWeinerPlayerWins1.wav");
		clip.stop ();
	}
	
	@Override
	public void onVictory() {
		this.playSound("JeffWeinerPlayerLoses.wav");
	}

}
