package de.atlasmc.node.io.protocol.play;

import java.util.List;

import de.atlasmc.NamespacedKey;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.util.EnumID;
import de.atlasmc.util.EnumValueCache;

@DefaultPacketID(packetID = PacketPlay.IN_SEEN_ADVANCEMENTS, definition = "seen_advancements")
public class PacketInSeenAdvancements extends AbstractPacket implements PacketPlayIn {
	
	public Action action;
	public NamespacedKey tabID;
	
	@Override
	public int getDefaultID() {
		return IN_SEEN_ADVANCEMENTS;
	}
	
	public static enum Action implements EnumID, EnumValueCache {
		OPEN,
		CLOSE;
		
		private static List<Action> VALUES;
		
		public int getID() {
			return ordinal();
		}
		
		public static Action getByID(int id) {
			return getValues().get(id);
		}
		
		/**
		 * Returns a immutable List of all Types.<br>
		 * This method avoid allocation of a new array not like {@link #values()}.
		 * @return list
		 */
		public static List<Action> getValues() {
			if (VALUES == null)
				VALUES = List.of(values());
			return VALUES;
		}
		
		/**
		 * Releases the system resources used from the values cache
		 */
		public static void freeValues() {
			VALUES = null;
		}
		
	}

}
