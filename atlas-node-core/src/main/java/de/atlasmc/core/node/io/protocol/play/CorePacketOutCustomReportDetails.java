package de.atlasmc.core.node.io.protocol.play;

import de.atlasmc.core.node.io.protocol.common.CoreAbstractPacketCustomReportDetails;
import de.atlasmc.io.Packet;
import de.atlasmc.node.io.protocol.play.PacketOutCustomReportDetails;

public class CorePacketOutCustomReportDetails extends CoreAbstractPacketCustomReportDetails<PacketOutCustomReportDetails> {

	@Override
	public PacketOutCustomReportDetails createPacketData() {
		return new PacketOutCustomReportDetails();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketOutCustomReportDetails.class);
	}

}
