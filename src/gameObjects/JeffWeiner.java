package gameObjects;

import engine.GameCode;
import engine.Sprite;
import engine.AudioClip;

public class JeffWeiner extends Enemy{
	AudioClip clip = new AudioClip ("file:resources/music/idk2.wav");
	public JeffWeiner (Connect c) {
		super (c);
		this.pieceType = 9;
		this.difficulty = 9;
		this.playSound("JeffWeinerIntro.wav");
		this.setSprite(new Sprite ("resources/sprites/jeff weiner.png"));
		this.background = new Sprite ("resources/sprites/SPACE.png");
		this.playSound("JeffWeinerIntro.wav");

		this.frameOrNah = false;

		clip.loop ();

	}
	
	@Override
	public void onDefeat () {
		ConnectFourGame g = new ConnectFourGame();
		g.setEnemy(new WeffJiener(new Connect ()));
		g.declare();

		clip.stop ();

	}
	
	@Override
	public void onVictory() {
		clip.stop();
		GameCode.map.declare();
	}
	
	@Override
	public void onDefeatLine () {
		this.playSound("JeffWeinerPlayerWins1.wav");
	}

	@Override
	public void onVictoryLine () {
		this.playSound("JeffWeinerPlayerLoses.wav");
	}
	
}
