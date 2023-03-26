package gl;

import static org.lwjgl.opengl.GL20.*;

public class GLProgram {

	private GLShader vertexShader;
	private GLShader fragShader;
	
	private int programName;
	private boolean linked = false;
	
	public GLProgram (GLShader vertexShader, GLShader fragShader) {
		
		//Set the shaders
		this.vertexShader = vertexShader;
		this.fragShader = fragShader;
		
		//Link the programs
		programName = glCreateProgram ();
		glAttachShader (programName, vertexShader.getShaderName ());
		glAttachShader (programName, fragShader.getShaderName ());
		glLinkProgram (programName);
		int status = glGetProgrami (programName, GL_LINK_STATUS);
		
		//Check for linking errors and print them out
		if (status == 0) {
			System.out.println ("Error: shader program failed to link:");
			System.out.print (glGetProgramInfoLog (programName));
			glDeleteProgram (programName);
		} else {
			linked = true;
		}
		
	}
	
	public boolean isLinked () {
		return linked;
	}
	
	public GLShader getVertexShader () {
		return vertexShader;
	}
	
	public GLShader getFragShader () {
		return fragShader;
	}
	
	public int getProgramName () {
		return programName;
	}
	
	public void use () {
		glUseProgram (programName);
	}
	
	public static GLProgram programFromFiles (String vertexPath, String fragPath) {
		GLShader vertexShader = GLShader.shaderFromFile (vertexPath, GL_VERTEX_SHADER);
		GLShader fragShader = GLShader.shaderFromFile (fragPath, GL_FRAGMENT_SHADER);
		return new GLProgram (vertexShader, fragShader);
	}
	
	public static GLProgram programFromDirectory (String folder) {
		GLShader vertexShader = GLShader.shaderFromFile (folder + "vertex.glsl", GL_VERTEX_SHADER);
		GLShader fragShader = GLShader.shaderFromFile (folder + "fragment.glsl", GL_FRAGMENT_SHADER);
		return new GLProgram (vertexShader, fragShader);
	}
	
}
