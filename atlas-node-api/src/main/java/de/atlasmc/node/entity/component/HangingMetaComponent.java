package de.atlasmc.node.entity.component;

import de.atlasmc.component.AbstractHolderBoundComponent;
import de.atlasmc.component.ComponentType;
import de.atlasmc.io.metadata.MetaDataContainer;
import de.atlasmc.io.metadata.MetaDataField;
import de.atlasmc.io.metadata.MetaDataFieldProvider;
import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.node.block.BlockFace;
import de.atlasmc.node.entity.Entity;
import de.atlasmc.node.entity.EntityMetaTypes;
import de.atlasmc.util.annotation.NotNull;

public class HangingMetaComponent extends AbstractHolderBoundComponent<Entity> implements MetaDataFieldProvider {
	
	@NotNull
	public static final NBTCodec<HangingMetaComponent>
	NBT_CODEC = NBTCodec
					.builder(HangingMetaComponent.class)
					.include(AbstractHolderBoundComponent.NBT_CODEC)
					.codec("Facing", HangingMetaComponent::getAttachedFace, HangingMetaComponent::setFacingDirection, BlockFace.FACE_ID_NBT_CODEC, BlockFace.SOUTH)
					.build();
	
	public static final MetaDataField<BlockFace>
	META_DIRECTION = new MetaDataField<>(8, BlockFace.SOUTH, EntityMetaTypes.DIRECTION);
	
	public HangingMetaComponent(ComponentType type) {
		super(type);
	}
	
	@Override
	protected void holderChanged(Entity holder) {
		if (holder == null)
			return;
		var face = holder.getMetaContainer().getData(META_DIRECTION);
		updateFace(holder, face);
	}
	
	@Override
	public void initMetaContainer(MetaDataContainer container) {
		container.set(META_DIRECTION);
	}
	
	@Override
	public int getMetaFieldCount() {
		return 1;
	}

	public BlockFace getAttachedFace() {
		return getHolder().getMetaContainer().getData(META_DIRECTION);
	}

	public void setFacingDirection(BlockFace face) {
		if (face.ordinal() > 5)
			throw new IllegalArgumentException("Face not compatible with Painting: " + face.name());
		var holder = getHolder();
		if (!holder.getMetaContainer().setData(META_DIRECTION, face))
			return;
		updateFace(holder, face);
	}
	
	private void updateFace(Entity holder, BlockFace face) {
		holder.setObjectData(face.getFaceID());
		var loc = holder.getLocationUnsafe();
		loc.yaw = face.getYaw();
		loc.pitch = face.getPitch();
	}
	
	@Override
	public NBTCodec<? extends HangingMetaComponent> getNBTCodec() {
		return NBT_CODEC;
	}
	
}
