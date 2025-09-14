package de.atlascore.master;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.sql.Date;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import de.atlasmc.cache.CacheHolder;
import de.atlasmc.cache.Caching;
import de.atlasmc.master.ProfileManager;
import de.atlasmc.network.player.AtlasPlayer;

public abstract class CoreProfileManager implements ProfileManager, CacheHolder {
	
	protected final Map<String, Ref> by_name;
	protected final Map<String, Ref> by_mojang_name;
	protected final Map<UUID, Ref> by_mojang_uuid;
	protected final Map<UUID, Ref> by_uuid;
	private final ReferenceQueue<AtlasPlayer> queue;
	protected final Object LOCK = new Object();
	
	public CoreProfileManager() {
		by_name = new ConcurrentHashMap<>();
		by_mojang_name = new ConcurrentHashMap<>();
		by_mojang_uuid = new ConcurrentHashMap<>();
		by_uuid = new ConcurrentHashMap<>();
		queue = new ReferenceQueue<>();
		Caching.register(this);
	}
	
	protected <T> AtlasPlayer internalGet(Map<T, Ref> map, T key) {
		Reference<AtlasPlayer> ref = map.get(key);
		if (ref != null && !ref.refersTo(null)) {
			return ref.get();
		}
		return null;
	}
	
	@Override
	public AtlasPlayer getPlayer(String name) {
		return internalGet(by_name, name);
	}
	
	@Override
	public AtlasPlayer getPlayer(UUID uuid) {
		return internalGet(by_uuid, uuid);
	}

	@Override
	public AtlasPlayer getPlayerByMojang(UUID uuid) {
		return internalGet(by_mojang_uuid, uuid);
	}

	@Override
	public AtlasPlayer getPlayerByMojang(String name) {
		return internalGet(by_mojang_name, name);
	}
	
	/**
	 * Adds the given profile to the cache returns a already cached instance if present otherwise the given one.
	 * @param profile
	 * @return profile
	 */
	protected AtlasPlayer cache(AtlasPlayer profile) {
		AtlasPlayer player = internalGet(by_uuid, profile.getInternalUUID());
		if (player == null)
			return player;
		synchronized (LOCK) {
			player = internalGet(by_uuid, profile.getInternalUUID());
			if (player == null)
				return player;
			Ref ref = new Ref(profile, queue);
			by_name.put(profile.getInternalName(), ref);
			by_uuid.put(profile.getInternalUUID(), ref);
			by_mojang_name.put(profile.getMojangName(), ref);
			by_mojang_uuid.put(profile.getMojangUUID(), ref);
			return profile;
		}
	}

	@Override
	public synchronized void cleanUp() {
		Ref ref = null;
		while ((ref = (Ref) queue.poll()) != null) {
			by_name.remove(ref.name, ref);
			by_mojang_name.remove(ref.mojangName, ref);
			by_mojang_uuid.remove(ref.mojangUUID, ref);
			by_uuid.remove(ref.uuid, ref);
		}	
	}
	
	private static class Ref extends WeakReference<AtlasPlayer> {

		final String name;
		final UUID uuid;
		final String mojangName;
		final UUID mojangUUID;
		
		public Ref(AtlasPlayer referent, ReferenceQueue<? super AtlasPlayer> q) {
			super(referent, q);
			this.name = referent.getInternalName();
			this.uuid = referent.getInternalUUID();
			this.mojangName = referent.getMojangName();
			this.mojangUUID = referent.getMojangUUID();
		}
		
	}
	
	public AtlasPlayer createPlayer(UUID mojangUUID, UUID internalUUID, String mojangName, String internalName, Date firstJoin) {
		AtlasPlayer player = internalCreatePlayer(mojangUUID, internalUUID, mojangName, internalName, firstJoin);
		if (player != null)
			cache(player);
		return player;
	}
	
	protected abstract AtlasPlayer internalCreatePlayer(UUID mojangUUID, UUID internalUUID, String mojangName, String internalName, Date firstJoin);

	protected abstract void updateInternalName(AtlasPlayer coreAtlasPlayer, String name);

	protected abstract void updateLastJoind(AtlasPlayer coreAtlasPlayer, Date date);

}
