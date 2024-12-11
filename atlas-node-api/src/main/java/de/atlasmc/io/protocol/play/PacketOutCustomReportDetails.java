package de.atlasmc.io.protocol.play;

import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.io.protocol.common.AbstractPacketCustomReportDetails;

@DefaultPacketID(packetID = PacketPlay.OUT_CUSTOM_REPORT_DETAILS, definition = "custom_report_details")
public class PacketOutCustomReportDetails extends AbstractPacketCustomReportDetails implements PacketPlayOut {

	@Override
	public int getDefaultID() {
		return OUT_CUSTOM_REPORT_DETAILS;
	}
	
}
