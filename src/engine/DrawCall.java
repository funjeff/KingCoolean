package engine;

import com.hackoeur.jglm.*;

import gl.GLTexture;

public class DrawCall {
	
	private Mat4 transform;
	private GLTexture tex;
	
	public DrawCall (Mat4 transform, GLTexture tex) {
		this.transform = transform;
		this.tex = tex;
	}
	
	public Mat4 getTransform () {
		return transform;
	}
	
	public GLTexture getTexture () {
		return tex;
	}
	
}
