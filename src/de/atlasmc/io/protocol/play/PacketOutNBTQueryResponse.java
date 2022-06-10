package de.atlasmc.io.protocol.play;

import java.io.IOException;

import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.io.PacketOutbound;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.tag.NBT;

@DefaultPacketID(PacketPlay.OUT_NBT_QUERY_RESPONSE)
public interface PacketOutNBTQueryResponse extends PacketPlay, PacketOutbound {
	
	public int getTransactionID();
	public NBT getNBT();
	public NBTReader getNBTReader() throws IOException;
	
	@Override
	public default int getDefaultID() {
		return OUT_NBT_QUERY_RESPONSE;
	}

}
