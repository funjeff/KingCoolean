package gameObjects;

import java.util.Random;

import engine.GameCode;
import engine.Sprite;
import javafx.scene.media.AudioClip;

public class HorizontalHenry extends Enemy {
	
	int move = 0;
	
	boolean dir = true;
	
	AudioClip clip = new AudioClip ("file:resources/music/idk2.wav");
	public HorizontalHenry (Connect connect) {
		super(connect);
		this.setSprite(new Sprite ("resources/sprites/config/horizontalHenry.txt"));
		this.getAnimationHandler().setFlipHorizontal(true);
		pieceType = 1;
		clip.setCycleCount (100);
		clip.play ();
	}
	
	public int getMove (int [] [] boardState) {
		
		while (boardState[move][0] != 0) {
			if (dir) {
				move = move + 1;
				if (move == 6) {
					dir = false;
				}
			} else {
				move = move - 1;
				if (move == 0) {
					dir = true;
				}
			}
		}
		
		int oldMove = move;
		
		if (dir) {
			move = move + 1;
			if (move == 6) {
				dir = false;
			}
		} else {
			move = move - 1;
			if (move == 0) {
				dir = true;
			}
		}
		
		return oldMove;
	}
	
	@Override
	public void onDefeat() {
		this.mapConnect.setAbove(mapConnect.getUpConnectByPosition());
		this.mapConnect.getAbove().setBelow(this.mapConnect);
		GameCode.map.declare();
		clip.stop ();
	}
	@Override
	public void onVictory () {
		GameCode.map.declare();
	}
	
	
}
