package de.atlasmc.io.protocol.play;

import java.util.ArrayList;
import java.util.List;

import de.atlasmc.entity.data.MetaData;
import de.atlasmc.entity.data.MetaDataContainer;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(PacketPlay.OUT_ENTITY_METADATA)
public class PacketOutEntityMetadata extends AbstractPacket implements PacketPlayOut {
	
	private int entityID;
	private List<MetaData<?>> data;
	
	public int getEntityID() {
		return entityID;
	}
	
	public void setEntityID(int entityID) {
		this.entityID = entityID;
	}
	
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
	
	public List<MetaData<?>> getData() {
		return data;
	}
	
	public void setData(List<MetaData<?>> data) {
		this.data = data;
	}
	
	@Override
	public int getDefaultID() {
		return OUT_ENTITY_METADATA;
	}

}
