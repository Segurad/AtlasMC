package de.atlasmc.io.protocol.play;

import java.util.List;
import java.util.UUID;

import de.atlasmc.BossBar;
import de.atlasmc.BossBar.BarColor;
import de.atlasmc.BossBar.BarFlag;
import de.atlasmc.BossBar.BarStyle;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(PacketPlay.OUT_BOSS_BAR)
public class PacketOutBossBar extends AbstractPacket implements PacketPlayOut {
	
	private UUID uuid;
	private BossBarAction action;
	private BarColor color;
	private BarStyle style;
	private int flags;
	private String title;
	private float health;
	
	public UUID getUUID() {
		return uuid;
	}
	
	public void setUUID(UUID uuid) {
		this.uuid = uuid;
	}
	
	public BossBarAction getAction() {
		return action;
	}
	
	public void setAction(BossBarAction action) {
		this.action = action;
	}
	
	public BarColor getColor() {
		return color;
	}
	
	public void setColor(BarColor color) {
		this.color = color;
	}
	
	public BarStyle getStyle() {
		return style;
	}
	
	public void setStyle(BarStyle style) {
		this.style = style;
	}
	
	/**
	 * Sets sets the {@link BarFlag}s for a {@link BossBar}
	 * <li>
	 * 0x01 {@link BarFlag#DARKEN_SKY}
	 * <li> 
	 * 0x02 {@link BarFlag#PLAY_BOSS_MUSIC}
	 * <li>
	 * 0x04 {@link BarFlag#CREATE_FOG}
	 * @param flags
	 */
	public void setFlags(int flags) {
		this.flags = flags;
	}
	
	public int getFlags() {
		return flags;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public float getHealth() {
		return health;
	}
	
	public void setHealth(float health) {
		this.health = health;
	}
	
	@Override
	public int getDefaultID() {
		return OUT_BOSS_BAR;
	}
	
	public static enum BossBarAction {
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
