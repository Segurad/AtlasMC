package de.atlasmc.inventory;

import java.io.IOException;

import de.atlasmc.Material;
import de.atlasmc.inventory.meta.ItemMeta;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.NBTHolder;
import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;
import de.atlasmc.util.nbt.tag.NBT;

public class ItemStack implements NBTHolder {

	protected static final CharKey
	NBT_COUNT = CharKey.literal("Count"),
	NBT_ID = CharKey.literal("id"),
	NBT_TAG = CharKey.literal("tag"),
	NBT_CUSTOM_CREATIVE_LOCK = CharKey.literal("CustomCreativeLock"),
	NBT_SLOT = CharKey.literal("Slot");
	
	private byte amount;
	private Material type;
	private ItemMeta meta;

	/**
	 * Creates a ItemStack of the Type {@link Material#AIR} with amount of 1
	 */
	public ItemStack() {
		this(Material.AIR, 1);
	}
	
	public ItemStack(Material material) {
		this(material, 1);
	}

	public ItemStack(Material material, int amount) {
		if (material == null) 
			throw new IllegalArgumentException("Material can not be null!");
		type = material;
		setAmount(amount);
	}

	public Material getType() {
		return type;
	}
	
	public int getAmount() {
		return amount;
	}
	
	public void setAmount(int amount) {
		if (amount < -128 || amount > 127) 
			throw new IllegalArgumentException("Amount must be between -128 and 127: " + amount);
		this.amount = (byte) amount;
	}
	
	public ItemMeta getItemMeta() {
		if (!hasItemMeta()) meta = type.createItemMeta();
		return meta;
	}
	
	public boolean hasItemMeta() {
		return meta != null;
	}
	
	public boolean setItemMeta(ItemMeta meta) {
		if (type.isValidMeta(meta)) return false;
		this.meta = meta;
		return true;
	}

	/**
	 * 
	 * @return the NBT or null if the ItemStack has no ItemMeta
	 * @throws IOException 
	 */
	public NBT getMetaAsNBT() throws IOException {
		if (meta == null) 
			return null;
		return meta.toNBT();
	}

	/**
	 * Compares a ItemStack with this ItemStack a returns whether or not it is similar.
	 * Ignoring stack size and damage
	 * @param item
	 * @return true if similar
	 */
	public boolean isSimilar(ItemStack item) {
		return isSimilar(item, true, true);
	}
	
	public ItemStack clone() {
		try {
			ItemStack clone = (ItemStack) super.clone();
			if (hasItemMeta()) clone.setItemMeta(getItemMeta().clone());
			return clone;
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return null;
	}

	public int getMaxStackSize() {
		return type.getMaxAmount();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (obj == this)
			return true;
		if (!(obj instanceof ItemStack))
			return false;
		ItemStack item = (ItemStack) obj;
		return isSimilar(item, false, false);
	}
	
	/**
	 * Compares a ItemStack with this ItemStack a returns whether or not it is similar
	 * @param item the ItemStack that should be compared
	 * @param ignoreAmount whether or not the amount should be ignored in this comparison
	 * @param ignoreDamage whether or not the damage values should be ignored in this comparison
	 * @return true if similar
	 */
	public boolean isSimilar(ItemStack item, boolean ignoreAmount, boolean ignoreDamage) {
		if (item == null) 
			return false;
		if (item == this) 
			return true;
		if (item.getClass() != getClass())
			return false;
		if (type != item.getType()) 
			return false; 
		if (!ignoreAmount && amount != item.getAmount())
			return false;
		if (!hasItemMeta())
			return !item.hasItemMeta();
		if (!item.hasItemMeta())
			return false;
		ItemMeta meta = item.getItemMeta();
		return meta.isSimilar(meta, ignoreDamage);
	}
	
	/**
	 * Same as {@link #toNBT(NBTWriter, boolean)} but does write the slot number
	 * @param writer
	 * @param systemData
	 * @param slot the slot number or -999 for none
	 * @throws IOException 
	 */
	public void toSlot(NBTWriter writer, boolean systemData, int slot) throws IOException {
		if (systemData)
			writer.writeStringTag("id", getType().getNamespacedKeyRaw());
		else
			writer.writeStringTag("id", getType().getClientKey().toString());
		writer.writeByteTag("Count", (byte) getAmount());
		if (slot != -999) writer.writeByteTag(NBT_SLOT, slot);
		if(hasItemMeta()) {
			writer.writeCompoundTag("tag");
			getItemMeta().toNBT(writer, systemData);
			writer.writeEndTag();
		}
	}

	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		toSlot(writer, systemData, -999);
	}

	@Override
	public void fromNBT(NBTReader reader) throws IOException {
		fromSlot(reader);
	}
	
	/**
	 * Same as {@link #fromSlot(NBTReader)} but does return the slot number while reading slot NBT
	 * @param reader the NBTReader should be used
	 * @return the slot number or -999 if not present
	 * @throws IOException 
	 */
	public int fromSlot(NBTReader reader) throws IOException {
		int slot = -999;
		final int depth = reader.getDepth();
		while (depth <= reader.getDepth()) {
			final CharSequence value = reader.getFieldName();
			if (NBT_COUNT.equals(value)) 
				amount = reader.readByteTag();
			else if (NBT_CUSTOM_CREATIVE_LOCK.equals(value)) 
				reader.skipTag(); // TODO skip creative lock because i don't know if it is used
			else if (NBT_ID.equals(value))
				reader.readStringTag(); // TODO skipped material should be initiated by creating the stack
			else if (NBT_SLOT.equals(value))
				slot = reader.readByteTag();
			else if (NBT_TAG.equals(value))
				if (reader.getType() != TagType.COMPOUND) {
					reader.skipTag();
				} else {
					reader.readNextEntry();
					getItemMeta().fromNBT(reader);
				}
			else
				reader.skipTag();
		}
		if (reader.getType() == TagType.TAG_END)
			reader.skipTag();
		return slot;
	}
	
	@Override
	public int hashCode() {
		int h = 1;
		h = 31 * h + amount;
		h = 31 * h + type.hashCode();
		if (meta != null)
			h = 31 * h + meta.hashCode();
		return h;
	}
	
}
