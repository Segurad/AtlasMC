package de.atlasmc.core.node.io.protocol.configuration;

import de.atlasmc.core.node.io.protocol.common.CoreAbstractPacketKnownPacks;
import de.atlasmc.io.Packet;
import de.atlasmc.node.io.protocol.configuration.ServerboundKnownPacks;

public class CorePacketInKnownPacks extends CoreAbstractPacketKnownPacks<ServerboundKnownPacks> {

	@Override
	public ServerboundKnownPacks createPacketData() {
		return new ServerboundKnownPacks();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(ServerboundKnownPacks.class);
	}

}
