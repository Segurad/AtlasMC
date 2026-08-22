package de.atlasmc.node.entity.component;

import de.atlasmc.IDHolder;
import de.atlasmc.component.AbstractHolderBoundComponent;
import de.atlasmc.component.ComponentType;
import de.atlasmc.component.HolderBoundComponent;
import de.atlasmc.io.metadata.MetaDataContainer;
import de.atlasmc.io.metadata.MetaDataField;
import de.atlasmc.io.metadata.MetaDataFieldProvider;
import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.node.DyeColor;
import de.atlasmc.node.entity.Entity;
import de.atlasmc.node.entity.EntityMetaTypes;
import de.atlasmc.util.annotation.NotNull;
import de.atlasmc.util.enums.EnumName;
import de.atlasmc.util.enums.EnumUtil;

public class CatMetaComponent extends AbstractHolderBoundComponent<Entity> implements MetaDataFieldProvider {
	
	public static final MetaDataField<Type>
	META_CAT_TYPE = new MetaDataField<>(20, Type.BLACK, EntityMetaTypes.CAT_VARIANT);
	public static final MetaDataField<Boolean>
	META_IS_LYING = new MetaDataField<>(21, false, EntityMetaTypes.BOOLEAN);
	public static final MetaDataField<Boolean>
	META_IS_RELAXED = new MetaDataField<>(22, false, EntityMetaTypes.BOOLEAN);
	public static final MetaDataField<Integer>
	META_COLLAR_COLOR = new MetaDataField<>(23, DyeColor.RED.getID(), EntityMetaTypes.VAR_INT);
	
	@NotNull
	public static final NBTCodec<CatMetaComponent>
	NBT_CODEC = NBTCodec
					.builder(CatMetaComponent.class)
					.include(HolderBoundComponent.NBT_CODEC)
					.codec("CollarColor", CatMetaComponent::getCollarColor, CatMetaComponent::setCollarColor, EnumUtil.enumByteNBTCodec(DyeColor.class), DyeColor.RED)
					.codec("variant", CatMetaComponent::getCatType, CatMetaComponent::setCatType, EnumUtil.enumStringNBTCodec(Type.class), Type.BLACK)
					.build();
	
	public CatMetaComponent(ComponentType type) {
		super(type);
	}
	
	@Override
	public int getMetaFieldCount() {
		return 4;
	}
	
	@Override
	public void initMetaContainer(MetaDataContainer container) {
		container.set(META_CAT_TYPE);
		container.set(META_IS_LYING);
		container.set(META_IS_RELAXED);
		container.set(META_COLLAR_COLOR);
	}
	
	public Type getCatType() {
		return getHolder().getMetaContainer().getData(META_CAT_TYPE);
	}

	public void setCatType(Type type) {
		getHolder().getMetaContainer().setData(META_CAT_TYPE, type);
	}

	public boolean isLying() {
		return getHolder().getMetaContainer().getData(META_IS_LYING);
	}

	public void setLying(boolean lying) {
		getHolder().getMetaContainer().setData(META_IS_LYING, lying);		
	}

	public boolean isRelaxed() {
		return getHolder().getMetaContainer().getData(META_IS_RELAXED);
	}

	public void setRelaxed(boolean relaxed) {
		getHolder().getMetaContainer().setData(META_IS_RELAXED, relaxed);		
	}

	public DyeColor getCollarColor() {
		return EnumUtil.getByID(DyeColor.class, getHolder().getMetaContainer().getData(META_COLLAR_COLOR));
	}

	public void setCollarColor(DyeColor color) {
		getHolder().getMetaContainer().setData(META_COLLAR_COLOR, color.getID());		
	}
	
	@Override
	public NBTCodec<? extends CatMetaComponent> getNBTCodec() {
		return NBT_CODEC;
	}
	
	public static enum Type implements EnumName, IDHolder {

		TABBY,
		BLACK,
		RED,
		SIAMESE,
		BRITISH_SHORTHAIR,
		CALICO,
		PERSIAN,
		RAGDOLL,
		WHITE,
		JELLIE,
		ALL_BLACK;
		
		private final String name;
		
		private Type() {
			String name = "minecraft:" + name().toLowerCase();
			this.name = name.intern();
		}
		
		@Override
		public String getName() {
			return name;
		}
		
		@Override
		public int getID() {
			return ordinal();
		}
		
	}

}
