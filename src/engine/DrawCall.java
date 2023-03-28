package engine;

import org.joml.Matrix4f;

import gl.GLTexture;

public class DrawCall {
	
	private Matrix4f transform;
	private GLTexture tex;
	private GameObject obj;
	
	public DrawCall (Matrix4f transform, GLTexture tex, GameObject obj) {
		this.transform = transform;
		this.tex = tex;
		this.obj = obj;
	}
	
	/*public DrawCall (Matrix4f transform, GLTexture tex) {
		this.transform = transform;
		this.tex = tex;
		this.obj = null;
	}*/
	
	public Matrix4f getTransform () {
		return transform;
	}
	
	public GLTexture getTexture () {
		return tex;
	}
	
	public GameObject getCallingObject () {
		return obj;
	}
	
}
