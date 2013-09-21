package core.network;

import java.net.InetAddress;

import com.esotericsoftware.kryonet.Client;

import core.Debug;
import core.Game;

public class GameClient {

	private static Client client;
	
	public static void init(int timeout, InetAddress host, int tcp, int udp)
	{
		client = new Client();
		client.start();
		try{
			client.connect(timeout, host, tcp, udp);
		}catch(Exception e)
		{
			Debug.Trace(e.getMessage());
			Game.exit(-1);
		}
	}
	public static void init(int timeout, String host, int tcp,
			int udp) {
		client = new Client();
		client.start();
		try{
			client.connect(timeout, host, tcp, udp);
		}catch(Exception e)
		{
			Debug.Trace(e.getMessage());
			Game.exit(-1);
		}	
	}
	public static void addListener()
	{
		
	}
	

}
