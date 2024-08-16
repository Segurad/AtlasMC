package de.atlasmc.master;

import java.util.UUID;

import de.atlasmc.atlasnetwork.AtlasPlayer;

public interface ProfileManager {

	default AtlasPlayer getPlayer(String name) {
		return getPlayer(name, true);
	}
	
	/**
	 * Returns the player profile of a player with the internal name
	 * @param name
	 * @param load if the profile should be loaded
	 * @return player or null
	 */
	AtlasPlayer getPlayer(String name, boolean load);
	
	default AtlasPlayer getPlayer(UUID uuid) {
		return getPlayer(uuid, true);
	}
	
	/**
	 * Returns the player profile of a player with the internal uuid
	 * @param uuid
	 * @param load if the profile should be loaded
	 * @return player or null
	 */
	AtlasPlayer getPlayer(UUID uuid, boolean load);
	
	default AtlasPlayer getPlayerByMojang(UUID uuid) {
		return getPlayerByMojang(uuid, true);
	}
	
	/**
	 * Returns the player profile of a player with the mojang uuid
	 * @param uuid
	 * @param load if the profile should be loaded
	 * @return player or null
	 */
	AtlasPlayer getPlayerByMojang(UUID uuid, boolean load);
	
	default AtlasPlayer getPlayerByMojang(String name) {
		return getPlayerByMojang(name, true);
	}
	
	/**
	 * Returns the player profile of a player with the mojang name
	 * @param name
	 * @param load if the profile should be loaded
	 * @return player or null
	 */
	AtlasPlayer getPlayerByMojang(String name, boolean load);
	
	default AtlasPlayer getPlayer(int id) {
		return getPlayer(id, true);
	}
	
	/**
	 * Return the player profile of a player with the internal id
	 * @param id
	 * @param load if the profile should be loaded
	 * @return player or null
	 */
	AtlasPlayer getPlayer(int id, boolean load);
	
}
