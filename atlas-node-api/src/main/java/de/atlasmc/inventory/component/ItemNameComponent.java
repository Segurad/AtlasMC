package de.atlasmc.inventory.component;

import de.atlasmc.NamespacedKey;
import de.atlasmc.chat.Chat;

public interface ItemNameComponent extends ItemComponent {
	
	public static final NamespacedKey COMPONENT_KEY = NamespacedKey.literal("minecraft:item_name");
	
	Chat getName();
	
	void setName(Chat name);
	
	ItemNameComponent clone();

}
