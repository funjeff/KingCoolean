package gameObjects;

import engine.GameObject;
import engine.Sprite;
import engine.State;

public class Enemy extends GameObject {

	Connect mapConnect = new Connect ();
	
	int pieceType;
	
	public Enemy (Connect map) {
		mapConnect = map;
	}
	
	public int getMove (int [] [] boardState) {
		//TODO get move from ai (override)
		return 0;
	}
	
	public void onDefeat () {
		
	}
	
	public void setPieceType (int type) {
		pieceType = type;
	}
	
}
