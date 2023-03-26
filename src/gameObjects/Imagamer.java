package gameObjects;

import engine.GameCode;
import engine.Sprite;

public class Imagamer extends Enemy{
	
	public Imagamer (Connect c) {
		super (c);

		this.pieceType = 8;

		this.playSound("ImagamerIntro.wav");

		this.difficulty = 7;
		this.setSprite(new Sprite ("resources/sprites/config/imagamer.txt"));
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
	}
	
	@Override
	public void onVictory () {
		GameCode.map.declare();
	}

}
