package de.atlasmc.inventory.component;

import java.util.List;

import de.atlasmc.Color;
import de.atlasmc.NamespacedKey;
import de.atlasmc.chat.Chat;
import de.atlasmc.potion.PotionData;
import de.atlasmc.potion.PotionEffect;

public interface PotionContentsComponent extends ItemComponent {
	
	public static final NamespacedKey COMPONENT_KEY = NamespacedKey.literal("minecraft:potion_contents");
	
	PotionData getPotionData();
	
	void setPotionData(PotionData data);
	
	Color getCustomColor();
	
	void setCustomColor(Color color);
	
	Chat getCustomName();
	
	void setCustomName(Chat name);
	
	List<PotionEffect> getEffects();
	
	boolean hasEffects();
	
	void addEffect(PotionEffect effect);
	
	void removeEffect(PotionEffect effect);
	
	PotionContentsComponent clone();

}
