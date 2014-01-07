package core.network;

import java.util.UUID;

public class PlayerConnectionDataPacket
{
	public String name;
	public int connectionID;
	public UUID uuid;
	public boolean isReady;
}