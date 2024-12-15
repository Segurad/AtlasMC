package de.atlasmc.io.protocol.configuration;

import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.io.protocol.common.AbstractPacketCustomReportDetails;

@DefaultPacketID(packetID = PacketConfiguration.OUT_CUSTOM_REPORT_DETAILS, definition = "custom_report_details")
public class PacketOutCustomReportDetails extends AbstractPacketCustomReportDetails implements PacketConfigurationOut {
	
	@Override
	public int getDefaultID() {
		return PacketConfiguration.OUT_CUSTOM_REPORT_DETAILS;
	}

}
