package de.atlasmc.node.entity.component;

import de.atlasmc.component.AbstractHolderBoundComponent;
import de.atlasmc.component.ComponentType;
import de.atlasmc.io.metadata.MetaDataContainer;
import de.atlasmc.io.metadata.MetaDataField;
import de.atlasmc.io.metadata.MetaDataFieldProvider;
import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.node.entity.Entity;
import de.atlasmc.node.entity.EntityMetaTypes;
import de.atlasmc.util.annotation.NotNull;

public class CamelMetaComponent extends AbstractHolderBoundComponent<Entity> implements MetaDataFieldProvider {

	@NotNull
	public static final NBTCodec<CamelMetaComponent>
	NBT_HANDLER = NBTCodec
					.builder(CamelMetaComponent.class)
					.include(AbstractHolderBoundComponent.NBT_CODEC)
					.longField("LastPoseTick", CamelMetaComponent::getLastPoseTick, CamelMetaComponent::setLastPoseTick)
					// non standard by atlas
					.boolField("IsDashing", CamelMetaComponent::isDashing, CamelMetaComponent::setDashing, false)
					.build();
	
	public static final MetaDataField<Boolean> 
	META_DASHING = new MetaDataField<>(19, false, EntityMetaTypes.BOOLEAN);
	public static final MetaDataField<Long> 
	META_LAST_POSE_TICK = new MetaDataField<>(20, 0L, EntityMetaTypes.VAR_LONG);
	
	public CamelMetaComponent(ComponentType type) {
		super(type);
	}
	
	@Override
	public int getMetaFieldCount() {
		return 2;
	}
	
	@Override
	public void initMetaContainer(MetaDataContainer container) {
		container.set(META_DASHING);
		container.set(META_LAST_POSE_TICK);
	}

	public long getLastPoseTick() {
		return getHolder().getMetaContainer().getData(META_LAST_POSE_TICK);
	}

	public void setLastPoseTick(long pose) {
		getHolder().getMetaContainer().setData(META_LAST_POSE_TICK, pose);
	}

	public boolean isDashing() {
		return getHolder().getMetaContainer().getData(META_DASHING);
	}

	public void setDashing(boolean dashing) {
		getHolder().getMetaContainer().setData(META_DASHING, dashing);
	}
	
	@Override
	public NBTCodec<? extends CamelMetaComponent> getNBTCodec() {
		return NBT_HANDLER;
	}
	
}
