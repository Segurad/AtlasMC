package de.atlascore.atlasnetwork;

import java.util.List;
import java.util.UUID;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import de.atlasmc.Atlas;
import de.atlasmc.atlasnetwork.NetworkInfo;
import de.atlasmc.chat.placeholder.PlaceholderHandler;

public class CoreNetworkInfo implements NetworkInfo {
	
	private final int slots;
	private final int slotDistance;
	private final SlotMode mode;
	private final String motd;
	private final String protocolText;
	private final String serverIcon;
	private final List<String> playerInfo;
	private final String[] playerInfoUUIDs;
	private final boolean maintenance;
	
	public CoreNetworkInfo(int slots, int slotDistance, SlotMode mode, String motd, String protocolText, String serverIcon, List<String> playerInfo, boolean maintenance) {
		this.slots = slots;
		this.slotDistance = slotDistance;
		this.mode = mode;
		this.motd = motd;
		this.protocolText = protocolText;
		this.serverIcon = "data:image/png;base64," + serverIcon;
		this.playerInfo = List.copyOf(playerInfo);
		playerInfoUUIDs = new String[playerInfo.size()];
		for (int i = 0; i < playerInfo.size(); i++) {
			playerInfoUUIDs[i] = UUID.randomUUID().toString();
		}
		this.maintenance = maintenance;
	}

	@Override
	public int getSlots() {
		return slots;
	}

	@Override
	public int getSlotDistance() {
		return slotDistance;
	}

	@Override
	public SlotMode getSlotMode() {
		return mode;
	}

	@Override
	public String getMotd() {
		return motd;
	}

	@Override
	public String getProtocolText() {
		return protocolText;
	}

	@Override
	public String getServerIcon() {
		return serverIcon;
	}

	@Override
	public List<String> getPlayerInfo() {
		return playerInfo;
	}

	@Override
	public String getStatusInfo(int protocol) {
		JsonObject statusInfo = new JsonObject();
		// Version
		JsonObject version = new JsonObject();
		statusInfo.add("version", version);
		version.addProperty("name", protocolText);
		version.addProperty("protocol", maintenance ? -1 : protocol);
		// Player
		JsonObject players = new JsonObject();
		statusInfo.add("players", players);
		int maxPlayers = 0, onlinePlayers = 0;
		switch (mode) {
		case NORMAL: 
			onlinePlayers = Atlas.getNetwork().getOnlinePlayerCount();
			maxPlayers = slots;
			break;
		case DYNAMIC: 
			onlinePlayers = Atlas.getNetwork().getOnlinePlayerCount();
			maxPlayers = onlinePlayers + slotDistance;
			break;
		case BASE:
			onlinePlayers = Atlas.getNetwork().getOnlinePlayerCount();
			maxPlayers = onlinePlayers + slotDistance;
			// TODO implement slotmode base
			break;
		case FIXED:
			onlinePlayers = slots;
			maxPlayers = slots + slotDistance;
			break;
		}
		players.addProperty("max", maxPlayers);
		players.addProperty("online", onlinePlayers);
		players.add("sample", createSample());
		// MOTD
		JsonObject motd = new JsonObject();
		motd.addProperty("text", PlaceholderHandler.setPlaceholders(this.motd, null));
		statusInfo.add("description", motd);
		// Favicon
		statusInfo.addProperty("favicon", serverIcon);
		return statusInfo.toString();
	}
	
	private JsonElement createSample() {
		JsonArray playerInfo = new JsonArray();
		int i = 0;
		for (String s : this.playerInfo) {
			JsonObject player = new JsonObject();
			playerInfo.add(player);
			player.addProperty("name", PlaceholderHandler.setPlaceholders(s, null));
			player.addProperty("id", playerInfoUUIDs[i]);
		}
		return playerInfo;
	}

}
