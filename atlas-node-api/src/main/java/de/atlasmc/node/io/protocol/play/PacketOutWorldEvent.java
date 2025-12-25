package de.atlasmc.node.io.protocol.play;

import org.joml.Vector3i;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.node.util.MathUtil;
import de.atlasmc.node.world.WorldEvent;

@DefaultPacketID(packetID = PacketPlay.OUT_WORLD_EVENT, definition = "level_event")
public class PacketOutWorldEvent extends AbstractPacket implements PacketPlayOut {
	
	public WorldEvent event;
	public long position;
	public int data;
	public boolean disableRelativeVolume;
	
	public PacketOutWorldEvent(Vector3i loc, WorldEvent effect, Object data, boolean relativeSound) {
		this.event = effect;
		this.position = MathUtil.toPosition(loc);
		this.data = effect.getDataValueByObject(data);
		this.disableRelativeVolume = !relativeSound;
	}
	
	public PacketOutWorldEvent() {
		// simple constructor
	}
	
	@Override
	public int getDefaultID() {
		return OUT_WORLD_EVENT;
	}

}
