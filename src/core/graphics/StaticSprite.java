/*******************************************************************************
 * Copyright 2013 Stephen Turley
 * 
 * This software is part of the Overwatch-Redemption and is not licensed for redistribution. 
 * You may not reproduce any part of this work unless otherwise stated.
 ******************************************************************************/
package core.graphics;

import static org.lwjgl.opengl.ARBTextureRectangle.GL_TEXTURE_RECTANGLE_ARB;
import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.util.Point;

public class StaticSprite implements Sprite {
	
	private String name;
	private TextureCoord texCoord;
	private int offSetX, offSetY;
	
	public StaticSprite(String name,TextureCoord textureCoord)
	{
		this.name = name;
		this.texCoord = textureCoord;
		offSetX = 0;
		offSetY = 0;
		
	}
	public StaticSprite(String name,TextureCoord textureCoord, int offSetX, int offSetY)
	{
		this.name = name;
		this.texCoord = textureCoord;
		this.offSetX = offSetX;
		this.offSetY = offSetY;
	}

	public void draw(Camera camera, Point position) {
		Point screenCoord = camera.computeScreenCoordinates(position);
		int x = screenCoord.getX() - offSetX;
		int y = screenCoord.getY() - offSetY;
		
		int w = texCoord.X2 - texCoord.X;
		int h = texCoord.Y2 - texCoord.Y;
		
		if(glGetInteger(GL_TEXTURE_BINDING_2D) != texCoord.glTextureID)
		{
			glBindTexture(GL_TEXTURE_RECTANGLE_ARB, texCoord.glTextureID);
		}
		
		glColor3f(1.0f, 1.0f, 1.0f);

		// draw quad
		glBegin(GL_QUADS);
			glTexCoord2f(texCoord.X,texCoord.Y);
			glVertex2f(x , y);						//TL
			glTexCoord2f(texCoord.X2,texCoord.Y);	
			glVertex2f(x + w, y);					//TR
			glTexCoord2f(texCoord.X2,texCoord.Y2);
			glVertex2f(x + w, y + h);				//BR
			glTexCoord2f(texCoord.X,texCoord.Y2);
			glVertex2f(x, y + h);					//BL
		glEnd();
		
	}

	public void update(int delta) {
		// TODO Auto-generated method stub

	}
	public String getName()
	{
		return name;
	}


}
