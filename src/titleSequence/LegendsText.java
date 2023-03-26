package titleSequence;

import engine.Sprite;
import gameObjects.FadeTimer;

public class LegendsText extends TitleScreenObject implements IgnoresFade {

	FadeTimer timer;
	
	public LegendsText () {
		declare (138, 259);
	}
	
	@Override
	public void frameEvent () {
		if (getSprite () == null) {
			setSprite (new Sprite ("resources/sprites/legends.png"));
		}
	}
	
}
