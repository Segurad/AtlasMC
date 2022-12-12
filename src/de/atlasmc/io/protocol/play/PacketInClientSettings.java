package de.atlasmc.io.protocol.play;

import de.atlasmc.chat.ChatType;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(PacketPlay.IN_CLIENT_SETTINGS)
public class PacketInClientSettings extends AbstractPacket implements PacketPlayIn {
	
	private String locale;
	private int viewDistance;
	private ChatType chatMode;
	private int mainHand;
	private boolean chatColor;
	private int skinParts;
	
	public String getLocale() {
		return locale;
	}
	
	public void setLocale(String locale) {
		this.locale = locale;
	}

	public int getViewDistance() {
		return viewDistance;
	}
	
	public void setViewDistance(int viewDistance) {
		this.viewDistance = viewDistance;
	}

	public ChatType getChatMode() {
		return chatMode;
	}
	
	public void setChatMode(ChatType chatMode) {
		this.chatMode = chatMode;
	}

	public boolean getChatColor() {
		return chatColor;
	}
	
	public void setChatColor(boolean chatColor) {
		this.chatColor = chatColor;
	}

	public int getDisplaySkinParts() {
		return skinParts;
	}
	
	public void setDisplaySkinParts(int skinParts) {
		this.skinParts = skinParts;
	}

	public int getMainHand() {
		return mainHand;
	}
	
	public void setMainHand(int mainHand) {
		this.mainHand = mainHand;
	}
	
	public int getDefaultID() {
		return IN_CLIENT_SETTINGS;
	}

}
