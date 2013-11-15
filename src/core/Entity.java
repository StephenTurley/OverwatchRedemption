package core;


public abstract class Entity implements DrawableComponent {
	
	protected int locX, locY, width, height;
	protected float scale, rotation;
	
	public Entity()
	{
		
	}
	
	public Entity(int startX, int startY, int width, int height, float scale, float rotation) {
		this.locX = startX;
		this.locY = startY;
		this.width = width;
		this.height = height;
		this.scale = scale;
		this.rotation = rotation;
	}

	@Override
	public void draw() {

	}

	@Override
	public void update(int delta) {
		
	}
	 
	public void collideWith(Entity entity)
	{
		
	}

}
