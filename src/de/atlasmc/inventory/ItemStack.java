package de.atlasmc.inventory;

import java.util.List;
import java.util.Map;
import de.atlasmc.Material;
import de.atlasmc.attribute.Attribute;
import de.atlasmc.attribute.AttributeModifier;
import de.atlasmc.enchantments.Enchantment;
import de.atlasmc.inventory.meta.ItemMeta;
import de.atlasmc.util.nbt.NBT;

public class ItemStack {

	private int amount;
	private Material type;
	private ItemMeta meta;
	
	public ItemStack(Material mat, byte amount2) {
		// TODO Auto-generated constructor stub
	}

	public ItemStack(Material material) {
		// TODO Auto-generated constructor stub
	}

	public ItemStack(Material material, int amount) {
		// TODO Auto-generated constructor stub
	}

	public Material getType() {
		return type;
	}
	
	public int getAmount() {
		return amount;
	}
	
	public void setAmount(int amount) {
		if (amount > 127) throw new IllegalArgumentException("Amount can not be higher than 127");
		if (amount < -128) throw new IllegalArgumentException("Amount can not be lower than -128");
		this.amount = amount;
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
		// TODO Auto-generated method stub
		return false;
	}
	
	public ItemStack clone() {
		return null;
	}

	public int getMaxStackSize() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public boolean isSimilarIgnoreDamage(ItemStack item) {
		if (item == null) return false;
		if (item == this) return true;
		if (type != item.getType()) return false; 
		if (item.hasItemMeta() || hasItemMeta()) {
			
		}
		return true;
	}
	
	public boolean isPartOf(ItemStack part, ItemStack item) {
		if (part == null || item == null) return false;
		if (part.getType() != item.getType()) return false;
		if (!part.hasItemMeta() && !item.hasItemMeta()) return true;
		if (part.isSimilar(item)) return true;
		ItemMeta metapart = part.getItemMeta();
		ItemMeta metaitem = part.getItemMeta();
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
			List<String> lore = metaitem.getLore();
			for (String s : metapart.getLore()) {
				if (!lore.contains(s)) return false;
			}
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
		List<ItemFlag> flags = metapart.getItemFlags();
		if (!flags.isEmpty()) {
			for (ItemFlag flag : flags) {
				if (!metaitem.hasItemFlag(flag)) return false;
			}
		}
		return true;
	}
}
