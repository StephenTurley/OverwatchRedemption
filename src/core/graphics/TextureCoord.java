package core.graphics;

public class TextureCoord {
	
	public final int X, Y, X2, Y2, glTextureID;
	
	public TextureCoord(int glTextureID, int X, int Y, int X2, int Y2)
	{
		this.X = X;
		this.Y = Y;
		this.X2 = X2;
		this.Y2 = Y2;
		this.glTextureID = glTextureID;
	}

}
