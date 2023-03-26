package gameObjects;

import engine.GameCode;
import engine.Sprite;

public class Imagamer extends Enemy{
	
	public Imagamer (Connect c) {
		super (c);
		this.playSound("ImagamerIntro.wav");
		this.pieceType = 6;
		this.difficulty = 7;
		this.setSprite(new Sprite ("resources/sprites/config/imagamer.txt"));
	}
	
	@Override
	public void onDefeat () {
		GameCode.defeatedImagamer = true;
		GameCode.map.declare();
	}

}
