package gameObjects;

import engine.GameCode;
import engine.Sprite;

public class Jerry extends Enemy{
	
	public Jerry (Connect c) {
		super (c);
		this.pieceType = 6;
		this.difficulty = 7;
		this.setSprite(new Sprite ("resources/sprites/Jerry the Jragon.png"));
	}
	
	@Override
	public void onDefeat () {
		GameCode.defeatedJerry = true;
		GameCode.map.declare();
	}

}
