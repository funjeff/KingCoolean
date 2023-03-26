package gameObjects;

import engine.GameObject;
import engine.SerbianConnectFour;
import engine.Sprite;
import engine.State;

public class Enemy extends GameObject {

	Connect mapConnect = new Connect ();
	
	int pieceType;
	int difficulty;
	
	public Enemy (Connect map) {
		mapConnect = map;
		
		difficulty = 7;
	}
	
	public int getMove (int [] [] boardState) {
		SerbianConnectFour scf = new SerbianConnectFour();
		AlphaBetaSearch abs = new AlphaBetaSearch(scf);
		return abs.alphaBetaSearch(boardState, difficulty);
		//TODO get move from ai (override)
	}
	
	public void onDefeat () {
		
	}
	
	public void setPieceType (int type) {
		pieceType = type;
	}
	
}
