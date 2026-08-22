package de.atlasmc.node.entity.component;

import de.atlasmc.component.AbstractHolderBoundComponent;
import de.atlasmc.component.ComponentType;
import de.atlasmc.io.metadata.MetaDataContainer;
import de.atlasmc.io.metadata.MetaDataField;
import de.atlasmc.io.metadata.MetaDataFieldProvider;
import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.node.entity.Entity;
import de.atlasmc.node.entity.EntityMetaTypes;
import de.atlasmc.node.inventory.ItemStack;
import de.atlasmc.util.annotation.NotNull;

public class ThrownProjectileMetaComponent extends AbstractHolderBoundComponent<Entity> implements MetaDataFieldProvider {

	@NotNull
	public static final NBTCodec<ThrownProjectileMetaComponent>
	NBT_CODEC = NBTCodec
					.builder(ThrownProjectileMetaComponent.class)
					.include(AbstractHolderBoundComponent.NBT_CODEC)
					.codec("Item", ThrownProjectileMetaComponent::getItem, ThrownProjectileMetaComponent::setItem, ItemStack.NBT_CODEC)
					.build();

	public static final MetaDataField<ItemStack> 
	META_PROJECTILE_ITEM = new MetaDataField<>(8, null, EntityMetaTypes.SLOT);
	
	public ThrownProjectileMetaComponent(ComponentType type) {
		super(type);
	}
	
	@Override
	public void initMetaContainer(MetaDataContainer container) {
		container.set(META_PROJECTILE_ITEM);
	}
	
	@Override
	public int getMetaFieldCount() {
		return 1;
	}

	public ItemStack getItem() {
		return getHolder().getMetaContainer().getData(META_PROJECTILE_ITEM);
	}

	public void setItem(ItemStack item) {
		getHolder().getMetaContainer().setData(META_PROJECTILE_ITEM, item);
	}
	
	@Override
	public NBTCodec<? extends ThrownProjectileMetaComponent> getNBTCodec() {
		return NBT_CODEC;
	}
	
}
