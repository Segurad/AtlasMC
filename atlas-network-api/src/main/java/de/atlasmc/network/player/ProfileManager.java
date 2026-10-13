package de.atlasmc.network.player;

import java.util.UUID;

import de.atlasmc.util.annotation.NotNull;
import de.atlasmc.util.annotation.Nullable;
import de.atlasmc.util.concurrent.future.Future;

public interface ProfileManager {
	
	/**
	 * Returns the player profile of a player with the internal name
	 * @param name
	 * @return player or null
	 */
	@Nullable
	AtlasPlayer getPlayer(String name);
	
	/**
	 * Returns the player profile of a player with the internal uuid
	 * @param uuid
	 * @return player or null
	 */
	@Nullable
	AtlasPlayer getPlayer(UUID uuid);
	
	/**
	 * Returns the player profile of a player with the mojang uuid
	 * @param uuid
	 * @return player or null
	 */
	@Nullable
	AtlasPlayer getPlayerByMojang(UUID uuid);
	
	/**
	 * Returns the player profile of a player with the mojang name
	 * @param name
	 * @param load if the profile should be loaded
	 * @return player or null
	 */
	@Nullable
	AtlasPlayer getPlayerByMojang(@NotNull String name);
	
	@NotNull
	Future<AtlasPlayer> loadPlayer(@NotNull String name);
	
	@NotNull
	Future<AtlasPlayer> loadPlayer(@NotNull UUID uuid);
	
	@NotNull
	Future<AtlasPlayer> loadPlayerByMojang(@NotNull String name);
	
	@NotNull
	Future<AtlasPlayer> loadPlayerByMojang(@NotNull UUID uuid);

}
