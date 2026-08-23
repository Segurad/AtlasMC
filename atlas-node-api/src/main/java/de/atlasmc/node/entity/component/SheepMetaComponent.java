package de.atlasmc.node.entity.component;

import de.atlasmc.component.AbstractHolderBoundComponent;
import de.atlasmc.component.ComponentType;
import de.atlasmc.io.metadata.MetaData;
import de.atlasmc.io.metadata.MetaDataContainer;
import de.atlasmc.io.metadata.MetaDataField;
import de.atlasmc.io.metadata.MetaDataFieldProvider;
import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.node.DyeColor;
import de.atlasmc.node.entity.Entity;
import de.atlasmc.node.entity.EntityMetaTypes;
import de.atlasmc.util.annotation.NotNull;
import de.atlasmc.util.enums.EnumUtil;

public class SheepMetaComponent extends AbstractHolderBoundComponent<Entity> implements MetaDataFieldProvider {

	@NotNull
	public static final NBTCodec<SheepMetaComponent>
	NBT_CODEC = NBTCodec
					.builder(SheepMetaComponent.class)
					.include(AbstractHolderBoundComponent.NBT_CODEC)
					.codec("Color", SheepMetaComponent::getColor, SheepMetaComponent::setColor, EnumUtil.enumByteNBTCodec(DyeColor.class), DyeColor.WHITE)
					.boolField("Sheared", SheepMetaComponent::isSheared, SheepMetaComponent::setSheared, false)
					.build();
	
	/**
	 * 0x0F = Color ID
	 * 0x10 = Is sheared
	 */
	public static final MetaDataField<Byte>
	META_SHEEP_FLAGS = new MetaDataField<>(18, (byte) DyeColor.WHITE.getID(), EntityMetaTypes.BYTE);
	
	public SheepMetaComponent(ComponentType type) {
		super(type);
	}

	@Override
	public int getMetaFieldCount() {
		return 1;
	}
	
	@Override
	public void initMetaContainer(MetaDataContainer container) {
		container.set(META_SHEEP_FLAGS);
	}

	public DyeColor getColor() {
		return EnumUtil.getByID(DyeColor.class, getHolder().getMetaContainer().getData(META_SHEEP_FLAGS) & 0xF);
	}

	public void setColor(DyeColor color) {
		var container = getHolder().getMetaContainer();
		MetaData<Byte> data = container.get(META_SHEEP_FLAGS);
		container.setData(META_SHEEP_FLAGS, (byte) ((data.getData() & 0xF0) | color.getID()));
	}

	public boolean isSheared() {
		return (getHolder().getMetaContainer().getData(META_SHEEP_FLAGS) & 0x10) == 0x10;
	}

	public void setSheared(boolean sheared) {
		var container = getHolder().getMetaContainer();
		MetaData<Byte> data = container.get(META_SHEEP_FLAGS);
		container.setData(META_SHEEP_FLAGS, (byte) (sheared ? data.getData() | 0x10 : data.getData() & 0xEF));
	}
	
	@Override
	public NBTCodec<? extends SheepMetaComponent> getNBTCodec() {
		return NBT_CODEC;
	}
	
}
