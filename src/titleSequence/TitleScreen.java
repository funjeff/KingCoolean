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
	LegendsText legends;
	
	int order = 0;
	
	AudioClip music;
	
	public TitleScreen () {
		
		//Load music
		music = new AudioClip ("file:resources/sound/intro_music.mp3");
		
		GameLoop.wind.setResolution (1280, 720);
		titleImg = new Sprite ("resources/sprites/title.png");
		setSprite (titleImg);
		muralOverlay = new Sprite ("resources/sprites/mural_overlay.png");
		scrollingScreen = new TitleScreenObject ();
		scrollingScreen.setSprite (new Sprite ("resources/sprites/mural/title.png"));
		declare (0, 0);
		
		//Init program
		GLProgram titleProgram = GLProgram.programFromDirectory ("resources/shaders/title/");
		GameLoop.wind.setProgram (titleProgram);
		
	}
	
	public void close () {
		GameLoop.wind.setResolution (960, 540);
		if (music != null) {
			music.stop ();
		}
		if (legends != null) {
			legends.forget ();
		}
		GLProgram titleProgram = GLProgram.programFromDirectory ("resources/shaders/default/");
		forget ();
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
			if (keyPressed (GLFW.GLFW_KEY_ENTER)) {
				close ();
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
		if (order == 0) {
			if (staticScreen != null) {
				if (staticScreen.visible) {
					staticScreen.draw ();
				}
			}
			if (scrollingScreen != null) {
				if (scrollingScreen.visible) {
					scrollingScreen.draw ();
					if (doScroll) {
						scrollingScreen.setX (scrollX);
						scrollingScreen.setY (scrollY);
					}
				}
			}
		} else {
			if (scrollingScreen != null) {
				if (scrollingScreen.visible) {
					scrollingScreen.draw ();
					if (doScroll) {
						scrollingScreen.setX (scrollX);
						scrollingScreen.setY (scrollY);
					}
				}
			}
			if (staticScreen.visible) {
				if (staticScreen != null) {
					staticScreen.draw ();
				}
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
				legends = new LegendsText ();
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
				staticScreen = new TitleScreenObject ();
				staticScreen.loadSprite ("resources/sprites/mural/panel_2.png");
				staticScreen.setX (200);
				staticScreen.setY (60);
				Thread.sleep (500);
				TitleScreenObject temp = staticScreen;
				staticScreen = scrollingScreen;
				scrollingScreen = temp;
				scrollingTimer.setDuration (24000);
				scrollingTimer.start ();
				scrollFromX = 200;
				scrollDestX = -387;
				scrollFromY = 60;
				scrollDestY = 60;
				staticScreen.hide ();
				
				currentText.loadSprite ("resources/sprites/mural/text_3.png");
				currentText.fadeIn (500);
				Thread.sleep (7000);
				
				currentText.fadeOut (500);
				Thread.sleep (500);
				
				currentText.loadSprite ("resources/sprites/mural/text_4.png");
				currentText.fadeIn (500);
				Thread.sleep (7000);
				
				currentText.fadeOut (500);
				Thread.sleep (3000);
				
				currentText.loadSprite ("resources/sprites/mural/text_5.png");
				currentText.fadeIn (500);
				Thread.sleep (5000);
				
				currentText.fadeOut (500);
				Thread.sleep (2000);
				
				staticScreen.show ();
				scrollingScreen.fadeOut (1000);
				staticScreen.setX (200);
				staticScreen.setY (60);
				staticScreen.fadeIn (1);
				staticScreen.loadSprite ("resources/sprites/mural/panel_3.png");
				Thread.sleep (1000);
				order = 0;
				doScroll = false;
				scrollingScreen.hide ();
				
				currentText.loadSprite ("resources/sprites/mural/text_6.png");
				currentText.fadeIn (500);
				Thread.sleep (6500);
				
				currentText.fadeOut (500);
				Thread.sleep (1000);
				
				currentText.loadSprite ("resources/sprites/mural/text_7.png");
				currentText.fadeIn (500);
				Thread.sleep (7000);
				
				currentText.fadeOut (500);
				Thread.sleep (1000);
				scrollingScreen.show ();
				order = 1;
				scrollX = 200;
				scrollY = 60;
				scrollFromX = 200;
				scrollFromY = 60;
				scrollDestX = -51;
				scrollDestY = 60;
				doScroll = true;
				scrollingTimer.setDuration (17000);
				scrollingTimer.start ();
				scrollingScreen.setX (200);
				scrollingScreen.setY (60);
				staticScreen.fadeOut (500);
				scrollingScreen.loadSprite ("resources/sprites/mural/panel_4.png");
				scrollingScreen.fadeIn (1);
				Thread.sleep (500);
				staticScreen.hide ();
				
				currentText.loadSprite ("resources/sprites/mural/text_8.png");
				currentText.fadeIn (500);
				Thread.sleep (7000);
				
				currentText.fadeOut (500);
				Thread.sleep (1000);
				
				currentText.loadSprite ("resources/sprites/mural/text_9.png");
				currentText.fadeIn (500);
				Thread.sleep (7000);
				
				currentText.fadeOut (500);
				Thread.sleep (1000);
				
				order = 0;
				staticScreen.show ();
				staticScreen.setX (200);
				staticScreen.setY (60);
				scrollingScreen.fadeOut (500);
				staticScreen.loadSprite ("resources/sprites/mural/panel_5.png");
				staticScreen.fadeIn (1);
				Thread.sleep (500);
				scrollingScreen.hide ();
				
				currentText.loadSprite ("resources/sprites/mural/text_10.png");
				currentText.fadeIn (500);
				Thread.sleep (7000);
				
				currentText.fadeOut (500);
				Thread.sleep (1000);
				
				currentText.loadSprite ("resources/sprites/mural/text_11.png");
				currentText.fadeIn (500);
				Thread.sleep (7000);
				
				currentText.fadeOut (500);
				Thread.sleep (1000);
				
				currentText.loadSprite ("resources/sprites/mural/text_12.png");
				currentText.fadeIn (500);
				Thread.sleep (16000);
				
				currentText.fadeOut (500);
				Thread.sleep (1000);
				
				currentText.loadSprite ("resources/sprites/mural/text_13.png");
				currentText.fadeIn (500);
				Thread.sleep (14000);
				
				currentText.fadeOut (500);
				Thread.sleep (1000);
				
				currentText.loadSprite ("resources/sprites/mural/text_14.png");
				currentText.fadeIn (500);
				Thread.sleep (14000);
				
				currentText.fadeOut (500);
				Thread.sleep (1000);
				
				currentText.loadSprite ("resources/sprites/mural/text_15.png");
				currentText.fadeIn (500);
				Thread.sleep (10000);
				
				currentText.fadeOut (2000);
				Thread.sleep (3000);
				
				currentText = null;
				titleFadeTimer.setInverted (false);
				titleFadeTimer.setDuration (2000);
				titleFadeTimer.start ();
				Thread.sleep (3000);
				music.stop ();
				
			} catch (InterruptedException e) {
				System.out.println ("oops");
			}
			
		}
		
	}
	
}
