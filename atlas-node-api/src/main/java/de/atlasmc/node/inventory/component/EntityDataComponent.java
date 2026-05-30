package de.atlasmc.node.inventory.component;

import de.atlasmc.io.codec.StreamCodec;
import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.node.entity.Entity;
import de.atlasmc.util.annotation.NotNull;

public interface EntityDataComponent extends ItemComponent {
	
	@NotNull
	public static final NBTCodec<EntityDataComponent>
	NBT_CODEC = NBTCodec
					.builder(EntityDataComponent.class)
					.include(ItemComponent.NBT_CODEC)
					.codec(ComponentType.ENTITY_DATA.getNamespacedKey(), EntityDataComponent::getEntity, EntityDataComponent::setEntity, Entity.NBT_CODEC)
					.build();
	
	@NotNull
	public static final StreamCodec<EntityDataComponent>
	STREAM_CODEC = StreamCodec
					.builder(EntityDataComponent.class)
					.include(ItemComponent.STREAM_CODEC)
					.codec(EntityDataComponent::getEntity, EntityDataComponent::setEntity, Entity.NBT_CODEC)
					.build();
	
	Entity getEntity();
	
	void setEntity(Entity entity);
	
	@Override
	EntityDataComponent clone();
	
	@Override
	default NBTCodec<? extends EntityDataComponent> getNBTCodec() {
		return NBT_CODEC;
	}
	
	@Override
	default StreamCodec<? extends EntityDataComponent> getStreamCodec() {
		return STREAM_CODEC;
	}

}
