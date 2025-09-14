package de.atlasmc.node.io.protocol.play;

import java.util.ArrayList;
import java.util.List;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.node.entity.data.MetaData;
import de.atlasmc.node.entity.data.MetaDataContainer;

@DefaultPacketID(packetID = PacketPlay.OUT_SET_ENTITY_METADATA, definition = "set_entity_data")
public class PacketOutSetEntityMetadata extends AbstractPacket implements PacketPlayOut {
	
	public int entityID;
	public List<MetaData<?>> data;
	
	/**
	 * Sets all data of the {@link MetaDataContainer} to this packet that is not default
	 * @param container
	 */
	public void setNonDefaultData(MetaDataContainer container) {
		if (data != null)
			data.clear();
		for (MetaData<?> meta : container) {
			if (meta.isDefault())
				continue;
			if (data == null)
				data = new ArrayList<>();
			data.add(meta.clone());
		}
	}
	
	/**
	 * Sets all data of the {@link MetaDataContainer} to this packet that is marked as changed
	 * @param container
	 */
	public void setChangedData(MetaDataContainer container) {
		if (!container.hasChanges())
			return;
		for (MetaData<?> meta : container) {
			if (!meta.hasChanged())
				continue;
			if (data == null)
				data = new ArrayList<>();
			data.add(meta.clone());
		}
	}
	
	@Override
	public int getDefaultID() {
		return OUT_SET_ENTITY_METADATA;
	}

}
