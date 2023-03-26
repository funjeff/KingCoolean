package gameObjects;

import org.lwjgl.glfw.GLFW;

import engine.GameObject;
import engine.Sprite;

public class Test extends GameObject {
	
	public Test () {
		this.setSprite (new Sprite ("jetpack.png"));
	}
	
	@Override
	public void frameEvent () {
		this.rotation += Math.PI / 180;
		if (this.keyDown (GLFW.GLFW_KEY_W)) {
			setY (getY () - 1);
		}
		if (this.keyDown (GLFW.GLFW_KEY_A)) {
			setX (getX () - 1);
		}
		if (this.keyDown (GLFW.GLFW_KEY_S)) {
			setY (getY () + 1);
		}
		if (this.keyDown (GLFW.GLFW_KEY_D)) {
			setX (getX () + 1);
		}
	}
	
}
