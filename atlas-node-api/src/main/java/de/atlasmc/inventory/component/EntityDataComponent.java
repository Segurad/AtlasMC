package de.atlasmc.inventory.component;

import de.atlasmc.NamespacedKey;
import de.atlasmc.entity.Entity;
import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public interface EntityDataComponent extends ItemComponent {
	
	public static final NamespacedKey COMPONENT_KEY = NamespacedKey.literal("minecraft:entity_data");
	
	public static final NBTSerializationHandler<EntityDataComponent>
	NBT_HANDLER = NBTSerializationHandler
					.builder(EntityDataComponent.class)
					.typeComponentField(COMPONENT_KEY, EntityDataComponent::getEntity, EntityDataComponent::setEntity, Entity.NBT_HANDLER)
					.build();
	
	Entity getEntity();
	
	void setEntity(Entity entity);
	
	EntityDataComponent clone();
	
	@Override
	default NBTSerializationHandler<? extends EntityDataComponent> getNBTHandler() {
		return NBT_HANDLER;
	}

}
