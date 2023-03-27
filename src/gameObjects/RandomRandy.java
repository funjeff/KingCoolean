package gameObjects;

import java.util.Random;

import engine.GameCode;
import engine.Sprite;
import javafx.scene.media.AudioClip;

public class RandomRandy extends Enemy {
	
	AudioClip clip = new AudioClip ("file:resources/music/idk2.wav");
	
	public RandomRandy (Connect connect) {
		super(connect);
		this.setSprite(new Sprite ("resources/sprites/config/randomRandy.txt"));
		this.getAnimationHandler().setFlipHorizontal(true);
		this.pieceType = 4;
		clip.setCycleCount (100);
		clip.play ();
	}
	
	public int getMove (int [] [] boardState) {
		Random rand = new Random();
		int curr = rand.nextInt(7);
		while (boardState[curr][0] != 0) {
			curr = rand.nextInt(7);
		}
		return curr;
	}
	
	@Override
	public void onDefeat () {
		
		this.mapConnect.setBelow(mapConnect.getDownConnectByPosition());
		this.mapConnect.getBelow().setAbove(this.mapConnect);
		ConnectFourGame.unlockedMoves[3] = true;
		GameCode.map.declare();
		clip.stop ();
	}
	
	@Override
	public void onVictory () {
		clip.stop();
		GameCode.map.declare();
	}
	
	@Override
	public void onDefeatLine () {
		this.playSound("RRPlayerWins.wav",true);
	}
	
	
	
}
