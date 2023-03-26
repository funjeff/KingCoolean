package gameObjects;

import engine.GameCode;

public class ImagamerConnect extends Connect{
	@Override
	public void onSelect () {
		GameCode.map.forget();
		ConnectFourGame g = new ConnectFourGame();
		g.setEnemy(new Imagamer (this));
		g.declare();
	}
}
