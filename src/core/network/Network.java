package core.network;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;

public class Network {

	public static void register(EndPoint endPoint)
	{
		Kryo kryo = endPoint.getKryo();
		kryo.register(SimpleMessage.class);
		kryo.register(Login.class);
	}
	public static class SimpleMessage
	{
		public String msg;
		public SimpleMessage()
		{
			
		}
		public SimpleMessage(String msg)
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
