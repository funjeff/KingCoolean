package gameObjects;

import engine.GameCode;

public class DerekConnect extends Connect {
	
public DerekConnect () {
		
	}
	
	@Override
	public void onSelect () {
		GameCode.map.forget();
		ConnectFourGame g = new ConnectFourGame();
		g.setEnemy(new DeliriousDerek (this));
		g.declare();
	}

}
