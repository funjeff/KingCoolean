package gameObjects;

import engine.GameCode;

public class LarryConnect extends Connect {
	public LarryConnect () {
		
	}
	
	@Override
	public void onSelect () {
		GameCode.map.forget();
		ConnectFourGame g = new ConnectFourGame();
		g.setEnemy(new LeftLarry ());
		g.declare();
	}
}