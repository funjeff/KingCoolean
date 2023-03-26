package gameObjects;

import java.util.Random;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import engine.GameCode;
import engine.Sprite;

public class LeftLarry extends Enemy {
	
	public LeftLarry (Connect connect) {
		super(connect);
		this.setSprite(new Sprite ("resources/sprites/config/leftLarry.txt"));
		this.getAnimationHandler().setFlipHorizontal(true);
		pieceType = 2;
		Random rand = new Random();
		if (rand.nextInt(2) == 1) this.playSound("LLStartDialog.wav");
		else this.playSound("LLStartDialog2.wav");
	}
	
	public int getMove (int [] [] boardState) {
		return 0;
	}

	@Override
	public void onDefeat() {
		this.mapConnect.setRight(mapConnect.getRightConnectByPosition());
		this.stopAllSounds();
		this.playSound("leftLarryPlayerWins.wav");
		this.mapConnect.getRight().setLeft(this.mapConnect);
		GameCode.map.declare();
		ConnectFourGame.unlockedMoves[2] = true;
	}
	
	public void onVictory() {
		this.playSound("leftLarryPlayerLoses.wav");
	}
	
}
