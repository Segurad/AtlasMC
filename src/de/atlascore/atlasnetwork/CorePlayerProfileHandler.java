package de.atlascore.atlasnetwork;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import de.atlasmc.atlasnetwork.AtlasPlayer;

public abstract class CorePlayerProfileHandler {
	
	private final Map<Integer, CoreAtlasPlayer> by_id;
	private final Map<String, CoreAtlasPlayer> by_name;
	private final Map<String, CoreAtlasPlayer> by_mojang_name;
	private final Map<UUID, CoreAtlasPlayer> by_mojang_uuid;
	
	public CorePlayerProfileHandler() {
		by_id = new ConcurrentHashMap<>();
		by_name = new ConcurrentHashMap<>();
		by_mojang_name = new ConcurrentHashMap<>();
		by_mojang_uuid = new ConcurrentHashMap<>();
	}
	
	public abstract AtlasPlayer getPlayer(String name);

	public abstract AtlasPlayer getPlayerByMojang(UUID uuid);

	public abstract AtlasPlayer getPlayerByMojang(String name);

	public abstract AtlasPlayer getPlayer(int id);
	
	protected abstract void save(CoreAtlasPlayer coreAtlasPlayer);
	
	protected synchronized void cache(CoreAtlasPlayer profile) {
		by_id.put(profile.getID(), profile);
		by_name.put(profile.getInternalName(), profile);
		by_mojang_name.put(profile.getMojangName(), profile);
		by_mojang_uuid.put(profile.getMojangUUID(), profile);
	}
	
	protected CoreAtlasPlayer getCached(String name) {
		return by_name.get(name);
	}
	
	protected CoreAtlasPlayer getCachedByMojang(String name) {
		return by_mojang_name.get(name);
	}
	
	protected CoreAtlasPlayer getCachedByMojang(UUID uuid) {
		return by_mojang_uuid.get(uuid);
	}
	
	protected CoreAtlasPlayer getCached(int id) {
		return by_id.get(id);
	}

	public void removeCached(CoreAtlasPlayer profile) {
		by_id.remove(profile.getID());
		by_name.remove(profile.getInternalName());
		by_mojang_name.remove(profile.getMojangName());
		by_mojang_uuid.remove(profile.getMojangUUID());
	}

}
