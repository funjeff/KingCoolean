package gameObjects;

import engine.GameCode;

public class CharlieConnect extends Connect {
	public CharlieConnect () {
		
	}
	
	@Override
	public void onSelect () {
		GameCode.map.forget();
		ConnectFourGame g = new ConnectFourGame();
		g.setEnemy(new CheatingCharlie (this));
		g.declare();
	}
}