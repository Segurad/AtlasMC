package de.atlasmc.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(PacketPlay.IN_UPDATE_SIGN)
public class PacketInUpdateSign extends AbstractPacket implements PacketPlayIn {
	
	private long position;
	private String line1,line2,line3,line4;
	
	public long getPosition() {
		return position;
	}
	
	public void setPosition(long position) {
		this.position = position;
	}
	
	public String getLine1() {
		return line1;
	}
	
	public String getLine2() {
		return line2;
	}
	
	public String getLine3() {
		return line3;
	}
	
	public String getLine4() {
		return line4;
	}
	
	public void setLine1(String line1) {
		this.line1 = line1;
	}
	
	public void setLine2(String line2) {
		this.line2 = line2;
	}
	
	public void setLine3(String line3) {
		this.line3 = line3;
	}
	
	public void setLine4(String line4) {
		this.line4 = line4;
	}
	
	@Override
	public int getDefaultID() {
		return IN_UPDATE_SIGN;
	}

}
