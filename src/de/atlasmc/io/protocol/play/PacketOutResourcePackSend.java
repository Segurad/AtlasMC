package de.atlasmc.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(PacketPlay.OUT_RESOURCE_PACK_SEND)
public class PacketOutResourcePackSend extends AbstractPacket implements PacketPlayOut {
	
	private String url, hash;
	
	public String getURL() {
		return url;
	}
	
	public void setURL(String url) {
		this.url = url;
	}
	
	public String getHash() {
		return hash;
	}
	
	public void setHash(String hash) {
		this.hash = hash;
	}
	
	@Override
	public int getDefaultID() {
		return OUT_RESOURCE_PACK_SEND;
	}

}
