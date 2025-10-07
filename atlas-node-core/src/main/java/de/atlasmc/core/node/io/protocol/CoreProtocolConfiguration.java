package de.atlasmc.core.node.io.protocol;

import de.atlasmc.core.node.io.protocol.configuration.*;
import de.atlasmc.io.AbstractProtocol;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.PacketListener;
import de.atlasmc.node.io.protocol.PlayerConnection;
import de.atlasmc.node.io.protocol.ProtocolConfiguration;
import de.atlasmc.node.io.protocol.configuration.PacketConfigurationIn;
import de.atlasmc.node.io.protocol.configuration.PacketConfigurationOut;

public class CoreProtocolConfiguration extends AbstractProtocol<PacketConfigurationIn, PacketConfigurationOut> implements ProtocolConfiguration {

	@SuppressWarnings("unchecked")
	public CoreProtocolConfiguration() {
		super(new PacketIO[] {
			new CorePacketInClientInformation(),
			new CorePacketInPluginMessage(),
			new CorePacketInFinishConfiguration(),
			new CorePacketInKeepAlive(),
			new CorePacketInPong(),
			new CorePacketInResourcePack(),
			new CorePacketInCookieResponse(),
			new CorePacketInKnownPacks()
		}, new PacketIO[] {
			new CorePacketOutPluginMessage(),
			new CorePacketOutDisconnect(),
			new CorePacketOutFinishConfiguration(),
			new CorePacketOutKeepAlive(),
			new CorePacketOutPing(),
			new CorePacketOutRegistryData(),
			new CorePacketOutAddResourcePack(),
			new CorePacketOutFeatureFlags(),
			new CorePacketOutUpdateTags(),
			new CorePacketOutCookieRequest(),
			new CorePacketOutResetChat(),
			new CorePacketOutRemoveResourcePack(),
			new CorePacketOutStoreCookie(),
			new CorePacketOutTransfer(),
			new CorePacketOutKnownPacks(),
			new CorePacketOutCustomReportDetails(),
			new CorePacketOutServerLinks()
		});
	}
	
	@Override
	public int getVersion() {
		return CoreProtocolAdapter.VERSION;
	}

	@Override
	public PacketListener createDefaultPacketListenerIn(Object o) {
		if (o instanceof PlayerConnection con)
			return new CorePacketListenerConfigurationIn(con);
		throw new IllegalArgumentException("Expected PlayerConnection but got: " + o.getClass().getName());
	}

	@Override
	public PacketListener createDefaultPacketListenerOut(Object o) {
		return null;
	}

}
