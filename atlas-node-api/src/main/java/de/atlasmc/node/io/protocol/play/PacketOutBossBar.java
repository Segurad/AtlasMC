package de.atlasmc.node.io.protocol.play;

import java.util.UUID;

import de.atlasmc.IDHolder;
import de.atlasmc.chat.Chat;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.node.BossBar.BarColor;
import de.atlasmc.node.BossBar.BarStyle;

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
	
	public static enum BossBarAction implements IDHolder {
		
		ADD,
		REMOVE,
		UPDATE_HEALTH,
		UPDATE_TITLE,
		UPDATE_STYLE,
		UPDATE_FLAGS;
		
		@Override
		public int getID() {
			return ordinal();
		}
		
	}

}
