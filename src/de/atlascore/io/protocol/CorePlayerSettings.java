package de.atlascore.io.protocol;

import de.atlasmc.chat.ChatType;
import de.atlasmc.io.protocol.PlayerSettings;

public class CorePlayerSettings implements PlayerSettings {

	private String local;
	private ChatType chatmode = ChatType.MESSAGE;
	private boolean chatColors = true;
	private int viewDistance;
	private int mainHand;
	private int skinparts;
	
	@Override
	public String getLocal() {
		return local;
	}

	@Override
	public void setLocal(String local) {
		this.local = local;
	}

	@Override
	public int getViewDistance() {
		return viewDistance;
	}

	@Override
	public void setViewDistance(int distance) {
		this.viewDistance = distance;
	}

	@Override
	public int getMainHand() {
		return mainHand;
	}

	@Override
	public void setMainHand(int mainHand) {
		this.mainHand = mainHand;
	}

	@Override
	public void setSkinParts(int skinparts) {
		this.skinparts = skinparts;
	}

	@Override
	public int getSkinParts() {
		return skinparts;
	}

	@Override
	public void setChatMode(ChatType mode) {
		this.chatmode = mode;
	}

	@Override
	public void setChatColor(boolean color) {
		this.chatColors = color;
	}

	@Override
	public boolean getChatColor() {
		return chatColors;
	}

	@Override
	public ChatType getChatMode() {
		return chatmode;
	}

}
