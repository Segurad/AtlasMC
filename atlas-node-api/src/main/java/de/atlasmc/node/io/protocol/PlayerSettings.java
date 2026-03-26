package de.atlasmc.node.io.protocol;

import java.util.Objects;

import de.atlasmc.chat.ChatMode;
import de.atlasmc.node.inventory.MainHand;
import de.atlasmc.util.annotation.InternalAPI;
import de.atlasmc.util.annotation.NotNull;

/**
 * Stores some client side settings of the player
 */
public class PlayerSettings {
	
	public static final int
	SHOW_CAPE = 0x01,
	SHOW_JACKED = 0x02,
	SHOW_LEFT_SLEEVE = 0x04,
	SHOW_RIGHT_SLEEVE = 0x08,
	SHOW_LEFT_LEG = 0x10,
	SHOW_RIGHT = 0x20,
	SHOW_HAT = 0x40;
	
	private String local;
	private ChatMode chatmode = ChatMode.FULL;
	private boolean chatColors = true;
	private int viewDistance;
	private MainHand mainHand = MainHand.RIGHT;
	private int skinparts;
	private boolean allowServerListing;
	private boolean enableTextFiltering;
	
	public String getLocal() {
		return local;
	}

	@InternalAPI
	public void setLocal(String local) {
		this.local = local;
	}

	public int getViewDistance() {
		return viewDistance;
	}

	@InternalAPI
	public void setViewDistance(int distance) {
		this.viewDistance = distance;
	}

	@NotNull
	public MainHand getMainHand() {
		return mainHand;
	}

	@InternalAPI
	public void setMainHand(MainHand mainHand) {
		this.mainHand = Objects.requireNonNull(mainHand);
	}

	@InternalAPI
	public void setSkinParts(int skinparts) {
		this.skinparts = skinparts;
	}

	public int getSkinParts() {
		return skinparts;
	}

	@InternalAPI
	public void setChatMode(ChatMode mode) {
		this.chatmode = Objects.requireNonNull(mode);
	}

	@InternalAPI
	public void setChatColor(boolean color) {
		this.chatColors = color;
	}

	public boolean getChatColor() {
		return chatColors;
	}

	@NotNull
	public ChatMode getChatMode() {
		return chatmode;
	}

	public boolean getAllowServerListing() {
		return allowServerListing;
	}

	@InternalAPI
	public void setAllowServerListing(boolean allowServerListing) {
		this.allowServerListing = allowServerListing;
	}

	public boolean hasTextFiltering() {
		return enableTextFiltering;
	}

	@InternalAPI
	public void setTextFiltering(boolean textFiltering) {
		this.enableTextFiltering = textFiltering;
	}

	
}
