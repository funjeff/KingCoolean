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
	
	Frame f = new Frame ();
	
	public Enemy (Connect map) {
		mapConnect = map;
	}
	@Override
	public void draw() {
		f.draw();
		super.draw();
	}
	
	public int getMove (int [] [] boardState) {
		SerbianConnectFour scf = new SerbianConnectFour();
		AlphaBetaSearch abs = new AlphaBetaSearch(scf);
		return abs.alphaBetaSearch(boardState, difficulty);
		//TODO get move from ai (override)
	}
	
	public void playSound(String sound) {
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
