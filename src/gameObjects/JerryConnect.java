package gameObjects;

import engine.GameCode;

public class JerryConnect extends Connect{
	@Override
	public void onSelect () {
		GameCode.map.forget();
		ConnectFourGame g = new ConnectFourGame();
		g.setEnemy(new Jerry (this));
		g.declare();
	}
}
