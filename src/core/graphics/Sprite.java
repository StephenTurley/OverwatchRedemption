package core.graphics;

public abstract class Sprite implements DrawableComponent {
	
	private Camera camera;
	
	public Sprite(Camera camera)
	{
		this.camera = camera;
	}

	@Override
	public void draw() {
		
	}

	@Override
	public void update(int delta) {
		// TODO Auto-generated method stub

	}
	
	public void load()
	{
		
	}

}
