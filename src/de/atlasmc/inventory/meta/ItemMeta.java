package de.atlasmc.inventory.meta;

import java.util.List;
import java.util.Map;
import java.util.Set;

import de.atlasmc.attribute.Attribute;
import de.atlasmc.attribute.AttributeModifier;
import de.atlasmc.chat.LanguageHandler;
import de.atlasmc.enchantments.Enchantment;
import de.atlasmc.inventory.EquipmentSlot;
import de.atlasmc.inventory.ItemFlag;
import de.atlasmc.util.map.Multimap;
import de.atlasmc.util.nbt.CompoundTag;
import de.atlasmc.util.nbt.NBT;

public interface ItemMeta extends Cloneable {

	public ItemMeta clone();
	
	public boolean addAttributeModifier(Attribute attribute, AttributeModifier modifier);
	public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot);
	public CompoundTag getCustomTagContainer();
	public String getLocalizedName();
	public String getLocalizedName(String local);
	public LanguageHandler getLanguageHandler();
	public void setLanguageHandler(LanguageHandler handler);
	public boolean hasLanguageHandler();
	public boolean hasLocalizedName();
	public boolean hasLocalizedName(String local);
	public boolean hasLocalizedNameKey();
	public String getLocalizedNameKey();
	public void setLocalizedNameKey(String key);
	public boolean isUnbreakable();
	public boolean hasEnchant(Enchantment ench);
	public boolean hasConflictingEnchant(Enchantment enchantment);
	public boolean removeAttributeModifier(Attribute attribute);
	public boolean removeAttributeModifier(Attribute attribute, AttributeModifier modifier);
	public boolean removeAttributeModifier(EquipmentSlot slot);
	public boolean removeEnchant(Enchantment ench);
	public void removeItemFlags(ItemFlag... itemflags);
	public void setAttributeModifiers(Multimap<Attribute, AttributeModifier> attributeModifiers);
	public void setCustomModelData(Integer data);
	public void setUnbreakable(boolean unbreakable);

	public default NBT toNBT() {
		return toNBT("default");
	}
	public NBT toNBT(String local);

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

	public Set<ItemFlag> getItemFlags();

	public boolean hasItemFlag(ItemFlag flag);

	public boolean hasAttributeModifiers();

	public Multimap<Attribute, AttributeModifier> getAttributeModifiers();

	public List<AttributeModifier> getAttributeModifiers(Attribute attribute);
}
