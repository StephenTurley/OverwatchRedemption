package core.network;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;

public class Network {

	public static void register(EndPoint endPoint)
	{
		Kryo kryo = endPoint.getKryo();
		kryo.register(ServerMessage.class);
		kryo.register(Login.class);
	}
	public static class ServerMessage
	{
		public String msg;
		public ServerMessage()
		{
			
		}
		public ServerMessage(String msg)
		{
			this.msg = msg;
		}
	}
	public static class Login
	{
		public String name;
		public Login()
		{
			
		}
		public Login(String name)
		{
			this.name = name;
		}
	}
}
