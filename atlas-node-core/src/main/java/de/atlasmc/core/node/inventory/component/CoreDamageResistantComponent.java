package de.atlasmc.core.node.inventory.component;

import de.atlasmc.node.entity.DamageType;
import de.atlasmc.node.inventory.component.AbstractItemComponent;
import de.atlasmc.node.inventory.component.ComponentType;
import de.atlasmc.node.inventory.component.DamageResistantComponent;
import de.atlasmc.tag.TagKey;

public class CoreDamageResistantComponent extends AbstractItemComponent implements DamageResistantComponent {
	
	private TagKey<DamageType> types;
	
	public CoreDamageResistantComponent(ComponentType type) {
		super(type);
	}
	
	@Override
	public CoreDamageResistantComponent clone() {
		return (CoreDamageResistantComponent) super.clone();
	}

	@Override
	public TagKey<DamageType> getDamageTypes() {
		return types;
	}

	@Override
	public void setDamageTypes(TagKey<DamageType> types) {
		this.types = types;
	}

}
