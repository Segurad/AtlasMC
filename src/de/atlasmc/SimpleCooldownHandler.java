package de.atlasmc;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public final class SimpleCooldownHandler {

	private final HashMap<Object, Long> entries = new HashMap<Object, Long>();
	
	public void add(Object entry) {
		entries.put(entry, 0l);
	}
	
	public void remove(Object entry) {
		entries.remove(entry);
	}
	
	public boolean isOnCoolDown(Object entry) {
		if (entries.containsKey(entry)) {
			if (entries.get(entry) - System.currentTimeMillis() > 0) return true;
		}
		return false;
	}
	
	public boolean contains(Object entry) {
		return entries.containsKey(entry);
	}
	
	public void setCoolDown(Object entry, long millis) {
		entries.put(entry, System.currentTimeMillis() + millis);
	}
	
	public void removeCoolDown(Object entry) {
		entries.put(entry, 0l);
	}
	
	public long getRemainingCooldown(Object entry) {
		if (entries.containsKey(entry)) {
			long l = entries.get(entry) - System.currentTimeMillis();
			if (l > 0) {
				return l;
			}
		}
		return 0l;
	}
	
	public double getRemainingCooldownSec(Object entry) {
		if (entries.containsKey(entry)) {
			double l = entries.get(entry) - System.currentTimeMillis();
			if (l > 0) {
				l /= 1000;
				return Double.parseDouble(new DecimalFormat("0.0").format(l).replace(",", "."));
			}
		}
		return 0.0;
	}

	public void removeAll() {
		entries.clear();
	}
	
	public Set<Object> getEntries() {
		return new HashSet<>(entries.keySet());
	}
}
