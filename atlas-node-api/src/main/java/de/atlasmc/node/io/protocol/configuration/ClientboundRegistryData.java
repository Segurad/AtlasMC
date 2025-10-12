package de.atlasmc.node.io.protocol.configuration;

import java.util.List;

import de.atlasmc.NamespacedKey;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.util.nbt.tag.NBT;

@DefaultPacketID(packetID = PacketConfiguration.OUT_REGISTRY_DATA, definition = "registry_data")
public class ClientboundRegistryData extends AbstractPacket implements PacketConfigurationClientbound {

	public NamespacedKey registryID;
	public List<RegistryEntry> entries;
	
	public static class RegistryEntry {
		
		public NamespacedKey entryID;
		public NBT data;
		
		public RegistryEntry(NamespacedKey entryID, NBT data) {
			this.entryID = entryID;
			this.data = data;
		}
		
	}
	
	@Override
	public int getDefaultID() {
		return OUT_REGISTRY_DATA;
	}
	
}
