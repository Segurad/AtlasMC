package de.atlasmc.node.io.protocol.play;

import de.atlasmc.IDHolder;
import de.atlasmc.NamespacedKey;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(packetID = PacketPlay.IN_SEEN_ADVANCEMENTS, definition = "seen_advancements")
public class PacketInSeenAdvancements extends AbstractPacket implements PacketPlayIn {
	
	public Action action;
	public NamespacedKey tabID;
	
	@Override
	public int getDefaultID() {
		return IN_SEEN_ADVANCEMENTS;
	}
	
	public static enum Action implements IDHolder {

		OPEN,
		CLOSE;

		@Override
		public int getID() {
			return ordinal();
		}
		
	}

}
