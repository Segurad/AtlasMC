package de.atlasmc.io.protocol.play;

import de.atlasmc.io.Packet;

public interface PacketInTabComplete extends Packet {

	public int getTransactionID();
	public String getText();
}
