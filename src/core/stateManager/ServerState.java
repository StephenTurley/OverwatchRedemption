package core.stateManager;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

public abstract class ServerState extends Listener {

	protected Server server;
	
	public ServerState(Server server) {
		this.server = server;
	}
	
	public abstract void update();
	public abstract void enter();
	public abstract void exit();
	@Override
	public abstract void connected(Connection c);
	@Override
	public abstract void received (Connection c, Object object);
	@Override
	public abstract void disconnected(Connection c);
	@Override
	public abstract void idle(Connection c);
	

}
