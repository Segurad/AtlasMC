package de.atlasmc.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(PacketPlay.OUT_SET_TAB_LIST_HEADER_AND_FOOTER)
public class PacketOutSetTabListHeaderAndFooter extends AbstractPacket implements PacketPlayOut {
	
	private String header;
	private String footer;
	
	public String getHeader() {
		return header;
	}
	
	public String getFooter() {
		return footer;
	}
	
	public void setHeader(String header) {
		this.header = header;
	}
	
	public void setFooter(String footer) {
		this.footer = footer;
	}
	
	@Override
	public int getDefaultID() {
		return OUT_SET_TAB_LIST_HEADER_AND_FOOTER;
	}

}
