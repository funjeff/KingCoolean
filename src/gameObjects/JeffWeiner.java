package gameObjects;

import engine.GameCode;
import engine.Sprite;

public class JeffWeiner extends Enemy{
	
	public JeffWeiner (Connect c) {
		super (c);
		this.pieceType = 9;
		this.difficulty = 9;
		this.setSprite(new Sprite ("resources/sprites/jeff weiner.png"));
		this.background = new Sprite ("resources/sprites/SPACE.png");
	}
	
	@Override
	public void onDefeat () {
		ConnectFourGame g = new ConnectFourGame();
		g.setEnemy(new WeffJiener(new Connect ()));
		g.declare();
	}

}
