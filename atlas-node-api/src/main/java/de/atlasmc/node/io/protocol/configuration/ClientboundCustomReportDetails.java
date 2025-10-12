package de.atlasmc.node.io.protocol.configuration;

import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.node.io.protocol.common.AbstractPacketCustomReportDetails;

@DefaultPacketID(packetID = PacketConfiguration.OUT_CUSTOM_REPORT_DETAILS, definition = "custom_report_details")
public class ClientboundCustomReportDetails extends AbstractPacketCustomReportDetails implements PacketConfigurationClientbound {
	
	@Override
	public int getDefaultID() {
		return PacketConfiguration.OUT_CUSTOM_REPORT_DETAILS;
	}

}
