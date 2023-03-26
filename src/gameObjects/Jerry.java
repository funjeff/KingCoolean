package gameObjects;

import engine.GameCode;
import engine.Sprite;

public class Jerry extends Enemy{
	
	public Jerry (Connect c) {
		super (c);
		this.pieceType = 10;
		this.difficulty = 7;
		this.setSprite(new Sprite ("resources/sprites/Jerry the Jragon.png"));
		this.background = new Sprite ("resources/sprites/Office.png");
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
	}
	
	@Override
	public void onVictory () {
		GameCode.map.declare();
	}

}
