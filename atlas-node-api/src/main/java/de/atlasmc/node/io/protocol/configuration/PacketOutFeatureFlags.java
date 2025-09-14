package de.atlasmc.node.io.protocol.configuration;

import java.util.List;

import de.atlasmc.NamespacedKey;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(packetID = PacketConfiguration.OUT_FEATURE_FLAGS, definition = "update_enabled_features")
public class PacketOutFeatureFlags extends AbstractPacket implements PacketConfigurationOut {

	public List<NamespacedKey> flags;
	
	@Override
	public int getDefaultID() {
		return OUT_FEATURE_FLAGS;
	}
	
}
