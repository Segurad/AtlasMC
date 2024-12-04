package de.atlasmc.inventory.component.effect;

import java.util.List;
import java.util.UUID;

import de.atlasmc.potion.PotionEffectType;

public interface RemoveEffect extends ComponentEffect {

	List<PotionEffectType> getEffectTypes();
	
	boolean hasEffectTypes();
	
	void addEffectType(PotionEffectType type);
	
	void removeEffectType(PotionEffectType type);
	
	List<UUID> getEffectUUIDs();
	
	boolean hasEffectUUIDs();
	
	void addEffectUUID(UUID uuid);
	
	void removeEffectUUID(UUID uuid);
	
	@Override
	default ConsumeEffectType getType() {
		return ConsumeEffectType.REMOVE_EFFECTS;
	}
	
}
