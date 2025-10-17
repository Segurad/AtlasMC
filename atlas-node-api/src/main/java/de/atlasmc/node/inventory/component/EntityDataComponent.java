package de.atlasmc.node.inventory.component;

import de.atlasmc.node.entity.Entity;
import de.atlasmc.util.nbt.codec.NBTCodec;

public interface EntityDataComponent extends ItemComponent {
	
	public static final NBTCodec<EntityDataComponent>
	NBT_HANDLER = NBTCodec
					.builder(EntityDataComponent.class)
					.typeCompoundField(ComponentType.ENTITY_DATA.getNamespacedKey(), EntityDataComponent::getEntity, EntityDataComponent::setEntity, Entity.NBT_HANDLER)
					.build();
	
	Entity getEntity();
	
	void setEntity(Entity entity);
	
	EntityDataComponent clone();
	
	@Override
	default NBTCodec<? extends EntityDataComponent> getNBTCodec() {
		return NBT_HANDLER;
	}

}
