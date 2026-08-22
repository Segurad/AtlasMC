package de.atlasmc.node.entity.component;

import de.atlasmc.IDHolder;
import de.atlasmc.component.AbstractHolderBoundComponent;
import de.atlasmc.component.ComponentType;
import de.atlasmc.component.HolderBoundComponent;
import de.atlasmc.io.metadata.MetaDataContainer;
import de.atlasmc.io.metadata.MetaDataField;
import de.atlasmc.io.metadata.MetaDataFieldProvider;
import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.node.entity.Entity;
import de.atlasmc.node.entity.EntityMetaTypes;
import de.atlasmc.util.annotation.NotNull;
import de.atlasmc.util.enums.EnumName;
import de.atlasmc.util.enums.EnumUtil;

public class FrogMetaComponent extends AbstractHolderBoundComponent<Entity> implements MetaDataFieldProvider {
	
	public static final MetaDataField<Variant> 
	META_FROG_VARIANT = new MetaDataField<>(18, Variant.TEMPERATE, EntityMetaTypes.FROG_VARIANT);
	public static final MetaDataField<Integer> 
	META_TONGUE_TARGET = new MetaDataField<>(19, null, EntityMetaTypes.OPT_VAR_INT);
	
	@NotNull
	public static final NBTCodec<FrogMetaComponent>
	NBT_CODEC = NBTCodec
					.builder(FrogMetaComponent.class)
					.include(HolderBoundComponent.NBT_CODEC)
					.codec("variant", FrogMetaComponent::getVariant, FrogMetaComponent::setVariant, EnumUtil.enumStringNBTCodec(Variant.class), Variant.TEMPERATE)
					.build();
	
	private Entity tongueTarget;
	
	public FrogMetaComponent(ComponentType type) {
		super(type);
	}
	
	@Override
	public int getMetaFieldCount() {
		return 2;
	}
	
	@Override
	public void initMetaContainer(MetaDataContainer container) {
		container.set(META_FROG_VARIANT);
		container.set(META_TONGUE_TARGET);
	}

	public Entity getTongueTarget() {
		return tongueTarget;
	}

	public void setTangueTarget(Entity entity) {
		getHolder().getMetaContainer().setData(META_TONGUE_TARGET, entity != null ? entity.getID() : null);
		this.tongueTarget = entity;
	}
	
	public Variant getVariant() {
		return getHolder().getMetaContainer().getData(META_FROG_VARIANT);
	}

	public void setVariant(Variant variant) {
		getHolder().getMetaContainer().setData(META_FROG_VARIANT, variant);
	}
	
	@Override
	public NBTCodec<? extends FrogMetaComponent> getNBTCodec() {
		return NBT_CODEC;
	}
	
	public static enum Variant implements EnumName, IDHolder {
		
		TEMPERATE,
		WARM,
		COLD;
		
		private String name;
		
		private Variant() {
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
