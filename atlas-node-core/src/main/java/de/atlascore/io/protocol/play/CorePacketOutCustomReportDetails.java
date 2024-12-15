package de.atlascore.io.protocol.play;

import de.atlascore.io.protocol.common.CoreAbstractPacketCustomReportDetails;
import de.atlasmc.io.Packet;
import de.atlasmc.io.protocol.play.PacketOutCustomReportDetails;

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
