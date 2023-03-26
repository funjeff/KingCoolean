package engine;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.imageio.ImageIO;

import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;

import com.hackoeur.jglm.Mat4;
import com.hackoeur.jglm.Matrices;
import com.hackoeur.jglm.Vec4;

import java.nio.*;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL40.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

import gl.*;
import titleSequence.IgnoresFade;
import titleSequence.TitleScreenObject;

/**
 * The window which the game is displayed in. Also handles keyboard and mouse events.
 * @author nathan
 *
 */
public class GameWindow {

	int width, height;
	int[] resolution = new int[2];
	
	/**
	 * The image used as a drawing buffer
	 */
	private BufferedImage buffer;
	/**
	 * another image that is not scalled by resolution
	 */
	private BufferedImage nonScallableBuffer;
	
	
	private ArrayList <BufferedImage> inGameBufferes = new ArrayList <BufferedImage>();
	/**
	 * The InputManager used to detect input for this window
	 */
	private InputManager inputManager;
	
	// The window handle
	private long window;
	
	//Draw calls
	private ArrayList<DrawCall> drawCalls = new ArrayList<DrawCall> ();
	
	//GL state things
	private GLProgram program;
	private ObjectVBOS vbos;
	
	public double fade = 0.0;
	
	/**
	 * Constructs a new GameWindow with the given width and height.
	 * @param width The initial width, in pixels, of the window content
	 * @param height The initial height, in pixels, of the window content
	 */
	public GameWindow (int width, int height) {
		
		this.width = width;
		this.height = height;
		this.resolution = new int[] {width, height};
		
		// Setup an error callback. The default implementation
		// will print the error message in System.err.
		GLFWErrorCallback.createPrint(System.err).set();

		// Initialize GLFW. Most GLFW functions will not work before doing this.
		if ( !glfwInit() )
			throw new IllegalStateException("Unable to initialize GLFW");

		// Configure GLFW
		glfwDefaultWindowHints(); // optional, the current window hints are already the default
		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation
		glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // the window will be resizable

		// Create the window
		window = glfwCreateWindow(width, height, "Hello World!", NULL, NULL);
		if ( window == NULL )
			throw new RuntimeException ("Failed to create the GLFW window");
		
		// Setup a key callback. It will be called every time a key is pressed, repeated or released.
		inputManager = new InputManager ();
		glfwSetKeyCallback(window, inputManager);
		glfwSetWindowSizeCallback (window, new WinResizeCallback ());

		// Get the thread stack and push a new frame
		try ( MemoryStack stack = stackPush() ) {
			IntBuffer pWidth = stack.mallocInt(1); // int*
			IntBuffer pHeight = stack.mallocInt(1); // int*

			// Get the window size passed to glfwCreateWindow
			glfwGetWindowSize(window, pWidth, pHeight);

			// Get the resolution of the primary monitor
			GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

			// Center the window
			glfwSetWindowPos(
				window,
				(vidmode.width() - pWidth.get(0)) / 2,
				(vidmode.height() - pHeight.get(0)) / 2
			);
		} // the stack frame is popped automatically

		// Make the OpenGL context current
		glfwMakeContextCurrent(window);
		// Enable v-sync
		glfwSwapInterval(1);

		// Make the window visible
		glfwShowWindow(window);
		
		GL.createCapabilities();
		
		//OpenGL init
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		glEnable (GL_BLEND);
		//glEnable (GL_DEPTH_TEST);
		
		program = GLProgram.programFromDirectory ("resources/shaders/default/");
		vbos = new ObjectVBOS ();
		
	}

	public void setProgram (GLProgram program) {
		this.program = program;
	}
	
	public void closeWindow () {
		
		// Free the window callbacks and destroy the window
		glfwFreeCallbacks(window);
		glfwDestroyWindow(window);

		// Terminate GLFW and free the error callback
		glfwTerminate();
		glfwSetErrorCallback(null).free();
		
	}
	
	/**
	 * Renders the contents of the buffers onto the window.
	 */
	public void refresh () {

		// Set the clear color
		glClearColor(0.75f, 0.75f, 0.75f, 1.0f);
		
		// Run the rendering loop until the user has attempted to close
		// the window or has pressed the ESCAPE key.
		if (glfwWindowShouldClose(window)) {
			this.closeWindow ();
			GameLoop.end ();
		} else {
			
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer
			
			//Draw to the screen
			program.use ();
			vbos.bindSpriteVBO ();
			glVertexAttribPointer (
					0,
					3,
					GL_DOUBLE,
					false,
					8 * 5,
					0);
			glEnableVertexAttribArray (0);
			glVertexAttribPointer (
					1,
					2,
					GL_DOUBLE,
					false,
					8 * 5,
					8 * 3);
			glEnableVertexAttribArray (1);
			
			//Get the uniforms
			int transformLoc = glGetUniformLocation (program.getProgramName (), "transform");
			int vpLoc = glGetUniformLocation (program.getProgramName (), "vp");
			int samplerLoc = glGetUniformLocation (program.getProgramName (), "texture");
			int fadeTimerGlobalLoc = glGetUniformLocation (program.getProgramName (), "fade_timer_global");
			int fadeTimerLocalLoc = glGetUniformLocation (program.getProgramName (), "fade_timer_local");
			GLTexture currentTexture = null;
			glUniform1i(samplerLoc, 0);
			float[] transformBuffer = new float[16];
			float[] vpBuffer = new float[16];
			
			Mat4 vp = Matrices.ortho (0f, (float)resolution[0], (float)resolution[1], 0, -5000, 5000);
			//System.out.println (vp);

			for (int i = 0; i < drawCalls.size (); i++) {
				
				DrawCall working = drawCalls.get (i);
				if (working.getCallingObject () != null && working.getCallingObject () instanceof IgnoresFade) {
					glUniform1f(fadeTimerGlobalLoc, (float)0);
				} else {
					glUniform1f(fadeTimerGlobalLoc, (float)fade);
				}
				if (working.getCallingObject () != null && working.getCallingObject () instanceof TitleScreenObject) {
					glUniform1f (fadeTimerLocalLoc, (float)((TitleScreenObject)working.getCallingObject ()).getFadeTime ());
				} else {
					glUniform1f (fadeTimerLocalLoc, (float)0);
				}
				
				Mat4 mvp = vp.multiply (working.getTransform ());
				if (currentTexture != working.getTexture ()) {
					currentTexture = working.getTexture ();
					currentTexture.bindTexture ();
					glActiveTexture (GL_TEXTURE0);
				}
				for (int wx = 0; wx < 4; wx++) {
					Vec4 column = mvp.getColumn (wx);
					vpBuffer[wx * 4] = column.getX ();
					vpBuffer[wx * 4 + 1] = column.getY ();
					vpBuffer[wx * 4 + 2] = column.getZ ();
					vpBuffer[wx * 4 + 3] = column.getW ();
				}
				glUniformMatrix4fv (transformLoc, false, transformBuffer);
				glUniformMatrix4fv (vpLoc, false, vpBuffer);
				glDrawArrays (
						GL_TRIANGLE_STRIP,
						0,
						4);
			}
			
			glDisableVertexAttribArray (0);
			glDisableVertexAttribArray (1);

			glfwSwapBuffers(window); // swap the color buffers

			// Poll for window events. The key callback above will only be
			// invoked during this call.
			glfwPollEvents();
			
			//Reset the draw call buffer
			drawCalls = new ArrayList<DrawCall> ();
			
		}
		
	}
	
	/**
	 * Gets the dimensions of the buffer, e.g. the resolution of the output.
	 * @return The dimensions of this GameWindow's buffer as an int array, in the format [width, height]
	 */
	public int[] getResolution () {
		return new int[] {resolution[0], resolution[1]};
	}
	
	/**
	 * Sets the resolution of the buffer to the given width and height; erases its contents.
	 * @param width The width to use, in pixels
	 * @param height The height to use, in pixels
	 */
	public void setResolution (int width, int height) {
		resolution[0] = width;
		resolution[1] = height;
	}
	
	public InputManager getInputImage () {
		return inputManager.createImage ();
	}

	public void resetInputBuffers () {
		inputManager.resetKeyBuffers ();
		inputManager.resetMouseBuffers ();
	}
	
	public void drawSprite (Mat4 matrix, GLTexture tex, GameObject obj) {
		drawCalls.add (new DrawCall (matrix, tex, obj));
	}
	
	public void drawSprite (Mat4 matrix, GLTexture tex) {
		drawCalls.add (new DrawCall (matrix, tex, null));
	}
	
	public class WinResizeCallback implements GLFWWindowSizeCallbackI {

		@Override
		public void invoke (long window, int wwidth, int wheight) {
			width = wwidth;
			height = wheight;
			glViewport (0, 0, width, height);
		}
		
	}
	
}