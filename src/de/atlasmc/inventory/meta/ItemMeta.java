package de.atlasmc.inventory.meta;

import java.util.List;
import java.util.Map;
import java.util.Set;

import de.atlasmc.Material;
import de.atlasmc.attribute.Attribute;
import de.atlasmc.attribute.AttributeModifier;
import de.atlasmc.attribute.Attributeable;
import de.atlasmc.chat.Chat;
import de.atlasmc.enchantments.Enchantment;
import de.atlasmc.inventory.EquipmentSlot;
import de.atlasmc.inventory.ItemFlag;
import de.atlasmc.util.map.Multimap;
import de.atlasmc.util.nbt.CustomTagContainer;
import de.atlasmc.util.nbt.NBTHolder;

public interface ItemMeta extends Cloneable, NBTHolder, Attributeable {
	
	ItemMeta clone();
	
	boolean hasCustomTagContainer();
	
	CustomTagContainer getCustomTagContainer();
	
	boolean isUnbreakable();
	
	boolean hasEnchant(Enchantment ench);
	
	boolean hasConflictingEnchant(Enchantment enchantment);
	
	boolean removeEnchant(Enchantment ench);
	
	void removeItemFlags(ItemFlag... itemflags);
	
	void setCustomModelData(Integer data);
	
	void setUnbreakable(boolean unbreakable);

	void setDisplayName(Chat name);

	void setLore(List<Chat> lore);

	void addEnchant(Enchantment enchantment, int level);

	void addItemFlags(ItemFlag... flags);

	boolean hasDisplayName();

	Chat getDisplayName();

	boolean hasEnchants();

	Map<Enchantment, Integer> getEnchants();

	int getEnchantLevel(Enchantment enchantment);

	boolean hasLore();

	List<Chat> getLore();

	boolean hasCustomModelData();

	int getCustomModelData();

	Set<ItemFlag> getItemFlags();
	
	int getItemFlagsRaw();

	boolean hasItemFlag(ItemFlag flag);

	boolean hasItemFlags();

	List<AttributeModifier> getAttributeModifiers(Attribute attribute);
	
	boolean hasCanDestroy();
	
	List<Material> getCanDestroy();
	
	void setCanDestroy(List<Material> material);
	
	boolean removeAttributeModifier(EquipmentSlot slot);
	
	Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot);

	boolean isSimilar(ItemMeta meta, boolean ignoreDamage);

}
