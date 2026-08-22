package de.atlasmc.node.entity.component;

import de.atlasmc.component.AbstractHolderBoundComponent;
import de.atlasmc.component.ComponentType;
import de.atlasmc.io.metadata.MetaDataContainer;
import de.atlasmc.io.metadata.MetaDataField;
import de.atlasmc.io.metadata.MetaDataFieldProvider;
import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.node.DyeColor;
import de.atlasmc.node.block.BlockFace;
import de.atlasmc.node.entity.Entity;
import de.atlasmc.node.entity.EntityMetaTypes;
import de.atlasmc.util.annotation.NotNull;
import de.atlasmc.util.annotation.Nullable;
import de.atlasmc.util.enums.EnumUtil;

public class ShulkerMetaComponent extends AbstractHolderBoundComponent<Entity> implements MetaDataFieldProvider {

	@NotNull
	public static final NBTCodec<ShulkerMetaComponent>
	NBT_CODEC = NBTCodec
					.builder(ShulkerMetaComponent.class)
					.include(AbstractHolderBoundComponent.NBT_CODEC)
					.codec("AttachFace", ShulkerMetaComponent::getAttachedFace, ShulkerMetaComponent::setAttachedFace, BlockFace.FACE_ID_NBT_CODEC, BlockFace.DOWN)
					.codec("Color", ShulkerMetaComponent::getColor, ShulkerMetaComponent::setColor, EnumUtil.enumByteNBTCodec(DyeColor.class), DyeColor.MAGENTA)
					.byteField("Peek", ShulkerMetaComponent::getShieldHeight, ShulkerMetaComponent::setShieldHeight, (byte) 16)
					.build();
	
	public static final MetaDataField<BlockFace>
	META_DIRECTION = new MetaDataField<>(16, BlockFace.DOWN, EntityMetaTypes.DIRECTION);
	public static final MetaDataField<Byte>
	META_SHIELD_HEIGHT = new MetaDataField<>(17, (byte) 0, EntityMetaTypes.BYTE);
	public static final MetaDataField<Byte>
	META_COLOR = new MetaDataField<>(18, (byte) 16, EntityMetaTypes.BYTE);
	
	public ShulkerMetaComponent(ComponentType type) {
		super(type);
	}
	
	@Override
	public void initMetaContainer(MetaDataContainer container) {
		container.set(META_DIRECTION);
		container.set(META_SHIELD_HEIGHT);
		container.set(META_COLOR);
	}

	@Override
	public int getMetaFieldCount() {
		return 3;
	}
	
	public BlockFace getAttachedFace() {
		return getHolder().getMetaContainer().getData(META_DIRECTION);
	}

	public void setAttachedFace(BlockFace attached) {
		getHolder().getMetaContainer().setData(META_DIRECTION, attached);
	}

	public int getShieldHeight() {
		return getHolder().getMetaContainer().getData(META_SHIELD_HEIGHT);
	}

	public void setShieldHeight(int height) {
		if (height < 0 || height > 100)
			throw new IllegalArgumentException("Height must be between 0 and 100: " + height);
		getHolder().getMetaContainer().setData(META_SHIELD_HEIGHT, (byte) height);
	}

	@Nullable
	public DyeColor getColor() {
		int color = getHolder().getMetaContainer().getData(META_COLOR);
		return color == 16 ? null : EnumUtil.getByID(DyeColor.class, color);
	}

	public void setColor(DyeColor color) {
		int value = 16;
		if (color != null)
			value = color.getID();
		getHolder().getMetaContainer().setData(META_COLOR, (byte) value);
	}
	
	@Override
	public NBTCodec<? extends ShulkerMetaComponent> getNBTCodec() {
		return NBT_CODEC;
	}
	
}
