package de.atlasmc.node.io.protocol.play;

import java.util.List;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.io.metadata.MetaDataInfo;

@DefaultPacketID(packetID = PacketPlay.OUT_SET_ENTITY_METADATA, definition = "set_entity_data")
public class PacketOutSetEntityMetadata extends AbstractPacket implements PacketPlayOut {
	
	public int entityID;
	public List<MetaDataInfo<Object>> data;
	
	@Override
	public int getDefaultID() {
		return OUT_SET_ENTITY_METADATA;
	}

}
