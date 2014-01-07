/*******************************************************************************
 * Copyright 2013 Stephen Turley
 * 
 * This software is part of the Overwatch-Redemption and is not licensed for redistribution. 
 * You may not reproduce any part of this work unless otherwise stated.
 ******************************************************************************/
package core.network;

import java.util.ArrayList;

import serverStates.ServerStartState;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Server;

import core.Debug;
import core.Game;
import core.entity.Entity;
import core.level.ServerLevel;
import core.network.Network.EntitiesPacket;
import core.network.Network.PlayersPacket;
import core.stateManager.ServerState;

public class GameServer{
	
		private Server server;
		private ServerState currentState;
		private boolean playersReady;
		private ServerLevel currentLevel;

		
		public void init()
	 	{
	 		server = new Server(){
	 			protected Connection newConnection(){
	 				return new PlayerConnection();
	 			}
	 		};

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
	 			server.bind( Game.getGameConfig().getServerTCP(),Game.getGameConfig().getServerUDP());
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
	 		server.close();
	 	}
	 	public Server getServer()
	 	{
	 		return server;
	 	}
	 	
	 	
	 	public int getPlayerCount()
	 	{
	 		return server.getConnections().length;
	 	}
	 	public boolean isPlayersReady() {
			return playersReady;
		}
		public void setPlayersReady(boolean playersReady) {
			
			for(Connection c: server.getConnections())
			{
				PlayerConnection pc = (PlayerConnection)c;
				pc.isReady = playersReady;
			}
			this.playersReady = playersReady;
		}
		public void setPlayerReady(int playerID, boolean playerReady)
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
		
		public void sendToAllTCP(Object object)
		{
				server.sendToAllTCP(object);
		}
		public void sendToAllUDP(Object object)
		{
				server.sendToAllUDP(object);
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
		public ServerLevel getCurrentLevel() {
			return currentLevel;
		}
		public void setCurrentLevel(ServerLevel currentLevel) {
			this.currentLevel = currentLevel;
		}
		
		public ArrayList<PlayerConnection> getPlayerConnections()
		{
			ArrayList<PlayerConnection> playerConnections = new ArrayList<PlayerConnection>();
			
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
		public void updateEntity(Entity entity) {
		
			currentLevel.getEntityCollection().addEntity(entity.getID(), entity);
		}
		public void sendEntitiesPacket() {
			EntitiesPacket ep = new EntitiesPacket();
			
			ArrayList<Entity> entities =  currentLevel.getEntityCollection().getEntities();
			
			ep.entities = new EntityDataPacket[entities.size()];
			
			for(int i = 0; i < entities.size(); i++)
			{
				ep.entities[i] = entities.get(i).getDataPacket();
			}
			
			sendToAllUDP(ep);
		}

}
