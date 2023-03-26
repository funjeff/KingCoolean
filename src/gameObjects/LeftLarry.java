package gameObjects;

import engine.GameCode;
import engine.Sprite;

public class LeftLarry extends Enemy {
	
	public LeftLarry (Connect connect) {
		super(connect);
		this.setSprite(new Sprite ("resources/sprites/config/leftLarry.txt"));
		this.getAnimationHandler().setFlipHorizontal(true);
		pieceType = 2;
	}
	
	public int getMove (int [] [] boardState) {
		return 0;
	}

	@Override
	public void onDefeat() {
		this.mapConnect.setRight(mapConnect.getRightConnectByPosition());
		this.mapConnect.getRight().setLeft(this.mapConnect);
		GameCode.map.declare();
		ConnectFourGame.unlockedMoves[2] = true;
	}
	
}
