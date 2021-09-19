package de.atlasmc.inventory.meta;

import java.util.List;
import java.util.Map;
import java.util.Set;

import de.atlasmc.Material;
import de.atlasmc.attribute.Attribute;
import de.atlasmc.attribute.AttributeModifier;
import de.atlasmc.chat.component.ChatComponent;
import de.atlasmc.enchantments.Enchantment;
import de.atlasmc.inventory.EquipmentSlot;
import de.atlasmc.inventory.ItemFlag;
import de.atlasmc.inventory.meta.lore.Lore;
import de.atlasmc.util.map.Multimap;
import de.atlasmc.util.nbt.CustomTagContainer;
import de.atlasmc.util.nbt.NBTHolder;

public interface ItemMeta extends Cloneable, NBTHolder {
	
	public ItemMeta clone();
	public boolean addAttributeModifier(Attribute attribute, AttributeModifier modifier);
	public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot);
	public boolean hasCustomTagContainer();
	public CustomTagContainer getCustomTagContainer();
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

	public void setDisplayName(ChatComponent name);

	public void setLore(Lore lore);

	public void addEnchant(Enchantment enchantment, int level);

	public void addItemFlags(ItemFlag... flags);

	public boolean hasDisplayName();

	public ChatComponent getDisplayName();

	public boolean hasEnchants();

	public Map<Enchantment, Integer> getEnchants();

	public int getEnchantLevel(Enchantment enchantment);

	public boolean hasLore();

	public Lore getLore();

	public boolean hasCustomModelData();

	public int getCustomModelData();

	public Set<ItemFlag> getItemFlags();

	public boolean hasItemFlag(ItemFlag flag);

	public boolean hasItemFlags();
	
	public boolean hasAttributeModifiers();

	public Multimap<Attribute, AttributeModifier> getAttributeModifiers();

	public List<AttributeModifier> getAttributeModifiers(Attribute attribute);
	
	public boolean hasCanDestroy();
	public List<Material> getCanDestroy();
	public void setCanDestroy(List<Material> material);
}
