package de.atlascore.entity;

import java.util.UUID;

import de.atlasmc.DyeColor;
import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.Llama;
import de.atlasmc.entity.data.MetaDataField;
import de.atlasmc.entity.data.MetaDataType;
import de.atlasmc.world.World;

public class CoreLlama extends CoreChestedHorse implements Llama {

	protected static final MetaDataField<Integer>
	META_LLAMA_STRENGTH = new MetaDataField<>(CoreChestedHorse.LAST_META_INDEX+1, 0, MetaDataType.INT);
	protected static final MetaDataField<Integer>
	META_LLAMA_CARPET = new MetaDataField<>(CoreChestedHorse.LAST_META_INDEX+2, -1, MetaDataType.INT);
	protected static final MetaDataField<Integer>
	META_LLAMA_VARIANT = new MetaDataField<>(CoreChestedHorse.LAST_META_INDEX+3, 0, MetaDataType.INT);

	protected static final int LAST_META_INDEX = CoreChestedHorse.LAST_META_INDEX+3;
	
	public CoreLlama(EntityType type, UUID uuid, World world) {
		super(type, uuid, world);
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
	public Color getColor() {
		return Color.getByID(metaContainer.getData(META_LLAMA_VARIANT));
	}

	@Override
	public void setStrength(int strength) {
		metaContainer.get(META_LLAMA_STRENGTH).setData(strength);		
	}

	@Override
	public void setCarpedColor(DyeColor color) {
		if (color == null)
			metaContainer.get(META_LLAMA_CARPET).setData(-1);
		else 
			metaContainer.get(META_LLAMA_CARPET).setData(color.getID());
	}

	@Override
	public void setColor(Color color) {
		if (color == null)
			throw new IllegalArgumentException("Color can not be null!");
		metaContainer.get(META_LLAMA_CARPET).setData(color.getID());
	}

}
