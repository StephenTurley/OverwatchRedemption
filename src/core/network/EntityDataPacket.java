package core.network;

import java.util.UUID;

import org.lwjgl.util.Point;

public class EntityDataPacket {
	public String className;
	public UUID uuid;
	public Point position;
	public int layer;
}
