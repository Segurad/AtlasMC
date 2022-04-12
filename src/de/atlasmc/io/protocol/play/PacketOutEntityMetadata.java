package de.atlasmc.io.protocol.play;

import de.atlasmc.entity.data.MetaDataContainer;
import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.io.PacketOutbound;
import io.netty.buffer.ByteBuf;

@DefaultPacketID(PacketPlay.OUT_ENTITY_METADATA)
public interface PacketOutEntityMetadata extends PacketPlay, PacketOutbound {
	
	public int getEntityID();
	
	public void setEntityID(int id);
	
	/**
	 * Sets all data of the {@link MetaDataContainer} to this packet that is not default
	 * @param container
	 */
	public void setNonDefaultData(MetaDataContainer container);
	
	/**
	 * Sets all data of the {@link MetaDataContainer} to this packet that is marked as changed
	 * @param container
	 */
	public void setChangedData(MetaDataContainer container);
	
	public ByteBuf getData();
	
	@Override
	public default int getDefaultID() {
		return OUT_ENTITY_METADATA;
	}

}
