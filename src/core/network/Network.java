/*******************************************************************************
 * Copyright 2013 Stephen Turley
 * 
 * This software is part of the Overwatch-Redemption and is not licensed for redistribution. 
 * You may not reproduce any part of this work unless otherwise stated.
 ******************************************************************************/
package core.network;

import org.lwjgl.util.Point;
import org.lwjgl.util.vector.Vector2f;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;

public class Network {

	public static void register(EndPoint endPoint)
	{
		Kryo kryo = endPoint.getKryo();
		kryo.register(ServerMessage.class);
		kryo.register(Login.class);
		kryo.register(PlayersPacket.class);
		kryo.register(Player.class);
		kryo.register(PlayerReady.class);
		kryo.register(StartGame.class);
		kryo.register(MovePlayer.class);
		kryo.register(Vector2f.class);
		kryo.register(LoadLevel.class);
		kryo.register(Point.class);
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
	public static class PlayersPacket
	{
		private Player thisPlayer;
		private Player thatPlayer;
		public Player getThisPlayer() {
			return thisPlayer;
		}
		public void setThisPlayer(Player thisPlayer) {
			this.thisPlayer = thisPlayer;
		}
		public Player getThatPlayer() {
			return thatPlayer;
		}
		public void setThatPlayer(Player thatPlayer) {
			this.thatPlayer = thatPlayer;
		}
	}
	public static class PlayerReady
	{
		public boolean isReady;
		
		public PlayerReady()
		{
			
		}
		public PlayerReady(boolean isReady)
		{
			this.isReady = isReady;
		}
	}
	public static class StartGame
	{
		
	}
	public static class MovePlayer
	{
		public Vector2f movementVector;
	}
	public static class LoadLevel
	{
		public int stage;
		public int level;
	}
}
