package gameObjects;

import java.util.Random;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import engine.GameCode;
import engine.GameObject;
import engine.Sprite;
import engine.AudioClip;

public class LeftLarry extends Enemy {
	
	AudioClip clip = new AudioClip ("file:resources/music/idk2.wav");
	public LeftLarry (Connect connect) {
		super(connect);
		this.setSprite(new Sprite ("resources/sprites/config/leftLarry.txt"));
		this.getAnimationHandler().setFrameTime(0);
		this.getAnimationHandler().setFlipHorizontal(true);
		pieceType = 2;
		Random rand = new Random();
		if (rand.nextInt(2) == 1) this.playSound("LLStartDialog.wav");
		else this.playSound("LLStartDialog2.wav");
		clip.loop ();
	}
	
	public int getMove (int [] [] boardState) {
		return 0;
	}

	@Override
	public void onDefeat() {
		this.mapConnect.setRight(mapConnect.getRightConnectByPosition());
		this.stopAllSounds();
		
		this.mapConnect.getRight().setLeft(this.mapConnect);
		GameCode.map.declare();
		ConnectFourGame.unlockedMoves[2] = true;
		clip.stop ();
	}
	
	public void onVictory() {
		clip.stop();
		GameCode.map.declare();
	}
	
	@Override
	public void onDefeatLine () {
		this.playSound("llPlayerWins.wav");
	}

	@Override
	public void onVictoryLine () {
		this.playSound("LLPlayerLoses.wav");
	}

}
