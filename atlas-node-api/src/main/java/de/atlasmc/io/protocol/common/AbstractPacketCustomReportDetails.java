package de.atlasmc.io.protocol.common;

import java.util.List;

import de.atlasmc.io.AbstractPacket;

public abstract class AbstractPacketCustomReportDetails extends AbstractPacket {

	public List<Detail> details;
	
	public static class Detail {
		
		public String title;
		public String description;
		
		public Detail() {}
		
		public Detail(String title, String description) {
			this.title = title;
			this.description = description;
		}
		
	}
	
}
