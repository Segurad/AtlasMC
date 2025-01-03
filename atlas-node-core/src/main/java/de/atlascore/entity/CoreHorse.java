package de.atlascore.entity;

import java.io.IOException;
import java.util.UUID;

import de.atlasmc.Material;
import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.Horse;
import de.atlasmc.entity.data.MetaData;
import de.atlasmc.entity.data.MetaDataField;
import de.atlasmc.entity.data.MetaDataType;
import de.atlasmc.inventory.ContainerFactory;
import de.atlasmc.inventory.HorseInventory;
import de.atlasmc.inventory.ItemStack;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.NBTFieldSet;
import de.atlasmc.util.nbt.io.NBTWriter;

public class CoreHorse extends CoreAbstractHorse implements Horse {

	protected static final MetaDataField<Integer>
	META_HORSE_VARIANT = new MetaDataField<>(CoreAbstractHorse.LAST_META_INDEX+1, 0, MetaDataType.VAR_INT);
	
	protected static final int LAST_META_INDEX = CoreAbstractHorse.LAST_META_INDEX+1;
	
	protected static final NBTFieldSet<CoreHorse> NBT_FIELDS;
	
	protected static final CharKey
	NBT_ARMOR_ITEM = CharKey.literal("ArmorItem"),
	NBT_VARIANT = CharKey.literal("Variant");
	
	static {
		NBT_FIELDS = CoreAbstractHorse.NBT_FIELDS.fork();
		NBT_FIELDS.setField(NBT_ARMOR_ITEM, (holder, reader) -> {
			reader.readNextEntry();
			Material mat = null;
			if (!NBT_ID.equals(reader.getFieldName())) {
				reader.mark();
				reader.search(NBT_ID);
				mat = Material.getByName(reader.readStringTag());
				reader.reset();
			} else mat = Material.getByName(reader.readStringTag());
			ItemStack item = new ItemStack(mat);
			item.fromNBT(reader);
			holder.getInventory().setArmor(item);
		});
		NBT_FIELDS.setField(NBT_VARIANT, (holder, reader) -> {
			int variant = reader.readIntTag();
			holder.setColor(HorseColor.getByID(variant & 0xFF));
			holder.setStyle(Style.getByID(variant >> 8 & 0xFF));
		});
	}
	
	public CoreHorse(EntityType type, UUID uuid) {
		super(type, uuid);
	}
	
	@Override
	protected NBTFieldSet<? extends CoreHorse> getFieldSetRoot() {
		return NBT_FIELDS;
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
	public HorseColor getColor() {
		return HorseColor.getByID(metaContainer.getData(META_HORSE_VARIANT) & 0xFF);
	}

	@Override
	public Style getStyle() {
		return Style.getByID((metaContainer.getData(META_HORSE_VARIANT) >> 8) & 0xFF);
	}

	@Override
	public void setColor(HorseColor color) {
		if (color == null)
			throw new IllegalArgumentException("Color can not be null!");
		MetaData<Integer> data = metaContainer.get(META_HORSE_VARIANT);
		data.setData(data.getData() & 0xFF00 | color.getID());
	}

	@Override
	public void setStyle(Style style) {
		if (style == null)
			throw new IllegalArgumentException("Style can not be null!");
		MetaData<Integer> data = metaContainer.get(META_HORSE_VARIANT);
		data.setData(data.getData() & 0xFF | (style.getID() << 8));
	}
	
	@Override
	public HorseInventory getInventory() {
		return (HorseInventory) super.getInventory();
	}

	@Override
	protected HorseInventory createInventory() {
		return ContainerFactory.HORSE_INV_FACTORY.create(this);
	}
	
	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		super.toNBT(writer, systemData);
		if (inv != null && getInventory().getArmor() != null) {
			writer.writeCompoundTag(NBT_ARMOR_ITEM);
			getInventory().getArmor().toNBT(writer, systemData);
			writer.writeEndTag();
		}
		writer.writeIntTag(NBT_VARIANT, metaContainer.getData(META_HORSE_VARIANT));
	}

}
