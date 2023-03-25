package gameObjects;

import engine.GameObject;
import engine.SerbianConnectFour;
import engine.Sprite;
import engine.State;

public class Enemy extends GameObject {
	int difficulty;
	public Enemy () {
		difficulty = 4;
	}
	
	public int getMove (int [] [] boardState) {
		SerbianConnectFour scf = new SerbianConnectFour();
		AlphaBetaSearch abs = new AlphaBetaSearch(scf);
		return abs.alphaBetaSearch(boardState, difficulty);
		//TODO get move from ai (override)
	}
	
}
