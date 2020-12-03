package de.atlasmc.potion;

public final class PotionEffectManger { /* implements Listener {
	
	private final HashMap<LivingEntity, HashMap<PotionEffectType, List<EffectConfig>>> map = new HashMap<LivingEntity, HashMap<PotionEffectType, List<EffectConfig>>>();
	private boolean enable = false;
	
	
	public void removePotionEffect(LivingEntity ent, PotionEffectType effect, boolean all) {
		if (map.containsKey(ent) && all) {
			map.get(ent).remove(effect);
			if (map.get(ent).isEmpty()) map.remove(ent);
		}
		ent.removePotionEffect(effect);
	}
	
	public void removeAllPotionEffect(LivingEntity ent) {
		map.remove(ent);
		for (PotionEffect eftype : ent.getActivePotionEffects()) {
			ent.removePotionEffect(eftype.getType());
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Player> getPlayers() {
		return new ArrayList<Player>((Collection<? extends Player>) map.keySet());
	}
	
	public boolean contains(Player player) {
		return map.containsKey(player);
	}
	
	public boolean isEnable() {
		return enable;
	}
	
	public void setEnable(boolean value) {
		enable = value;
	}
	
	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onPotionEffect(EntityPotionEffectEvent e) {
		if (!enable) return;
		LivingEntity ent = (LivingEntity) e.getEntity();
		if (!(ent instanceof Player)) return;
		Action ac = e.getAction();
		if (ac == Action.CHANGED) {
			PotionEffect nef = e.getNewEffect();
			PotionEffect oef = e.getOldEffect();
			if (oef.getDuration() < 1) return;
			if (nef.getAmplifier() == oef.getAmplifier()) return;
			PotionEffectType t = oef.getType();
			HashMap<PotionEffectType, List<EffectConfig>> efmap;
			if (map.containsKey(ent)) 
				efmap = map.get(ent); else {
					efmap = new HashMap<PotionEffectType, List<EffectConfig>>();
					map.put(ent, efmap);
				}
			if (efmap.containsKey(t)) {
				efmap.get(t).add(new EffectConfig(oef));
			} else efmap.put(t, Arrays.asList(new EffectConfig(oef)));
		} else if (ac == Action.REMOVED) {
			if (!map.containsKey(ent)) return;
			PotionEffect oef = e.getOldEffect();
			PotionEffectType t = oef.getType();
			HashMap<PotionEffectType, List<EffectConfig>> efmap = map.get(ent);
			if (efmap.containsKey(t)) {
				List<EffectConfig> list = efmap.get(t);
				EffectConfig max = null;
				for (EffectConfig ef : new ArrayList<>(list)) {
					int dur = ef.dur - (int) ((System.currentTimeMillis() - ef.ts) / 50);
					if (dur < 1) {
						list.remove(ef);
						continue;
					}
					if (max != null) {
						if (max.amp > ef.amp) continue;
						max = ef;
					} else {
						max = ef;
					}
				}
				list.remove(max);
				if (list.isEmpty()) efmap.remove(t);
				if (efmap.isEmpty()) map.remove(ent);
				ent.addPotionEffect(new PotionEffect(max.ef, max.dur, max.amp));
			}
		} else if (ac == Action.CLEARED) {
			map.remove(ent);
		}
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		map.remove(e.getPlayer());
	}
	
	private final class EffectConfig {
		final long ts;
		final PotionEffectType ef;
		int dur, amp;
		
		public EffectConfig(PotionEffect ef) {
			this.ef = ef.getType();
			dur = ef.getDuration();
			amp = ef.getAmplifier();
			ts = System.currentTimeMillis();
		}
	}
	
	public void gc() {
		for (Player p : getPlayers()) {
			if (!p.isOnline()) { map.remove(p); continue; }
			HashMap<PotionEffectType, List<EffectConfig>> efmap = map.get(p);
			if (efmap.isEmpty()) { map.remove(p); continue; }
			for (PotionEffectType t : efmap.keySet()) {
				List<EffectConfig> efl = efmap.get(t);
				for (EffectConfig ef : new ArrayList<EffectConfig>(efl)) {
					int dur = ef.dur - (int) ((System.currentTimeMillis() - ef.ts) / 50);
					if (dur < 1) efl.remove(ef);
				}
				if (efl.isEmpty()) efmap.remove(t);
			}
			if (efmap.isEmpty()) map.remove(p);
		}
	}
	*/
}
