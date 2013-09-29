package core.network;

import java.net.InetAddress;
import java.util.LinkedList;


import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import core.network.Network.SimpleMessage;
import core.Debug;
import core.Game;

public class GameClient {

	private static Client client;
	private static LinkedList<String> messageQueue;
	
	private static void init()
	{
		messageQueue = new LinkedList<String>();
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
	public static String[] getServerMessages()
	{
		String[] msgs = new String[messageQueue.size()];
		
		for(int i = 0; i < messageQueue.size(); i++)
		{	
			msgs[i] = messageQueue.removeLast();
		}
		return msgs;
	}
	public static void Login(String name)
	{
		client.sendTCP(new Network.Login(name));
	}
	private static void addListeners()
	{
		client.addListener(new Listener(){
			public void received(Connection connection, Object object)
			{
				if(object instanceof SimpleMessage)
				{
					SimpleMessage msgPacket = (SimpleMessage)object;
					messageQueue.addFirst(msgPacket.msg);
					System.out.println(msgPacket.msg);
				}
			}
		});
	}
}
