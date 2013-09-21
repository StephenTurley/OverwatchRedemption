package core.network;

import java.net.InetAddress;


import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import core.network.Network.SimpleMessage;
import core.Debug;
import core.Game;

public class GameClient {

	private static Client client;
	
	private static void init()
	{
		client = new Client();
		Network.register(client);
		client.start();
		addListeners();
	}
	public static void init(int timeout, InetAddress host, int tcp, int udp)
	{
		init();
		try{
			client.connect(timeout, host, tcp, udp);
		}catch(Exception e)
		{
			Debug.Trace(e.getMessage());
			Game.exit(-1);
		}
	}
	public static void init(int timeout, String host, int tcp, int udp) {
		init();
		try{
			client.connect(timeout, host, tcp, udp);
		}catch(Exception e)
		{
			Debug.Trace(e.getMessage());
			Game.exit(-1);
		}	
	}
	private static void addListeners()
	{
		client.addListener(new Listener(){
			public void received(Connection connection, Object object)
			{
				if(object instanceof SimpleMessage)
				{
					SimpleMessage msgPacket = (SimpleMessage)object;
					System.out.println(msgPacket.msg);
				}
			}
		});
	}
}
