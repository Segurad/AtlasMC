package de.atlasmc.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(PacketPlay.OUT_RESOURCE_PACK)
public class PacketOutResourcePack extends AbstractPacket implements PacketPlayOut {
	
	private String url;
	private String hash;
	private String promt;
	private boolean forced;
	
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
	
	public boolean isForced() {
		return forced;
	}
	
	public void setForced(boolean forced) {
		this.forced = forced;
	}
	
	public String getPromt() {
		return promt;
	}
	
	public void setPromt(String promt) {
		this.promt = promt;
	}
	
	@Override
	public int getDefaultID() {
		return OUT_RESOURCE_PACK;
	}

}
