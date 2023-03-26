package engine;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * The loop for rendering the game to the GameWindow. Runs on the event dispatching thread.
 * @author nathan
 *
 */
public class GameLoop {
	
	/**
	 * The maximum framerate the game can run at
	 */
	public static final double maxFramerate = 60;
	/**
	 * The time of the last update to the GameWindow, in nanoseconds.
	 */
	static private long lastUpdate;
	
	/**
	 * The system time when this frame's rendering began
	 */
	static private long frameTime;
	
	/**
	 * The image of the input from the past GameLogic frame
	 */
	static private InputManager inputImage;
	
	public static final GameWindow wind = new GameWindow (960, 540);
	
	public static void main (String[] args) {
		GameCode.init ();
		//Sets the initial frame time
		frameTime = System.currentTimeMillis ();
		//Initializes lastUpdate to the current time
		lastUpdate = System.nanoTime ();
		while (true) {
			//Get the target time in nanoseconds for this iteration; should be constant if the framerate doesn't change
			long targetNanoseconds = (long)(1000000000 / maxFramerate);
			//Get the time before refreshing the window
			long startTime = System.nanoTime ();
			frameTime = System.currentTimeMillis ();
			//Render the window
			inputImage = GameLoop.wind.getInputImage ();
			GameCode.beforeGameLogic ();
			GameCode.gameLoopFunc();
			ObjectHandler.callAll ();
			GameLoop.wind.resetInputBuffers ();
			GameCode.afterGameLogic ();
			GameCode.beforeRender ();
			GameCode.renderFunc();
			ObjectHandler.renderAll ();
			GameCode.afterRender ();
			wind.refresh ();
			//Calculate elapsed time and time to sleep for
			lastUpdate = System.nanoTime ();
			long elapsedTime = lastUpdate - startTime;
			int sleepTime = (int)((targetNanoseconds - elapsedTime) / 1000000) - 1;
			
			if (sleepTime < 0) {
				sleepTime = 0;
			}
			//Sleep until ~1ms before it's time to redraw the frame (to account for inaccuracies in Thread.sleep)
			try {
				Thread.currentThread ().sleep (sleepTime);
			} catch (InterruptedException e) {
				//Do nothing; the while loop immediately after handles this case well
			}
			//Wait until the frame should be redrawn
			while (System.nanoTime () - startTime < targetNanoseconds) {
			
			}
		}
	}
	
	/**
	 * Gets the value returned by System.currentTimeMillis() at the start of the frame being rendered.
	 * @return The start time of the current frame
	 */
	public static long frameStartTime () {
		return frameTime;
	}
	
	/**
	 * Gets the input image from the start of this game logic iteration.
	 * @return The input image from the start of this iteration
	 */
	public static InputManager getInputImage () {
		return inputImage;
	}
	
	/**
	 * Event callback for closing the game
	 */
	public static void end () {
		System.exit (0);
	}
	
}
