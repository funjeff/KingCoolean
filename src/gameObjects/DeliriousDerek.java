package gameObjects;

import java.util.ArrayList;
import java.util.Random;

import engine.GameCode;
import engine.Sprite;

public class DeliriousDerek extends Enemy {
	public DeliriousDerek (Connect connect) {
		
		super(connect);
		this.setSprite(new Sprite ("resources/sprites/config/deliriousDerek.txt"));
		this.getAnimationHandler().setFlipHorizontal(true);
		pieceType = 5;
	}
	
	
	@Override
	public void onDefeat() {
		this.mapConnect.setLeft(mapConnect.getLeftConnectByPosition());
		this.mapConnect.getLeft().setRight(this.mapConnect);
		GameCode.map.declare();
	}
}
