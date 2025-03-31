package de.atlasmc.atlasnetwork;

import java.util.UUID;

import de.atlasmc.util.concurrent.future.Future;

public interface ProfileHandler {
	
	/**
	 * Returns the player profile of a player with the internal name
	 * @param name
	 * @return player or null
	 */
	AtlasPlayer getPlayer(String name);
	
	/**
	 * Returns the player profile of a player with the internal uuid
	 * @param uuid
	 * @return player or null
	 */
	AtlasPlayer getPlayer(UUID uuid);
	
	/**
	 * Returns the player profile of a player with the mojang uuid
	 * @param uuid
	 * @return player or null
	 */
	AtlasPlayer getPlayerByMojang(UUID uuid);
	
	/**
	 * Returns the player profile of a player with the mojang name
	 * @param name
	 * @param load if the profile should be loaded
	 * @return player or null
	 */
	AtlasPlayer getPlayerByMojang(String name);
	
	Future<AtlasPlayer> loadPlayer(String name);
	
	Future<AtlasPlayer> loadPlayer(UUID uuid);
	
	Future<AtlasPlayer> loadPlayerByMojang(String name);
	
	Future<AtlasPlayer> loadPlayerByMojang(UUID uuid);

}
