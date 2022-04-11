package de.atlascore.entity;

import java.util.UUID;

import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.Horse;
import de.atlasmc.entity.data.MetaData;
import de.atlasmc.entity.data.MetaDataField;
import de.atlasmc.entity.data.MetaDataType;
import de.atlasmc.factory.ContainerFactory;
import de.atlasmc.inventory.HorseInventory;
import de.atlasmc.world.World;

public class CoreHorse extends CoreAbstractHorse implements Horse {

	protected static final MetaDataField<Integer>
	META_HORSE_VARIANT = new MetaDataField<>(CoreAbstractHorse.LAST_META_INDEX+1, 0, MetaDataType.INT);
	
	protected static final int LAST_META_INDEX = CoreAbstractHorse.LAST_META_INDEX+1;
	
	public CoreHorse(EntityType type, UUID uuid, World world) {
		super(type, uuid, world);
	}
	
	@Override
	protected void initMetaContainer() {
		super.initMetaContainer();
		metaContainer.set(META_HORSE_VARIANT);
	}

	@Override
	protected int getMetaContainerSize() {
		return LAST_META_INDEX+1;
	}
	
	@Override
	public Color getColor() {
		return Color.getByID(metaContainer.getData(META_HORSE_VARIANT) & 0xFF);
	}

	@Override
	public Style getStyle() {
		return Style.getByID((metaContainer.getData(META_HORSE_VARIANT) >> 8) & 0xFF);
	}

	@Override
	public void setColor(Color color) {
		if (color == null)
			throw new IllegalArgumentException("Color can not be null!");
		MetaData<Integer> data = metaContainer.get(META_HORSE_VARIANT);
		data.setData(data.getData() & 0xFF | color.getID());
	}

	@Override
	public void setStyle(Style style) {
		if (style == null)
			throw new IllegalArgumentException("Style can not be null!");
		MetaData<Integer> data = metaContainer.get(META_HORSE_VARIANT);
		data.setData(data.getData() & 0xFF00 | (style.getID() << 8));
	}
	
	@Override
	public HorseInventory getInventory() {
		return (HorseInventory) super.getInventory();
	}

	@Override
	protected HorseInventory createInventory() {
		return ContainerFactory.HORSE_INV_FACTORY.create(this);
	}

}
