package gameObjects;

import engine.GameCode;
import engine.Sprite;

public class WeffJiener extends Enemy{
	public WeffJiener (Connect c) {
		super (c);
		this.pieceType = 6;
		this.difficulty = 7;
		this.setSprite(new Sprite ("resources/sprites/Weff Jeiner.png"));
		this.background = new Sprite ("resources/sprites/SPACE.png");
	}
	
	@Override
	public void onDefeat () {
		GameCode.map.forget();
		ConnectFourGame.unlockedMoves[0] = true;
		
	}
	
}
