package de.atlasmc.node.entity.component;

import de.atlasmc.component.AbstractHolderBoundComponent;
import de.atlasmc.component.ComponentType;
import de.atlasmc.io.metadata.MetaDataContainer;
import de.atlasmc.io.metadata.MetaDataField;
import de.atlasmc.io.metadata.MetaDataFieldProvider;
import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.node.entity.Entity;
import de.atlasmc.node.entity.EntityMetaTypes;

public class MinecartFurnaceMetaComponent extends AbstractHolderBoundComponent<Entity> implements MetaDataFieldProvider {

	public static final NBTCodec<MinecartFurnaceMetaComponent>
	NBT_CODEC = NBTCodec
					.builder(MinecartFurnaceMetaComponent.class)
					.include(AbstractHolderBoundComponent.NBT_CODEC)
					// non standard by atlas
					.boolField("hasFuel", MinecartFurnaceMetaComponent::hasFuel, MinecartFurnaceMetaComponent::setFuel, false)
					.build();
	
	public static final MetaDataField<Boolean>
	META_HAS_FUEL = new MetaDataField<>(13, false, EntityMetaTypes.BOOLEAN);
	
	public MinecartFurnaceMetaComponent(ComponentType type) {
		super(type);
	}
	
	@Override
	public int getMetaFieldCount() {
		return 1;
	}
	
	@Override
	public void initMetaContainer(MetaDataContainer container) {
		container.set(META_HAS_FUEL);
	}
	
	public boolean hasFuel() {
		return getHolder().getMetaContainer().getData(META_HAS_FUEL);
	}
	
	public void setFuel(boolean fuel) {
		getHolder().getMetaContainer().setData(META_HAS_FUEL, fuel);
	}
	
	@Override
	public NBTCodec<? extends MinecartFurnaceMetaComponent> getNBTCodec() {
		return NBT_CODEC;
	}
	
}
