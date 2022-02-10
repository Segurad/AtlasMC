package de.atlasmc.attribute;

import de.atlasmc.util.map.Multimap;

public interface Attributeable {
	
	public void setAttributeModifiers(Multimap<Attribute, AttributeModifier> attributeModifiers);
	
	public boolean addAttributeModifier(Attribute attribute, AttributeModifier modifier);
	
	public boolean hasAttributeModifiers();

	public Multimap<Attribute, AttributeModifier> getAttributeModifiers();
	
	public boolean removeAttributeModifier(Attribute attribute);
	
	public boolean removeAttributeModifier(Attribute attribute, AttributeModifier modifier);

}
