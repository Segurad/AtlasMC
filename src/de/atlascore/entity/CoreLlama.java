package de.atlascore.entity;

import java.io.IOException;
import java.util.UUID;

import de.atlasmc.DyeColor;
import de.atlasmc.Material;
import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.Llama;
import de.atlasmc.entity.data.MetaDataField;
import de.atlasmc.entity.data.MetaDataType;
import de.atlasmc.factory.ContainerFactory;
import de.atlasmc.inventory.AbstractHorseInventory;
import de.atlasmc.inventory.ItemStack;
import de.atlasmc.inventory.LlamaInventory;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.ChildNBTFieldContainer;
import de.atlasmc.util.nbt.NBTFieldContainer;
import de.atlasmc.util.nbt.io.NBTWriter;
import de.atlasmc.world.World;

public class CoreLlama extends CoreChestedHorse implements Llama {

	protected static final MetaDataField<Integer>
	META_LLAMA_STRENGTH = new MetaDataField<>(CoreChestedHorse.LAST_META_INDEX+1, 0, MetaDataType.INT);
	protected static final MetaDataField<Integer>
	META_LLAMA_CARPET = new MetaDataField<>(CoreChestedHorse.LAST_META_INDEX+2, -1, MetaDataType.INT);
	protected static final MetaDataField<Integer>
	META_LLAMA_VARIANT = new MetaDataField<>(CoreChestedHorse.LAST_META_INDEX+3, 0, MetaDataType.INT);

	protected static final int LAST_META_INDEX = CoreChestedHorse.LAST_META_INDEX+3;
	
	protected static final NBTFieldContainer NBT_FIELDS;
	
	protected static final CharKey
	NBT_DECOR_ITEM = CharKey.of("DecorItem"),
	NBT_STRENGTH = CharKey.of("Strength"),
	NBT_VARIANT = CharKey.of("Variant");
	
	static {
		NBT_FIELDS = new ChildNBTFieldContainer(CoreChestedHorse.NBT_FIELDS);
		NBT_FIELDS.setField(NBT_DECOR_ITEM, (holder, reader) -> {
			if (!(holder instanceof Llama)) {
				reader.skipTag();
				return;
			}
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
			((Llama) holder).getInventory().setDecor(item);
		});
		NBT_FIELDS.setField(NBT_STRENGTH, (holder, reader) -> {
			if (holder instanceof Llama) {
				((Llama) holder).setStrength(reader.readIntTag());
			} else reader.skipTag();
		});
		NBT_FIELDS.setField(NBT_VARIANT, (holder, reader) -> {
			if (holder instanceof Llama) {
				((Llama) holder).setColor(LlamaColor.getByID(reader.readIntTag()));
			} else reader.skipTag();
		});
	}
	
	public CoreLlama(EntityType type, UUID uuid, World world) {
		super(type, uuid, world);
	}
	
	@Override
	protected NBTFieldContainer getFieldContainerRoot() {
		return NBT_FIELDS;
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

	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		super.toNBT(writer, systemData);
		if (inv != null && getInventory().getDecor() != null) {
			writer.writeCompoundTag(NBT_DECOR_ITEM);
			getInventory().getDecor().toNBT(writer, systemData);
			writer.writeEndTag();
		}
		writer.writeIntTag(NBT_STRENGTH, getStrength());
		writer.writeIntTag(NBT_VARIANT, getColor().getID());
	}
	
}
