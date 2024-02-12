package de.atlasmc.io.protocol.play;

import java.util.List;
import java.util.UUID;

import de.atlasmc.Gamemode;
import de.atlasmc.atlasnetwork.ProfileProperty;
import de.atlasmc.chat.PlayerChatSignatureData;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(PacketPlay.OUT_PLAYER_INFO_UPDATE)
public class PacketOutPlayerInfoUpdate extends AbstractPacket implements PacketPlayOut {
	
	public static final int 
		ACTION_ADD_PLAYER = 0x01,
		ACTION_INIT_CHAT = 0x02,
		ACTION_UPDATE_GAMEMODE = 0x04,
		ACTION_UPDATE_LISTED = 0x08,
		ACTION_UPDATE_LATENCY = 0x10,
		ACTION_UPDATE_DISPLAY_NAME = 0x20;
	
	
	private byte actions;
	private List<PlayerInfo> info;
	
	public byte getActions() {
		return actions;
	}
	
	public void setActions(byte actions) {
		this.actions = actions;
	}
	
	public void setInfo(List<PlayerInfo> info) {
		this.info = info;
	}
	
	public List<PlayerInfo> getPlayers() {
		return info;
	}
	
	@Override
	public int getDefaultID() {
		return OUT_PLAYER_INFO_UPDATE;
	}
	
	public static class PlayerInfo {
		
		public String name;
		public UUID uuid;
		public List<ProfileProperty> properties;
		public Gamemode gamemode;
		public int ping;
		public String displayName;
		public boolean listed;
		public PlayerChatSignatureData chatSignature;
		
		public PlayerInfo(UUID uuid) {
			this.uuid = uuid;
		}
		
	}

}
