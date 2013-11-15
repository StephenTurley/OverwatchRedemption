package core;


public abstract class Entity implements DrawableComponent {
	
	protected int posX, posY, width, height;
	protected float scale, rotation;
	
	public Entity()
	{
		
	}
	
	public Entity(int width, int height)
	{
		this.width =  width;
		this.height = height;
	}
	
	public int getPosX() {
		return posX;
	}

	public void setPosX(int locX) {
		this.posX = locX;
	}

	public int getPosY() {
		return posY;
	}

	public void setPosY(int locY) {
		this.posY = locY;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public float getScale() {
		return scale;
	}

	public void setScale(float scale) {
		this.scale = scale;
	}

	public float getRotation() {
		return rotation;
	}

	public void setRotation(float rotation) {
		this.rotation = rotation;
	}

	public Entity(int startX, int startY, int width, int height, float scale, float rotation) {
		this.posX = startX;
		this.posY = startY;
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
