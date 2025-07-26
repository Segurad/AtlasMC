package de.atlasmc.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import de.atlasmc.tick.Tickable;

public class CooldownHandler<K> implements Tickable {

	private final Map<K, Info<K>> cooldowns;
	private int currentTick;
	
	public CooldownHandler() {
		this.cooldowns = createMap();
	}
	
	protected Map<K, Info<K>> createMap() {
		return new HashMap<>();
	}
	
	/**
	 * Sets a cooldown for the given key in ticks
	 * @param key the key to use
	 * @param ticks cool down
	 */
	public void setCooldown(K key, int ticks) {
		if (key == null)
			return;
		if (ticks < 0)
			return;
		Info<K> info = cooldowns.put(key, new Info<>(key, currentTick, currentTick+ticks));
		onSetCooldown(key, ticks, info != null ? info.endTick-currentTick : 0);
	}
	
	/**
	 * Removes the key from this handler and returns the remaining ticks
	 * @param key
	 * @return ticks until expiration
	 */
	public int removeCooldown(K key) {
		if (key == null)
			return 0;
		Info<K> info = cooldowns.remove(key);
		int ticksLeft = info != null ? info.endTick-currentTick : 0;
		onRemoveCooldown(key, ticksLeft);
		return ticksLeft;
	}
	
	/**
	 * Returns the remaining ticks of the given key
	 * @param key
	 * @return ticks
	 */
	public int getCooldown(K key) {
		if (key == null)
			return 0;
		Info<K> info = cooldowns.get(key);
		return info != null ? info.endTick-currentTick : 0;
	}
	
	/**
	 * Returns the ticks past in for the given key
	 * @param key
	 * @return ticks past or -1 if not present
	 */
	public int getCooldownPast(K key) {
		if (key == null)
			return -1;
		Info<K> info = cooldowns.get(key);
		return info != null ? currentTick-info.startTick : -1;
	}
	
	/**
	 * Returns whether or not the given key has a cool down
	 * @param key
	 * @return true if cool down
	 */
	public boolean hasCooldown(K key) {
		return getCooldown(key) > 0;
	}
	
	@Override
	public void tick() {
		currentTick++;
		if (cooldowns.isEmpty())
			return;
		for (Iterator<Info<K>> it = cooldowns.values().iterator(); it.hasNext();) {
			Info<K> info = it.next();
			if (info.endTick > currentTick)
				continue;
			it.remove();
			onRemoveCooldown(info.key, 0);
		}
	}
	
	public void clear() {
		internalClear(false);
	}
	
	protected void internalClear(boolean informClear) {
		if (informClear)
			onClear(cooldowns.keySet());
		cooldowns.clear();
	}
	
	/**
	 * Called when a Key is added to this handler
	 * @param key the added key
	 * @param ticks cool down
	 * @param previousTicks tick remaining until expiration if the key was present in this handler 
	 */
	protected void onSetCooldown(K key, int ticks, int previousTicks) {}
	
	/**
	 * Called when a Key is removed from this handler
	 * @param key the removed key
	 * @param ticks remaining until expiration
	 */
	protected void onRemoveCooldown(K key, int ticks) {}
	
	protected void onClear(Set<K> keys) {
		// override as needed
	}
	
	private static class Info<K> {
		private final int startTick;
		private final int endTick;
		private final K key;
		
		public Info(K key, int start, int end) {
			this.key = key;
			this.startTick = start;
			this.endTick = end;
		}
		
	}

}
