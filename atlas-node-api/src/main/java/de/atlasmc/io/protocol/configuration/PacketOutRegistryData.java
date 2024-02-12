package de.atlasmc.io.protocol.configuration;

import java.util.List;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.registry.Registry;
import de.atlasmc.util.nbt.tag.NBT;

@DefaultPacketID(PacketConfiguration.OUT_REGISTRY_DATA)
public class PacketOutRegistryData extends AbstractPacket implements PacketConfigurationOut {

	public List<Registry<?>> data;
	public NBT rawData;
	
	@Override
	public int getDefaultID() {
		return OUT_REGISTRY_DATA;
	}
	
}
