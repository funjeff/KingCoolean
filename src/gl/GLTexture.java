package gl;

import static org.lwjgl.opengl.GL42.*;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Arrays;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;


public class GLTexture {

	private int textureName;
	private int textureType;
	private int sourceDataType;
	private int texDataFormat;
	private int texDataType;
	
	public GLTexture (int textureType) {
		
		this.textureType = textureType;
		textureName = glGenTextures ();
		
		//Set parameters
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);	
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
	
	}
	
	public GLTexture (int textureType, BufferedImage img) {
		
		//Gen texture
		this.textureType = textureType;
		textureName = glGenTextures ();
		glBindTexture (textureType, textureName);
		
		//Set parameters
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);	
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
		
		setData (img, GL_RGBA8, GL_RGBA, GL_UNSIGNED_BYTE);
		
	}
	
	//For sourceDataType, texDataFormat, texDataType: good defaults are RGBA, RGBA, and BYTE respectively.
	public GLTexture (int textureType, int width, int height, int sourceDataType, int texDataFormat, int texDataType, int[] data) {
		
		this (textureType);
		//setData (width, height, sourceDataType, texDataFormat, texDataType, data);
		
		
	}
	
	public void setData (BufferedImage image, int sourceDataType, int texDataFormat, int texDataType) {
		
		this.sourceDataType = sourceDataType;
		this.texDataFormat = texDataFormat;
		this.texDataType = texDataType;
		glBindTexture (textureType, textureName);
		int[] pixels = new int[image.getWidth() * image.getHeight()];
        image.getRGB(0, 0, image.getWidth(), image.getHeight(), pixels, 0, image.getWidth());

        ByteBuffer buffer = BufferUtils.createByteBuffer (image.getWidth() * image.getHeight() * 4); //4 for RGBA, 3 for RGB

        for(int y = 0; y < image.getHeight(); y++){
            for(int x = 0; x < image.getWidth(); x++){
                int pixel = pixels[y * image.getWidth() + x];
                buffer.put((byte) ((pixel >> 16) & 0xFF));     // Red component
                buffer.put((byte) ((pixel >> 8) & 0xFF));      // Green component
                buffer.put((byte) (pixel & 0xFF));               // Blue component
                buffer.put((byte) ((pixel >> 24) & 0xFF));    // Alpha component. Only for RGBA
            }
        }

        buffer.flip(); //FOR THE LOVE OF GOD DO NOT FORGET THIS
		glPixelStorei (1, GL_UNPACK_ALIGNMENT);
		switch (textureType) {
			case GL_TEXTURE_2D:
				glTexImage2D (
						GL_TEXTURE_2D,
						0,
						GL_RGBA8,
						image.getWidth (),
						image.getHeight (),
						0,
						texDataFormat,
						texDataType,
						buffer);
				break;
			default:
				//Other texture types are not supported
				break;
		}
		
		//Generate mipmaps
		glGenerateMipmap (GL_TEXTURE_2D);
		
	}
	
	public void bindTexture () {
		glBindTexture (textureType, textureName);
	}
	
}
