package de.atlasmc.core.node.entity;

import de.atlasmc.node.DyeColor;
import de.atlasmc.node.entity.EntityType;
import de.atlasmc.node.entity.Llama;
import de.atlasmc.node.entity.data.MetaDataField;
import de.atlasmc.node.entity.data.MetaDataType;
import de.atlasmc.node.inventory.AbstractHorseInventory;
import de.atlasmc.node.inventory.ContainerFactory;
import de.atlasmc.node.inventory.LlamaInventory;

public class CoreLlama extends CoreChestedHorse implements Llama {

	protected static final MetaDataField<Integer>
	META_LLAMA_STRENGTH = new MetaDataField<>(CoreChestedHorse.LAST_META_INDEX+1, 0, MetaDataType.VAR_INT);
	protected static final MetaDataField<Integer>
	META_LLAMA_CARPET = new MetaDataField<>(CoreChestedHorse.LAST_META_INDEX+2, -1, MetaDataType.VAR_INT);
	protected static final MetaDataField<Integer>
	META_LLAMA_VARIANT = new MetaDataField<>(CoreChestedHorse.LAST_META_INDEX+3, 0, MetaDataType.VAR_INT);

	protected static final int LAST_META_INDEX = CoreChestedHorse.LAST_META_INDEX+3;
	
	public CoreLlama(EntityType type) {
		super(type);
	}

	@Override
	protected void initMetaContainer() {
		super.initMetaContainer();
		metaContainer.set(META_LLAMA_STRENGTH);
		metaContainer.set(META_LLAMA_CARPET);
		metaContainer.set(META_LLAMA_VARIANT);
	}
	
	@Override
	protected int getMetaContainerSize() {
		return LAST_META_INDEX+1;
	}
	
	@Override
	public int getStrength() {
		return metaContainer.getData(META_LLAMA_STRENGTH);
	}

	@Override
	public DyeColor getCarpetColor() {
		return DyeColor.getByID(metaContainer.getData(META_LLAMA_CARPET));
	}

	@Override
	public LlamaColor getColor() {
		return LlamaColor.getByID(metaContainer.getData(META_LLAMA_VARIANT));
	}

	@Override
	public void setStrength(int strength) {
		if (!metaContainer.get(META_LLAMA_STRENGTH).setData(strength) || inv == null)
			return;
		AbstractHorseInventory old = inv;
		inv = createInventory();
		inv.setContents(old.getContents());
	}

	@Override
	public void setCarpedColor(DyeColor color) {
		if (color == null)
			metaContainer.get(META_LLAMA_CARPET).setData(-1);
		else 
			metaContainer.get(META_LLAMA_CARPET).setData(color.getID());
	}

	@Override
	public void setColor(LlamaColor color) {
		if (color == null)
			throw new IllegalArgumentException("Color can not be null!");
		metaContainer.get(META_LLAMA_CARPET).setData(color.getID());
	}
	
	@Override
	public LlamaInventory getInventory() {
		return (LlamaInventory) super.getInventory();
	}
	
	@Override
	protected AbstractHorseInventory createInventory() {
		return ContainerFactory.LLAMA_INV_FACTORY.create(this);
	}
	
	@Override
	public void setChest(boolean chest) {
		metaContainer.get(META_HORSE_HAS_CHEST).setData(chest);
	}
	
}
