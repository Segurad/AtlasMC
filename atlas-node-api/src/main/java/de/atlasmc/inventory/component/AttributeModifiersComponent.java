package de.atlasmc.inventory.component;

import de.atlasmc.NamespacedKey;
import de.atlasmc.attribute.Attributeable;

public interface AttributeModifiersComponent extends AbstractTooltipComponent, Attributeable {
	
	public static final NamespacedKey COMPONENT_KEY = NamespacedKey.literal("minecraft:attribute_modifiers");
	
	AttributeModifiersComponent clone();

}
