package gameObjects;

import engine.GameCode;

public class HenryConnect extends Connect {
	
public HenryConnect () {
		
	}
	
	@Override
	public void onSelect () {
		GameCode.map.forget();
		ConnectFourGame g = new ConnectFourGame();
		g.setEnemy(new HorizontalHenry (this));
		g.declare();
	}

}
