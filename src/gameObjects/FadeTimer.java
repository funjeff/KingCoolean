package gameObjects;

public class FadeTimer {
	
	long target;
	int duration;
	
	boolean inverted;
	
	public FadeTimer (int durationMs) {
		setDuration (durationMs);
		start ();
	}
	
	public void setDuration (int durationMs) {
		duration = durationMs;
	}
	
	public void start () {
		target = System.currentTimeMillis () + duration;
	}
	
	public void setInverted (boolean inverted) { 
		this.inverted = inverted;
	}
	
	public double getProgress () {
		long elapsed = target - System.currentTimeMillis ();
		double progress = (double)elapsed / duration;
		return Math.min (Math.max (inverted ? progress : 1.0 - progress, 0), 1.0);
	}
	
	public boolean finished () {
		long elapsed = System.currentTimeMillis () - target;
		if (elapsed < 0) {
			return false;
		}
		return true;
	}
	
}