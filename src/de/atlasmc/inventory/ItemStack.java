package de.atlasmc.inventory;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import de.atlasmc.Material;
import de.atlasmc.attribute.Attribute;
import de.atlasmc.attribute.AttributeModifier;
import de.atlasmc.enchantments.Enchantment;
import de.atlasmc.inventory.meta.ItemMeta;
import de.atlasmc.util.Validate;
import de.atlasmc.util.nbt.NBT;
import de.atlasmc.util.nbt.NBTHolder;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;

public class ItemStack implements NBTHolder {

	private byte amount;
	private Material type;
	private ItemMeta meta;

	public ItemStack(Material material) {
		this(material, 1);
	}

	public ItemStack(Material material, int amount) {
		Validate.notNull(material, "Material can not be null!");
		Validate.isFalse(amount > 127, "Amount can not be higher than 127");
		Validate.isFalse(amount < -128, "Amount can not be lower than -128");
		type = material;
		this.amount = (byte) amount;
	}

	public Material getType() {
		return type;
	}
	
	public int getAmount() {
		return amount;
	}
	
	public void setAmount(int amount) {
		Validate.isFalse(amount > 127, "Amount can not be higher than 127");
		Validate.isFalse(amount < -128, "Amount can not be lower than -128");
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
	 */
	public NBT getMetaAsNBT() {
		if (meta == null) return null;
		return meta.toNBT();
	}

	public boolean isSimilar(ItemStack item) {
		return false;
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
	
	public boolean isSimilarIgnoreDamage(ItemStack item) {
		if (item == null) return false;
		if (item == this) return true;
		if (type != item.getType()) return false; 
		if (item.hasItemMeta() || hasItemMeta()) {
			if (!(item.hasItemMeta() && hasItemMeta())) return false;
			// TODO
		}
		return true;
	}
	
	public boolean isPartOf(ItemStack item) {
		if (item == null) return false;
		if (getType() != item.getType()) return false;
		if (!hasItemMeta() && !item.hasItemMeta()) return true;
		if (isSimilar(item)) return true;
		ItemMeta metapart = getItemMeta();
		ItemMeta metaitem = item.getItemMeta();
		if (metapart.hasDisplayName()) {
			if (!metaitem.hasDisplayName()) return false;
			if (!metaitem.getDisplayName().contains(metapart.getDisplayName())) return false;
		}
		if (metapart.hasEnchants()) {
			Map<Enchantment, Integer> enchs = metapart.getEnchants();
			for (Enchantment ench : enchs.keySet()) {
				if (metaitem.getEnchantLevel(ench) < enchs.get(ench)) return false;
			} return false;
		}
		if (metapart.hasLore()) {
			if (!metaitem.hasLore()) return false;
			return metaitem.getLore().equals(metapart.getLore());
		}
		if (metapart.hasCustomModelData()) {
			if (!metaitem.hasCustomModelData()) return false;
			if (metapart.getCustomModelData() != metaitem.getCustomModelData()) return false;
		}
		if (!metapart.getItemFlags().isEmpty()) {
			for (ItemFlag f : metapart.getItemFlags()) {
				if (!metaitem.hasItemFlag(f)) return false;
			}
		}
		if (metapart.hasAttributeModifiers()) {
			if (!metaitem.hasAttributeModifiers()) return false;
			for (Attribute ab : metapart.getAttributeModifiers().keySet()) {
				List<AttributeModifier> iteml = metaitem.getAttributeModifiers(ab);
				if (iteml == null) return false;
				List<AttributeModifier> partl = metapart.getAttributeModifiers(ab);
				for (AttributeModifier am : partl) {
					if (!iteml.contains(am)) return false;
				}
			}
		}
		Set<ItemFlag> flags = metapart.getItemFlags();
		if (!flags.isEmpty()) {
			for (ItemFlag flag : flags) {
				if (!metaitem.hasItemFlag(flag)) return false;
			}
		}
		return true;
	}

	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		writer.writeByteTag("Count", (byte) getAmount());
		writer.writeStringTag("id", getType().getNamespacedName());
		if(hasItemMeta()) {
			writer.writeCompoundTag("tag");
			getItemMeta().toNBT(writer, systemData);
			writer.writeEndTag();
		}
	}

	@Override
	public void fromNBT(NBTReader reader) {
		
	}
}
