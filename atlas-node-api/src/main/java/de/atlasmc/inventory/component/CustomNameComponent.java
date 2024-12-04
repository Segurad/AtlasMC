package de.atlasmc.inventory.component;

import de.atlasmc.NamespacedKey;
import de.atlasmc.chat.Chat;

public interface CustomNameComponent extends ItemComponent {
	
	public static NamespacedKey COMPONENT_KEY = NamespacedKey.literal("minecraft:custom_name");
	
	Chat getCustomName();
	
	void setCustomName(Chat chat);
	
	CustomNameComponent clone();

}
