package gameObjects;

import engine.GameObject;
import engine.Sprite;
import engine.State;

public class Enemy extends GameObject {

	public Enemy () {
	
	}
	
	public int getMove (State boardState) {
		//TODO get move from ai (override)
		return 0;
	}
	
}
