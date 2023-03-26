package gameObjects;

import java.util.Random;

import engine.GameCode;
import engine.Sprite;

public class RandomRandy extends Enemy {
	
	public RandomRandy (Connect connect) {
		super(connect);
		this.setSprite(new Sprite ("resources/sprites/config/randomRandy.txt"));
		this.getAnimationHandler().setFlipHorizontal(true);
		this.pieceType = 4;
	}
	
	public int getMove (int [] [] boardState) {
		Random rand = new Random();
		int curr = rand.nextInt(7);
		while (boardState[curr][0] != 0) {
			curr = rand.nextInt(7);
		}
		return curr;
	}
	
	@Override
	public void onDefeat () {
		this.mapConnect.setBelow(mapConnect.getDownConnectByPosition());
		this.mapConnect.getBelow().setAbove(this.mapConnect);
		ConnectFourGame.unlockedMoves[3] = true;
		GameCode.map.declare();
	}
}
