package gameObjects;

import engine.GameCode;
import engine.Sprite;

public class JeffWeiner extends Enemy{
	
	public JeffWeiner (Connect c) {
		super (c);
		this.pieceType = 9;
		this.difficulty = 9;
		this.playSound("JeffWeinerIntro.wav");
		this.setSprite(new Sprite ("resources/sprites/jeff weiner.png"));
		this.background = new Sprite ("resources/sprites/SPACE.png");
		this.playSound("JeffWeinerIntro.wav");
		this.frameOrNah = false;
	}
	
	@Override
	public void onDefeat () {
		ConnectFourGame g = new ConnectFourGame();
		g.setEnemy(new WeffJiener(new Connect ()));
		g.declare();
		
	}
	
	@Override
	public void onVictory() {
		
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
