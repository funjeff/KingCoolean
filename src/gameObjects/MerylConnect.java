package gameObjects;

import engine.GameCode;

public class MerylConnect extends Connect {
	public MerylConnect () {
		
	}
	
	@Override
	public void onSelect () {
		GameCode.map.forget();
		ConnectFourGame g = new ConnectFourGame();
		g.setEnemy(new MirroredMeryl ());
		g.declare();
	}
}