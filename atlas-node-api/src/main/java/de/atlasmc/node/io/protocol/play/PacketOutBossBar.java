package de.atlasmc.node.io.protocol.play;

import java.util.List;
import java.util.UUID;

import de.atlasmc.chat.Chat;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.node.BossBar.BarColor;
import de.atlasmc.node.BossBar.BarStyle;
import de.atlasmc.util.EnumID;
import de.atlasmc.util.EnumValueCache;

@DefaultPacketID(packetID = PacketPlay.OUT_BOSS_BAR, definition = "boss_event")
public class PacketOutBossBar extends AbstractPacket implements PacketPlayOut {
	
	public UUID uuid;
	public BossBarAction action;
	public BarColor color;
	public BarStyle style;
	public int flags;
	public Chat title;
	public float health;
	
	@Override
	public int getDefaultID() {
		return OUT_BOSS_BAR;
	}
	
	public static enum BossBarAction implements EnumID, EnumValueCache {
		
		ADD,
		REMOVE,
		UPDATE_HEALTH,
		UPDATE_TITLE,
		UPDATE_STYLE,
		UPDATE_FLAGS;
		
		private static List<BossBarAction> VALUES;
		
		public int getID() {
			return ordinal();
		}
		
		public static BossBarAction getByID(int id) {
			return getValues().get(id);
		}
		
		/**
		 * Returns a immutable List of all Types.<br>
		 * This method avoid allocation of a new array not like {@link #values()}.
		 * @return list
		 */
		public static List<BossBarAction> getValues() {
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
