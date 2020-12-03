package de.atlasmc.io.protocol.play;

import de.atlasmc.SimpleLocation;
import de.atlasmc.io.Packet;

public interface PacketInUpdateStructureBlock extends Packet {
	
	public SimpleLocation Position();
	public int Action();
	public int Mode();
	public String Name();
	public byte OffsetX();
	public byte OffsetY();
	public byte OffsetZ();
	public byte SizeX();
	public byte SizeY();
	public byte SizeZ();
	public int Mirror();
	public int Rotation();
	public String Metadata();
	public float Integrity();
	public long Seed();
	public byte Flags();

}
