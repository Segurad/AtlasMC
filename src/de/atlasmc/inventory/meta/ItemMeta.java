package de.atlasmc.inventory.meta;

import java.io.DataOutput;
import java.util.List;
import java.util.Map;

import de.atlasmc.attribute.Attribute;
import de.atlasmc.attribute.AttributeModifier;
import de.atlasmc.enchantments.Enchantment;
import de.atlasmc.inventory.ItemFlag;
import de.atlasmc.util.nbt.NBT;

public interface ItemMeta extends Cloneable {

	public ItemMeta clone();

	public NBT toNBT();
	public DataOutput toNBT(DataOutput output);

	public void setDisplayName(String name);

	public void setLore(List<String> lore);

	public void addEnchant(Enchantment enchantment, int level);

	public void addItemFlags(ItemFlag... flags);

	public boolean hasDisplayName();

	public String getDisplayName();

	public boolean hasEnchants();

	public Map<Enchantment, Integer> getEnchants();

	public int getEnchantLevel(Enchantment enchantment);

	public boolean hasLore();

	public List<String> getLore();

	public boolean hasCustomModelData();

	public int getCustomModelData();

	public List<ItemFlag> getItemFlags();

	public boolean hasItemFlag(ItemFlag flag);

	public boolean hasAttributeModifiers();

	public Map<Attribute, List<AttributeModifier>> getAttributeModifiers();

	public List<AttributeModifier> getAttributeModifiers(Attribute attribute);
}
