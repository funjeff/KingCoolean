package engine;

import com.hackoeur.jglm.*;

import gl.GLTexture;

public class DrawCall {
	
	private Mat4 transform;
	private GLTexture tex;
	private GameObject obj;
	
	public DrawCall (Mat4 transform, GLTexture tex, GameObject obj) {
		this.transform = transform;
		this.tex = tex;
		this.obj = obj;
	}
	
	/*public DrawCall (Mat4 transform, GLTexture tex) {
		this.transform = transform;
		this.tex = tex;
		this.obj = null;
	}*/
	
	public Mat4 getTransform () {
		return transform;
	}
	
	public GLTexture getTexture () {
		return tex;
	}
	
	public GameObject getCallingObject () {
		return obj;
	}
	
}
