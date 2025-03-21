package de.atlasmc.inventory.component;

import de.atlasmc.NamespacedKey;
import de.atlasmc.entity.EntityType;
import de.atlasmc.inventory.EquipmentSlot;
import de.atlasmc.sound.Sound;
import de.atlasmc.util.dataset.DataSet;

public interface EquippableComponent extends ItemComponent {
	
	public static final NamespacedKey COMPONENT_KEY = NamespacedKey.literal("minecraft:equippable");
	
	EquipmentSlot getSlot();
	
	void setSlot(EquipmentSlot slot);
	
	Sound getEquipSound();
	
	void setEquipSound(Sound sound);
	
	DataSet<EntityType> getAllowedEntities();
	
	boolean hasAllowedEntities();
	
	void setAllowedEntities(DataSet<EntityType> entities);
	
	boolean isDispensable();
	
	void setDispensable(boolean dispensable);
	
	boolean isSwappable();
	
	void setSwappable(boolean swappable);
	
	boolean isDamageOnHurt();
	
	void setDamageOnHurt(boolean damage);
	
	NamespacedKey getCameraOverlay();
	
	void setCameraOverlay(NamespacedKey overlay);
	
	NamespacedKey getAssetID();
	
	void setAssetID(NamespacedKey assetID);
	
	EquippableComponent clone();

}
