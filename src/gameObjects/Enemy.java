package gameObjects;

import javafx.scene.media.AudioClip;

import java.util.ArrayList;

import engine.GameObject;
import engine.SerbianConnectFour;
import engine.Sprite;
import engine.State;

public class Enemy extends GameObject {
	ArrayList<AudioClip> audio = new ArrayList<AudioClip>();
	Connect mapConnect = new Connect ();
	
	int pieceType;
	int difficulty = 7;
	
	boolean frameOrNah = true;
	
	Frame f = new Frame ();
	Sprite background = new Sprite ("resources/sprites/Background1.png");
	
	public Enemy (Connect map) {
		mapConnect = map;
	}
	@Override
	public void draw() {
		if (frameOrNah) {
			f.draw();
		}
		super.draw();
	}
	
	@Override
	public void frameEvent () {
		if (!audio.isEmpty()) {
			for (int i = 0; i < audio.size(); i++) {
			
				if (audio.get(i).isPlaying()) {
					this.getAnimationHandler().setFrameTime(100);
				} else {
					audio.remove(i);
					i = i - 1;
				}
			}
		} else {
			if (this.getAnimationHandler().getFrameTime() != 0) {
				this.getAnimationHandler().setFrameTime(0);
			}
		}
	}
	
	public int getMove (int [] [] boardState) {
		SerbianConnectFour scf = new SerbianConnectFour();
		AlphaBetaSearch abs = new AlphaBetaSearch(scf);
		return abs.alphaBetaSearch(boardState, difficulty);
		//TODO get move from ai (override)
	}
	
	public void playSound(String sound) {
		
		while (!audio.isEmpty()) {
			if (audio.get(0).isPlaying()) {
				return;
			} else {
				audio.remove(0);
			}
		}
		
		AudioClip clip = new AudioClip("file:resources/sound/" + sound);
		audio.add(clip);
		clip.play();
	}
	

	public void playSound(String sound, boolean doItBRO) {
		
		while (!audio.isEmpty()) {
			audio.get(0).stop();
			audio.remove(0);
		}
		
		AudioClip clip = new AudioClip("file:resources/sound/" + sound);
		audio.add(clip);
		clip.play();
	}
	
	
	public void stopAllSounds() {
		for (int i = 0; i < audio.size(); i++) {
			audio.get(i).stop();
		}
	}
	
	public void onDefeat () {
		
	}
	
	public void onVictory () {
		
	}
	
	public void onDefeatLine () {
		
	}
	
	public void onVictoryLine () {
		
	}
	
	public void setPieceType (int type) {
		pieceType = type;
	}

	public class Frame extends GameObject {
		public Frame() {
			Sprite s = new Sprite("resources/sprites/config/frame.txt");
			this.setSprite(s);
			this.setX(765);
			this.setY(30);
		}

	}
	
}
