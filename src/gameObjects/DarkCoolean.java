package gameObjects;

import engine.GameCode;
import engine.Sprite;
import javafx.scene.media.AudioClip;

public class DarkCoolean extends Enemy{
	
	AudioClip clip = new AudioClip ("file:resources/music/idk2.wav");
	
	public DarkCoolean (Connect c) {
		super (c);
		this.pieceType = 6;
		this.difficulty = 7;
		this.playSound("darkCoolean.wav");
		this.setSprite(new Sprite ("resources/sprites/evilcoolean.png"));
		this.getAnimationHandler().setFlipHorizontal(true);
		this.background = new Sprite ("resources/sprites/Volcano.png");
		clip.setCycleCount (100);
		clip.play ();
	}
	
	@Override
	public void onDefeat () {
		clip.stop ();
		GameCode.defeatedDarkCoolean = true;
		if (GameCode.defeatedJerry && GameCode.defeatedImagamer && GameCode.defeatedDarkCoolean) {
			ConnectFourGame g = new ConnectFourGame();
			g.setEnemy(new JeffWeiner(new Connect ()));
			g.declare();
		} else {
			GameCode.map.declare();
		}
	}

	@Override
	public void onVictory () {
		GameCode.map.declare();
	}
	
}
