package gameObjects;

import engine.GameCode;

public class DarkCooleanConnect extends Connect {
	
public DarkCooleanConnect () {
		
	}
	
	@Override
	public void onSelect () {
		GameCode.map.forget();
		ConnectFourGame g = new ConnectFourGame();
		g.setEnemy(new DarkCoolean (this));
		g.declare();
	}

}
