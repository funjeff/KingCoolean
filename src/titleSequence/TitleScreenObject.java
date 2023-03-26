package titleSequence;

import com.hackoeur.jglm.Mat4;

import engine.GameObject;
import engine.Sprite;
import gameObjects.FadeTimer;

public class TitleScreenObject extends GameObject {
	
	FadeTimer timer = null;
	String spritePath = null;
	
	public double getFadeTime () {
		if (timer == null) {
			return 0;
		} else {
			return timer.getProgress ();
		}
	}
	
	public void fadeIn (int time) {
		if (timer == null) {
			timer = new FadeTimer (time);
			timer.setInverted (true);
		} else {
			timer.setDuration (time);
			timer.setInverted (true);
			timer.start ();
		}
	}
	
	public void fadeOut (int time) {
		if (timer == null) {
			timer = new FadeTimer (time);
			timer.setInverted (false);
		} else {
			timer.setDuration (time);
			timer.setInverted (false);
			timer.start ();
		}
	}
	
	public void loadSprite (String path) {
		spritePath = path;
	}
	
	@Override
	public void draw () {
		if (spritePath != null) {
			setSprite (new Sprite (spritePath));
			spritePath = null;
		}
		if (this.getSprite () != null) {
			Mat4 finalTransform = getTransform ().multiply (getDisplayTransform ());
			if (getAnimationHandler () != null) {
				getSprite ().draw (finalTransform, 0, this);
			} else {
				getAnimationHandler ().draw (finalTransform);
			}
		}
	}
	

}
