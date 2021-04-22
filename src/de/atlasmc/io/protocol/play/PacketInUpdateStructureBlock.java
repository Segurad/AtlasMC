package de.atlasmc.io.protocol.play;

import de.atlasmc.io.Packet;

public interface PacketInUpdateStructureBlock extends Packet {
	
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
		return 0x2A;
	}
	
}
