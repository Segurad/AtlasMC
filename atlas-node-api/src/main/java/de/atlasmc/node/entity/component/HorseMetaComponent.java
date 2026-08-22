package de.atlasmc.node.entity.component;

import de.atlasmc.IDHolder;
import de.atlasmc.component.AbstractHolderBoundComponent;
import de.atlasmc.component.ComponentType;
import de.atlasmc.component.HolderBoundComponent;
import de.atlasmc.io.metadata.MetaData;
import de.atlasmc.io.metadata.MetaDataContainer;
import de.atlasmc.io.metadata.MetaDataField;
import de.atlasmc.io.metadata.MetaDataFieldProvider;
import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.node.entity.Entity;
import de.atlasmc.node.entity.EntityMetaTypes;
import de.atlasmc.util.annotation.NotNull;
import de.atlasmc.util.enums.EnumUtil;

public class HorseMetaComponent extends AbstractHolderBoundComponent<Entity> implements MetaDataFieldProvider {

	public static final MetaDataField<Integer>
	META_HORSE_VARIANT = new MetaDataField<>(19, 0, EntityMetaTypes.VAR_INT);
	
	@NotNull
	public static final NBTCodec<HorseMetaComponent>
	NBT_CODEC = NBTCodec
					.builder(HorseMetaComponent.class)
					.include(HolderBoundComponent.NBT_CODEC)
					.intField("Variant", HorseMetaComponent::getVariantID, HorseMetaComponent::setVariantID, 0)
					.build();
	
	public HorseMetaComponent(ComponentType type) {
		super(type);
	}
	
	@Override
	public void initMetaContainer(MetaDataContainer container) {
		container.set(META_HORSE_VARIANT);
	}
	
	@Override
	public int getMetaFieldCount() {
		return 1;
	}
	
	public HorseColor getColor() {
		return EnumUtil.getByID(HorseColor.class, getHolder().getMetaContainer().getData(META_HORSE_VARIANT) & 0xFF);
	}

	public Style getStyle() {
		return EnumUtil.getByID(Style.class, (getHolder().getMetaContainer().getData(META_HORSE_VARIANT) >> 8) & 0xFF);
	}

	public void setColor(HorseColor color) {
		var metaContainer = getHolder().getMetaContainer();
		MetaData<Integer> data = metaContainer.get(META_HORSE_VARIANT);
		metaContainer.setData(META_HORSE_VARIANT, data.getData() & 0xFF00 | color.getID());
	}

	public void setStyle(Style style) {
		var metaContainer = getHolder().getMetaContainer();
		MetaData<Integer> data = metaContainer.get(META_HORSE_VARIANT);
		metaContainer.setData(META_HORSE_VARIANT, data.getData() & 0xFF | (style.getID() << 8));
	}
	
	public int getVariantID() {
		return getHolder().getMetaContainer().getData(META_HORSE_VARIANT);
	}
	
	public void setVariantID(int id) {
		getHolder().getMetaContainer().setData(META_HORSE_VARIANT, id);
	}
	
	@Override
	public NBTCodec<? extends HorseMetaComponent> getNBTCodec() {
		return NBT_CODEC;
	}
	
	public static enum HorseColor implements IDHolder {
		
		WHITE,
		CREAMY,
		CJESTMIT,
		BROWN,
		BLACK,
		GRAY,
		DARK_BROWN;
		
		@Override
		public int getID() {
			return ordinal();
		}
		
	}
	
	public static enum Style implements IDHolder {
		
		NONE,
		WHITE,
		WHITE_FIELD,
		WHITE_DOTS,
		BLACK_DOTS;
		
		@Override
		public int getID() {
			return ordinal();
		}
		
	}
	
}
