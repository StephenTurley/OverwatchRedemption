/*******************************************************************************
 * Copyright 2013 Stephen Turley
 * 
 * This software is part of the Overwatch-Redemption and is not licensed for redistribution. 
 * You may not reproduce any part of this work unless otherwise stated.
 ******************************************************************************/
package core.graphics;

import de.matthiasmann.twl.utils.PNGDecoder;

import org.lwjgl.BufferUtils;

import core.Debug;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.HashMap;

import static org.lwjgl.opengl.ARBTextureRectangle.GL_TEXTURE_RECTANGLE_ARB;
import static org.lwjgl.opengl.GL11.*;



public class TextureLoader {
	
	private static HashMap<String, Integer> textureMap;
	
	public static int loadTexture(String path)
	{
		if(textureMap == null)
		{
			textureMap =  new HashMap<String, Integer>();
		}
		
		if(textureMap.get(path) == null)
		{
			textureMap.put(path, glLoadLinearPNG(path));
		}
		
		return textureMap.get(path);
	}
	
	
	private static int glLoadLinearPNG(String path) {
        int texture = glGenTextures();
        glBindTexture(GL_TEXTURE_RECTANGLE_ARB, texture);
        InputStream in = null;
        try {
            in = TextureLoader.class.getResourceAsStream(path);
            PNGDecoder decoder = new PNGDecoder(in);
            ByteBuffer buffer = BufferUtils.createByteBuffer(4 * decoder.getWidth() * decoder.getHeight());
            decoder.decode(buffer, decoder.getWidth() * 4, PNGDecoder.Format.RGBA);
            buffer.flip();
            glTexParameteri(GL_TEXTURE_RECTANGLE_ARB, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
            glTexImage2D(GL_TEXTURE_RECTANGLE_ARB, 0, GL_RGBA, decoder.getWidth(), decoder.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);
        } catch (FileNotFoundException e) {
            System.err.println("Texture file could not be found.");
            return -1;
        } catch (IOException e) {
            System.err.print("Failed to load the texture file.");
            return -1;
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        glBindTexture(GL_TEXTURE_RECTANGLE_ARB, 0);
        return texture;
    }
	
	public static void deleteTextures()
	{
		if(textureMap != null)
		{
			for(int t : textureMap.values())
			{
				try{
					glDeleteTextures(t);
				}
				catch(Exception e)
				{
					Debug.Trace(e.getMessage());
					continue;
				}
			}
			textureMap = new HashMap<String, Integer>();
		}
	}

}
