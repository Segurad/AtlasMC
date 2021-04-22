package de.atlasmc.io.protocol.play;

import java.util.List;
import java.util.UUID;

import de.atlasmc.Gamemode;
import de.atlasmc.chat.component.ChatComponent;
import de.atlasmc.io.Packet;

public interface PacketOutPlayerInfo extends Packet {
	
	public PlayerInfoAction getAction();
	public List<PlayerInfo> getPlayers();
	
	@Override
	public default int getDefaultID() {
		return 0x32;
	}
	
	public static class PlayerInfo {
		
		private final UUID uuid;
		private String name;
		private String textures;
		private Gamemode gamemode = Gamemode.SURVIVAL;
		private int ping;
		private String displayName;
		
		public PlayerInfo(UUID uuid) {
			this.uuid = uuid;
		}
		
		public PlayerInfo(UUID uuid, Gamemode gamemode) {
			this.uuid = uuid;
			this.gamemode = gamemode;
		}
		
		public PlayerInfo(UUID uuid, int ping) {
			this.uuid = uuid;
			this.ping = ping;
		}
		
		public PlayerInfo(UUID uuid, ChatComponent displayName) {
			this.uuid = uuid;
			this.displayName = displayName.getJsonText();
		}
		
		public PlayerInfo(UUID uuid, String name, String textures, int ping, ChatComponent displayName, Gamemode gamemode) {
			this.uuid = uuid;
			this.name = name;
			this.textures = textures;
			this.ping = ping;
			this.displayName = displayName.getJsonText();
			this.gamemode = gamemode;
		}
		
		public UUID getUUID() {
			return uuid;
		}
		
		public String getName() {
			return name;
		}
		
		public String getTextures() {
			return textures;
		}
		
		public Gamemode getGamemode() {
			return gamemode;
		}

		public int getPing() {
			return ping;
		}

		public String getDisplayName() {
			return displayName;
		}

		public void setGamemode(Gamemode gamemode) {
			this.gamemode = gamemode;
		}

		public void setPing(int ping) {
			this.ping = ping;
		}

		public void setDisplayName(ChatComponent displayName) {
			this.displayName = displayName != null ? displayName.getJsonText() : null;
		}
		
		public boolean hasDisplayName() {
			return displayName != null;
		}
		
		public boolean hasTextures() {
			return textures != null;
		}
		
	}
	
	public static enum PlayerInfoAction {
		ADD_PLAYER,
		UPDATE_GAMEMODE,
		UPDATE_LATENCY,
		UPDATE_DISPLAY_NAME,
		REMOVE_PLAYER;
		
		public static PlayerInfoAction getByID(int id) {
			return values()[id];
		}
		
	}

}
