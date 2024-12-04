package de.atlasmc.attribute;

import java.util.List;

import de.atlasmc.inventory.EquipmentSlot;
import de.atlasmc.util.map.Multimap;

public interface Attributeable {
	
	void setAttributeModifiers(Multimap<Attribute, AttributeModifier> attributeModifiers);
	
	boolean addAttributeModifier(Attribute attribute, AttributeModifier modifier);
	
	boolean hasAttributeModifiers();

	Multimap<Attribute, AttributeModifier> getAttributeModifiers();
	
	boolean removeAttributeModifier(Attribute attribute);
	
	boolean removeAttributeModifier(Attribute attribute, AttributeModifier modifier);
	
	List<AttributeModifier> getAttributeModifiers(Attribute attribute);

	boolean removeAttributeModifier(EquipmentSlot slot);

}
