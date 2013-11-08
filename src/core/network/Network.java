package core.network;

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
			this.thisPlayer = thisPlayer.clone();
		}
		public Player getThatPlayer() {
			return thatPlayer;
		}
		public void setThatPlayer(Player thatPlayer) {
			this.thatPlayer = thatPlayer.clone();
		}
	}
	public static class PlayerReady
	{
		public boolean isReady;
	}
}
