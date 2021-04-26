package de.atlasmc.io.protocol.play;

import de.atlasmc.io.Packet;
import de.atlasmc.util.nbt.NBT;
import de.atlasmc.util.nbt.io.NBTReader;

public interface PacketOutNBTQueryResponse extends Packet {
	
	public int getTransactionID();
	public NBT getNBT();
	public NBTReader getNBTReader();
	
	@Override
	public default int getDefaultID() {
		return 0x54;
	}

}
