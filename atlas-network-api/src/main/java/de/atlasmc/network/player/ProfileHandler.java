package de.atlasmc.network.player;

import java.util.UUID;

import de.atlasmc.util.annotation.NotNull;
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
	AtlasPlayer getPlayerByMojang(@NotNull String name);
	
	Future<AtlasPlayer> loadPlayer(@NotNull String name);
	
	Future<AtlasPlayer> loadPlayer(@NotNull UUID uuid);
	
	Future<AtlasPlayer> loadPlayerByMojang(@NotNull String name);
	
	Future<AtlasPlayer> loadPlayerByMojang(@NotNull UUID uuid);

}
