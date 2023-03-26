package gameObjects;

import engine.GameCode;

public class RandyConnect extends Connect {
	public RandyConnect () {
		
	}
	
	@Override
	public void onSelect () {
		GameCode.map.forget();
		ConnectFourGame g = new ConnectFourGame();
		g.setEnemy(new RandomRandy (this));
		g.declare();
	}
}