package gameObjects;

import engine.GameCode;
import engine.Sprite;

public class DarkCoolean extends Enemy{
	
	public DarkCoolean (Connect c) {
		super (c);
		this.pieceType = 6;
		this.difficulty = 7;
		this.playSound("darkCoolean.wav");
		this.setSprite(new Sprite ("resources/sprites/evilcoolean.png"));
		this.getAnimationHandler().setFlipHorizontal(true);
	}
	
	@Override
	public void onDefeat () {
		GameCode.defeatedDarkCoolean = true;
		GameCode.map.declare();
	}

}
