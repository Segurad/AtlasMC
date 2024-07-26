package de.atlascore.atlasnetwork;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import de.atlasmc.atlasnetwork.NetworkInfo;
import de.atlasmc.chat.placeholder.PlaceholderHandler;
import de.atlasmc.util.configuration.ConfigurationSection;
import de.atlasmc.util.configuration.MemoryConfigurationSection;
import de.atlasmc.util.configuration.file.JsonConfiguration;

public class CoreNetworkInfo implements NetworkInfo {
	
	private final int slots;
	private final int slotDistance;
	private volatile int onlinePlayerCount;
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
	public JsonConfiguration getStatusInfo(int protocol) {
		JsonConfiguration statusInfo = new JsonConfiguration();
		// Version
		ConfigurationSection version = statusInfo.createSection("version");
		version.set("name", protocolText);
		version.set("protocol", maintenance ? -1 : protocol);
		// Player
		ConfigurationSection players = statusInfo.createSection("players");
		int maxPlayers = 0, onlinePlayers = 0;
		switch (mode) {
		case NORMAL: 
			onlinePlayers = onlinePlayerCount;
			maxPlayers = slots;
			break;
		case DYNAMIC: 
			onlinePlayers = onlinePlayerCount;
			maxPlayers = onlinePlayers + slotDistance;
			break;
		case BASE:
			onlinePlayers = onlinePlayerCount;
			maxPlayers = onlinePlayers + slotDistance;
			// TODO implement slotmode base
			break;
		case FIXED:
			onlinePlayers = slots;
			maxPlayers = slots + slotDistance;
			break;
		}
		players.set("max", maxPlayers);
		players.set("online", onlinePlayers);
		buildSample(statusInfo);
		// MOTD
		ConfigurationSection motd = statusInfo.createSection("description");
		motd.set("text", PlaceholderHandler.setPlaceholders(this.motd, null));
		// Favicon
		statusInfo.set("favicon", serverIcon);
		return statusInfo;
	}
	
	private void buildSample(ConfigurationSection parent) {
		ArrayList<ConfigurationSection> sample = new ArrayList<>();
		parent.set("sample", sample);
		int i = 0;
		for (String s : this.playerInfo) {
			ConfigurationSection section = new MemoryConfigurationSection(parent);
			sample.add(section);
			section.set("name", PlaceholderHandler.setPlaceholders(s, null));
			section.set("id", playerInfoUUIDs[i++]);
		}
	}

	public void setOnlinePlayerCount(int onlinePlayerCount) {
		this.onlinePlayerCount = onlinePlayerCount;
	}

}
