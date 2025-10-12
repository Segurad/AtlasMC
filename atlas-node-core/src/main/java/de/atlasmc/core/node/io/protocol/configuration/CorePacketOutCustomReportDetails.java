package de.atlasmc.core.node.io.protocol.configuration;

import de.atlasmc.core.node.io.protocol.common.CoreAbstractPacketCustomReportDetails;
import de.atlasmc.io.Packet;
import de.atlasmc.node.io.protocol.configuration.ClientboundCustomReportDetails;

public class CorePacketOutCustomReportDetails extends CoreAbstractPacketCustomReportDetails<ClientboundCustomReportDetails> {

	@Override
	public ClientboundCustomReportDetails createPacketData() {
		return new ClientboundCustomReportDetails();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(ClientboundCustomReportDetails.class);
	}

}
