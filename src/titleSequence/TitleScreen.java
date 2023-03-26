package titleSequence;

import java.util.HashMap;

import org.lwjgl.glfw.GLFW;

import com.hackoeur.jglm.Mat4;

import engine.GameLoop;
import engine.GameObject;
import engine.Sprite;
import gameObjects.FadeTimer;
import gl.GLProgram;
import javafx.scene.media.AudioClip;

public class TitleScreen extends GameObject {
	
	Sprite titleImg;
	Sprite muralOverlay;
	boolean overlayHidden = true;
	
	boolean playingSequence;
	
	boolean doScroll = false;
	double scrollX = 0;
	double scrollY = 0;
	double scrollFromX = 0;
	double scrollFromY = 0;
	double scrollDestX = 0;
	double scrollDestY = 0;
	
	Thread timingThread;
	
	FadeTimer titleFadeTimer;
	FadeTimer scrollingTimer;
	
	TitleScreenObject scrollingScreen;
	TitleScreenObject staticScreen;
	TitleScreenObject currentText;
	
	AudioClip music;
	
	public TitleScreen () {
		
		//Load music
		music = new AudioClip ("file:resources/sound/intro_music.mp3");
		
		GameLoop.wind.setResolution (1280, 720);
		titleImg = new Sprite ("resources/sprites/title.png");
		setSprite (titleImg);
		muralOverlay = new Sprite ("resources/sprites/mural_overlay.png");
		scrollingScreen = new TitleScreenObject ();
		scrollingScreen.setSprite (titleImg);
		declare (0, 0);
		
		//Init program
		GLProgram titleProgram = GLProgram.programFromDirectory ("resources/shaders/title/");
		GameLoop.wind.setProgram (titleProgram);
		
	}
	
	public void close () {
		GameLoop.wind.setResolution (960, 540);
	}
	
	@Override
	public void frameEvent () {
		
		if (!playingSequence) {
			if (keyPressed (GLFW.GLFW_KEY_ENTER)) {
				playingSequence = true;
				timingThread = new Thread (new IntroCutscecneController ());
				timingThread.start ();
			}
		} else {
			if (!titleFadeTimer.finished ()) {
				GameLoop.wind.fade = titleFadeTimer.getProgress ();
			}
		}
		
		if (doScroll && scrollingTimer != null) {
			double progress = scrollingTimer.getProgress ();
			double distX = scrollDestX - scrollFromX;
			double distY = scrollDestY - scrollFromY;
			scrollX = scrollFromX + distX * progress;
			scrollY = scrollFromY + distY * progress;
		}
		
	}
	
	@Override
	public void draw () {
		if (overlayHidden) {
			super.draw ();
		}
		Mat4 transform = getTransform ().multiply (getDisplayTransform ());
		if (staticScreen != null) {
			staticScreen.draw ();
		}
		if (scrollingScreen != null) {
			scrollingScreen.draw ();
			if (doScroll) {
				scrollingScreen.setX (scrollX);
				scrollingScreen.setY (scrollY);
			}
		}
		if (!overlayHidden) {
			muralOverlay.draw (transform, 0);
		}
		if (currentText != null) {
			currentText.setX (0);
			currentText.setY (500);
			currentText.draw ();
		}
	}

	public class IntroCutscecneController implements Runnable {

		@Override
		public void run () {
			
			try {
				
				titleFadeTimer = new FadeTimer (2000);
				Thread.sleep (2000);
				
				music.play ();
				overlayHidden = false;
				LegendsText legends = new LegendsText ();
				legends.fadeIn (1000);
				Thread.sleep (7000);
				
				legends.fadeOut (3000);
				Thread.sleep (3000);
				
				titleFadeTimer.setInverted (true);
				titleFadeTimer.setDuration (500);
				titleFadeTimer.start ();
				scrollingTimer = new FadeTimer (22000);
				scrollDestY = -200;
				doScroll = true;
				Thread.sleep (500);
				
				currentText = new TitleScreenObject ();
				currentText.loadSprite ("resources/sprites/mural/text_1.png");
				currentText.fadeIn (500);
				Thread.sleep (7000);
				
				currentText.fadeOut (500);
				Thread.sleep (1000);
				
				currentText.loadSprite ("resources/sprites/mural/text_2.png");
				currentText.fadeIn (500);
				Thread.sleep (7000);
				
				currentText.fadeOut (500);
				Thread.sleep (1000);
				
				scrollingScreen.fadeOut (500);
				//staticScreen = new TitleScreenObject ();
				//staticScreen.loadSprite ("resources/sprites/mural/panel_2.png");
				
				
				
			} catch (InterruptedException e) {
				System.out.println ("oops");
			}
			
		}
		
	}
	
}
