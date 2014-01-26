/*******************************************************************************
 * Copyright 2013 Stephen Turley
 * 
 * This software is part of the Overwatch-Redemption and is not licensed for redistribution. 
 * You may not reproduce any part of this work unless otherwise stated.
 ******************************************************************************/
package core.network;

import java.util.ArrayList;

import overwatch.serverStates.ServerStartState;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Server;

import core.Debug;
import core.Game;
import core.entity.ServerEntity;
import core.level.ServerLevel;
import core.network.Network.EntitiesPacket;
import core.network.Network.PlayersPacket;
import core.stateManager.ServerState;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import org.teleal.cling.UpnpService;
import org.teleal.cling.UpnpServiceImpl;
import org.teleal.cling.support.igd.PortMappingListener;
import org.teleal.cling.support.model.PortMapping;

public class GameServer{
	
		private Server server;
		private ServerState currentState;
		private volatile boolean playersReady;
		private volatile ServerLevel currentLevel;
		private int tcp, udp;
		//private String publicIP;
		private String privateIP;
		
		private UpnpService upnpService;

		
		public void init()
	 	{
	 		server = new Server(){
				 @Override
	 			protected Connection newConnection(){
	 				return new PlayerConnection();
	 			}
	 		};

			tcp = Game.getGameConfig().getServerTCP();
			udp = Game.getGameConfig().getServerUDP();
			try{
				getPrivateIP();
				Debug.Trace("Local ip is: " + privateIP.toString());
			}
			catch(IOException e)
			{
				Debug.Trace("Localhost lookup failed");
				Game.exit(1);
			}
	 		Network.register(server);
	 		currentState = new ServerStartState(this);
	 		currentState.enter();
	 		server.addListener(currentState);
	 		
	 		
	 	}
	 	public void start()
	 	{
	 		server.start();
	 		try
	 		{
	 			server.bind(tcp,udp);
				
				//config the router
				
				PortMapping tcpMap = new PortMapping(
					tcp,
					privateIP,
					PortMapping.Protocol.TCP,
					"tcpMap"
				);
				
				PortMapping udpMap = new PortMapping(
					udp,
					privateIP,
					PortMapping.Protocol.UDP,
					"udpMap"
				);
				
				PortMapping[] ports = {tcpMap,udpMap};
				
				upnpService = new UpnpServiceImpl(
					new PortMappingListener(ports)
				);
				
				upnpService.getControlPoint().search();
				
	 		}catch(Exception e)
	 		{
				Debug.Trace(e.getMessage());
	 			Game.exit(-1);
	 		}
	 	}
	 	public void update(int delta)
	 	{
	 		currentState.update(delta);
	 		
	 		Connection[] playerConnections = server.getConnections();
	 		
	 		if(playerConnections.length >=2 )
	 		{
		 		boolean tempReady = true;
		 		for(Connection c: playerConnections)
		 		{
		 			PlayerConnection pc = (PlayerConnection)c;

		 			if(!pc.isReady)
		 			{
		 				tempReady = false;
		 				break;
		 			}
		 		}
		 		playersReady = tempReady;
	 		}
	 		if(playersReady && currentLevel != null)
	 		{
	 			currentLevel.update(delta);
	 		}
	 	}
	 	
		public void changeState(ServerState state)
	 	{
			//ServerStartState initializes listeners that are common for all states. They shouldn't be removed. 
			if(!(currentState instanceof ServerStartState)) server.removeListener(currentState);
	 		currentState.exit();
	 		currentState = state;
	 		currentState.enter();
	 		server.addListener(state);
	 	}
	 	
	 	public void kill()
	 	{
			if(upnpService != null)
			{
				upnpService.shutdown();
			}
	 		server.close();
	 	}
	 	public Server getServer()
	 	{
	 		return server;
	 	}
	 	
	 	
	 	public synchronized int getPlayerCount()
	 	{
	 		return server.getConnections().length;
	 	}
	 	public synchronized boolean isPlayersReady() {
			return playersReady;
		}
		public synchronized void setPlayersReady(boolean playersReady) {
			
			for(Connection c: server.getConnections())
			{
				PlayerConnection pc = (PlayerConnection)c;
				pc.isReady = playersReady;
			}
			this.playersReady = playersReady;
		}
		public synchronized void setPlayerReady(int playerID, boolean playerReady)
		{
			PlayerConnection player =  getPlayerConnection(playerID);
			player.isReady = playerReady;
			String msg;
			if(playerReady)
			{
				msg = " is ready!";
			}
			else 
			{
				msg = " is not ready!";
			}
			server.sendToAllTCP(new Network.ServerMessage(player.name + msg) );
		}
		
		public synchronized void sendToAllTCP(Object object)
		{
				server.sendToAllTCP(object);
		}
		public synchronized void sendToAllUDP(Object object)
		{
				server.sendToAllUDP(object);
		}
		public synchronized void sendPacketTcp(PlayerConnection playerConnection,Object object)
		{
			server.sendToTCP(playerConnection.getID(), object);
		}
		public void sendPlayersPacket()
		{
			PlayersPacket playersPacket = new PlayersPacket();
			
			ArrayList<PlayerConnection> connections = getPlayerConnections();
			
			playersPacket.playerConnections = new PlayerConnectionDataPacket[connections.size()];
			
			for(int i = 0; i < connections.size(); i++)
			{		
				playersPacket.playerConnections[i] = connections.get(i).getPlayerData();
			}
			
			server.sendToAllUDP(playersPacket);
		}
		public synchronized ServerLevel getCurrentLevel() {
			return currentLevel;
		}
		public void setCurrentLevel(ServerLevel currentLevel) {
			this.currentLevel = currentLevel;
		}
		
		public ArrayList<PlayerConnection> getPlayerConnections()
		{
			ArrayList<PlayerConnection> playerConnections = new ArrayList<>();
			
			for(Connection c : server.getConnections())
			{
				playerConnections.add((PlayerConnection)c);
			}
			
			return playerConnections;
		}
		
		public PlayerConnection getPlayerConnection(int playerID)
		{
			for(Connection c: server.getConnections())
			{
				if(c.getID() == playerID) return (PlayerConnection)c;
			}
			return null;
		}
		public synchronized void updateEntity(ServerEntity entity) {
		
			currentLevel.getEntityCollection().addEntity(entity.getID(), entity);
		}
		public void sendEntitiesPacket() {
			EntitiesPacket ep = new EntitiesPacket();
			
			ArrayList<ServerEntity> entities =  currentLevel.getEntityCollection().getEntities();
			
			ep.entities = new EntityDataPacket[entities.size()];
			
			for(int i = 0; i < entities.size(); i++)
			{
				ep.entities[i] = entities.get(i).getDataPacket();
			}
			
			sendToAllUDP(ep);
		}
		
		public String getPrivateIP() throws UnknownHostException, IOException
		{
			if(privateIP == null)
			{
				//connect to web host to get the correct interface
				Socket s = new Socket("www.overwatchredemption.com", 80);
			
				privateIP = s.getLocalAddress().getHostAddress();
			}
			
			return privateIP;
		}
		
		/*public String getPublicIP()
		{
			//TODO: create webservice for this. 
			
		}*/

}
