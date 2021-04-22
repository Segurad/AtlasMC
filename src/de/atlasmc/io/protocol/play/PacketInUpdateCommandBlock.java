package de.atlasmc.io.protocol.play;

import de.atlasmc.io.Packet;

public interface PacketInUpdateCommandBlock extends Packet {
	
	public long getPosition();
	public String getCommand();
	public int getMode();
	public byte Flags();
	
	@Override
	default int getDefaultID() {
		return 0x26;
	}

}
