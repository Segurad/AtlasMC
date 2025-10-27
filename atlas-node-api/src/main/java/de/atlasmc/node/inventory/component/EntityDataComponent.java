package de.atlasmc.node.inventory.component;

import de.atlasmc.io.codec.StreamCodec;
import de.atlasmc.node.entity.Entity;
import de.atlasmc.util.nbt.codec.NBTCodec;

public interface EntityDataComponent extends ItemComponent {
	
	public static final NBTCodec<EntityDataComponent>
	NBT_HANDLER = NBTCodec
					.builder(EntityDataComponent.class)
					.typeCompoundField(ComponentType.ENTITY_DATA.getNamespacedKey(), EntityDataComponent::getEntity, EntityDataComponent::setEntity, Entity.NBT_CODEC)
					.build();
	
	public static final StreamCodec<EntityDataComponent>
	STREAM_CODEC = StreamCodec
					.builder(EntityDataComponent.class)
					.include(ItemComponent.STREAM_CODEC)
					.codec(EntityDataComponent::getEntity, EntityDataComponent::setEntity, Entity.NBT_CODEC)
					.build();
	
	Entity getEntity();
	
	void setEntity(Entity entity);
	
	EntityDataComponent clone();
	
	@Override
	default NBTCodec<? extends EntityDataComponent> getNBTCodec() {
		return NBT_HANDLER;
	}
	
	@Override
	default StreamCodec<? extends EntityDataComponent> getStreamCodec() {
		return STREAM_CODEC;
	}

}
