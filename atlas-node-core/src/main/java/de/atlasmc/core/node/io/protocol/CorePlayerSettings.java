package de.atlasmc.core.node.io.protocol;

import de.atlasmc.chat.ChatMode;
import de.atlasmc.node.io.protocol.PlayerSettings;

public class CorePlayerSettings implements PlayerSettings {

	private String local;
	private ChatMode chatmode = ChatMode.FULL;
	private boolean chatColors = true;
	private int viewDistance;
	private int mainHand;
	private int skinparts;
	private boolean allowServerListing;
	private boolean enableTextFiltering;
	
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
	public void setChatMode(ChatMode mode) {
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
	public ChatMode getChatMode() {
		return chatmode;
	}

	@Override
	public boolean getAllowServerListing() {
		return allowServerListing;
	}

	@Override
	public void setAllowServerListing(boolean allowServerListing) {
		this.allowServerListing = allowServerListing;
	}

	@Override
	public boolean hasTextFiltering() {
		return enableTextFiltering;
	}

	@Override
	public void setTextFiltering(boolean textFiltering) {
		this.enableTextFiltering = textFiltering;
	}

}
