package de.atlasmc.inventory.component;

import java.util.Set;

import de.atlasmc.NamespacedKey;
import de.atlasmc.entity.EntityType;
import de.atlasmc.inventory.EquipmentSlot;

public interface EquippableComponent extends ItemComponent {
	
	public static final NamespacedKey COMPONENT_KEY = NamespacedKey.literal("minecraft:equippable");
	
	EquipmentSlot getSlot();
	
	void setSlot(EquipmentSlot slot);
	
	NamespacedKey getEquipSound();
	
	void setEquipSound(NamespacedKey sound);
	
	float getEquipSoundRange();
	
	void setEquipSoundRange(float range);
	
	Set<EntityType> getAllowedEntities();
	
	boolean hasAllowedEntities();
	
	void addAllowedEntity(EntityType type);
	
	void removeAllowedEntity(EntityType type);
	
	boolean isDispensable();
	
	void setDispensable(boolean dispensable);
	
	boolean isSwappable();
	
	void setSwappable(boolean swappable);
	
	boolean isDamageOnHurt();
	
	void setDamageOnHurt(boolean damage);
	
	String getCameraOverlay();
	
	void setCameraOverlay(String overlay);
	
	EquippableComponent clone();

}
