package de.atlascore.atlasnetwork;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.sql.Date;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import de.atlasmc.atlasnetwork.AtlasPlayer;
import de.atlasmc.atlasnetwork.ProfileHandler;
import de.atlasmc.cache.Cache;
import de.atlasmc.cache.CacheHolder;

public abstract class CorePlayerProfileHandler implements ProfileHandler, CacheHolder {
	
	private final Map<Integer, RefListener> by_id;
	private final Map<String, RefListener> by_name;
	private final Map<String, RefListener> by_mojang_name;
	private final Map<UUID, RefListener> by_mojang_uuid;
	private final Map<UUID, RefListener> by_uuid;
	private final ReferenceQueue<AtlasPlayer> queue;
	
	public CorePlayerProfileHandler() {
		by_id = new ConcurrentHashMap<>();
		by_name = new ConcurrentHashMap<>();
		by_mojang_name = new ConcurrentHashMap<>();
		by_mojang_uuid = new ConcurrentHashMap<>();
		by_uuid = new ConcurrentHashMap<>();
		queue = new ReferenceQueue<>();
		Cache.register(this);
	}
	
	private <T> AtlasPlayer internalGet(Map<T, RefListener> map, T key) {
		Reference<AtlasPlayer> ref = map.get(key);
		if (ref != null && !ref.refersTo(null)) {
			return ref.get();
		}
		return null;
	}
	
	public AtlasPlayer getPlayer(String name, boolean load) {
		AtlasPlayer player = internalGet(by_name, name);
		if (player != null)
			return player;
		if (!load)
			return null;
		synchronized (this) {
			player = internalGet(by_name, name);
			if (player != null)
				return player;
			player = loadPlayer(name);
			if (player == null)
				return null;
			cache(player);
			return player;
		}
	}
	
	@Override
	public AtlasPlayer getPlayer(UUID uuid, boolean load) {
		AtlasPlayer player = internalGet(by_uuid, uuid);
		if (player != null)
			return player;
		if (!load)
			return null;
		synchronized (this) {
			player = internalGet(by_uuid, uuid);
			if (player != null)
				return player;
			player = loadPlayer(uuid);
			if (player == null)
				return null;
			cache(player);
			return player;
		}
	}

	@Override
	public AtlasPlayer getPlayerByMojang(UUID uuid, boolean load) {
		AtlasPlayer player = internalGet(by_mojang_uuid, uuid);
		if (player != null)
			return player;
		if (!load)
			return null;
		synchronized (this) {
			player = internalGet(by_mojang_uuid, uuid);
			if (player != null)
				return player;
			player = loadPlayerByMojang(uuid);
			if (player == null)
				return null;
			cache(player);
			return player;
		}
	}

	@Override
	public AtlasPlayer getPlayerByMojang(String name, boolean load) {
		AtlasPlayer player = internalGet(by_mojang_name, name);
		if (player != null)
			return player;
		if (!load)
			return null;
		synchronized (this) {
			player = internalGet(by_mojang_name, name);
			if (player != null)
				return player;
			player = loadPlayerByMojang(name);
			if (player == null)
				return null;
			cache(player);
			return player;
		}

	}

	@Override
	public AtlasPlayer getPlayer(int id, boolean load) {
		final Integer key = id;
		AtlasPlayer player = internalGet(by_id, key);
		if (player != null)
			return player;
		if (!load)
			return null;
		synchronized (this) {
			player = internalGet(by_id, key);
			if (player != null)
				return player;
			player = loadPlayer(id);
			if (player == null)
				return null;
			cache(player);
			return player;
		}
	}

	protected abstract AtlasPlayer loadPlayer(String name);
	
	protected abstract AtlasPlayer loadPlayer(UUID uuid);
	
	protected abstract AtlasPlayer loadPlayer(int id);
	
	protected abstract AtlasPlayer loadPlayerByMojang(String name);
	
	protected abstract AtlasPlayer loadPlayerByMojang(UUID uuid);
	
	private synchronized void cache(AtlasPlayer profile) {
		RefListener ref = new RefListener(profile, queue);
		by_id.put(profile.getID(), ref);
		by_name.put(profile.getInternalName(), ref);
		by_uuid.put(profile.getInternalUUID(), ref);
		by_mojang_name.put(profile.getMojangName(), ref);
		by_mojang_uuid.put(profile.getMojangUUID(), ref);
	}

	@Override
	public synchronized void cleanUp() {
		RefListener ref = null;
		while ((ref = (RefListener) queue.poll()) != null) {
			by_id.remove(ref.id, ref);
			by_name.remove(ref.name, ref);
			by_mojang_name.remove(ref.mojangName, ref);
			by_mojang_uuid.remove(ref.mojangUUID, ref);
			by_uuid.remove(ref.uuid, ref);
		}	
	}
	
	private static class RefListener extends WeakReference<AtlasPlayer> {

		final int id;
		final String name;
		final UUID uuid;
		final String mojangName;
		final UUID mojangUUID;
		
		public RefListener(AtlasPlayer referent, ReferenceQueue<? super AtlasPlayer> q) {
			super(referent, q);
			this.id = referent.getID();
			this.name = referent.getInternalName();
			this.uuid = referent.getInternalUUID();
			this.mojangName = referent.getMojangName();
			this.mojangUUID = referent.getMojangUUID();
		}
		
	}

	protected abstract void updateLastJoind(AtlasPlayer player, Date date);

	protected abstract void updateInternalUUID(AtlasPlayer player, UUID uuid);

	protected abstract void updateInternalName(AtlasPlayer player, String name);
	
	public AtlasPlayer createPlayer(UUID mojangUUID, UUID internalUUID, String mojangName, String internalName, Date firstJoin) {
		AtlasPlayer player = internalCreatePlayer(mojangUUID, internalUUID, mojangName, internalName, firstJoin);
		if (player != null)
			cache(player);
		return player;
	}
	
	protected abstract AtlasPlayer internalCreatePlayer(UUID mojangUUID, UUID internalUUID, String mojangName, String internalName, Date firstJoin);

}
