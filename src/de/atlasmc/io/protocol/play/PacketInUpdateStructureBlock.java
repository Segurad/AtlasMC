package de.atlasmc.io.protocol.play;

import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.io.PacketInbound;

@DefaultPacketID(PacketPlay.IN_UPDATE_STRUCTURE_BLOCK)
public interface PacketInUpdateStructureBlock extends PacketPlay, PacketInbound {
	
	public long getPosition();
	public int getAction();
	public int getMode();
	public String getName();
	public byte getOffsetX();
	public byte getOffsetY();
	public byte getOffsetZ();
	public byte getSizeX();
	public byte getSizeY();
	public byte getSizeZ();
	public int getMirror();
	public int getRotation();
	public String getMetadata();
	public float getIntegrity();
	public long getSeed();
	public byte getFlags();

	@Override
	public default int getDefaultID() {
		return IN_UPDATE_STRUCTURE_BLOCK;
	}
	
}
