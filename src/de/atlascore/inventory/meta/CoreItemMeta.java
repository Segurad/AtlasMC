package de.atlascore.inventory.meta;

import java.io.DataOutput;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import de.atlasmc.Material;
import de.atlasmc.attribute.Attribute;
import de.atlasmc.attribute.AttributeModifier;
import de.atlasmc.enchantments.Enchantment;
import de.atlasmc.inventory.EquipmentSlot;
import de.atlasmc.inventory.ItemFlag;
import de.atlasmc.inventory.meta.ItemMeta;
import de.atlasmc.lang.LanguageHandler;
import de.atlasmc.util.Multimap;
import de.atlasmc.util.Validate;
import de.atlasmc.util.nbt.CompoundTag;
import de.atlasmc.util.nbt.NBT;

public class CoreItemMeta implements ItemMeta {
	
	private boolean unbreakable;
	private String displayname, nameKey;
	private LanguageHandler langHandler;
	private List<String>lore;
	private Set<ItemFlag>flags;
	private Integer costommodeldata;
	private Map<Enchantment, Integer> enchants;
	private Multimap<Attribute, AttributeModifier> attributes;
	private CompoundTag customTags;
	
	public CoreItemMeta(Material material) {

	}
	
	public CoreItemMeta clone() {
		try {
			return (CoreItemMeta) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean addAttributeModifier(Attribute attribute, AttributeModifier modifier) {
		Validate.notNull(attribute, "Attribute can not be null!");
		Validate.notNull(modifier, "AttributeModifier can not be null!");
		return false;
	}

	@Override
	public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot) {
		Validate.notNull(slot, "EquipmentSlot can not be null!");
		// TODO
		for (Attribute a : attributes.keySet()) {
			List<AttributeModifier> mods = attributes.getValues(a);
			Iterator<AttributeModifier> it = mods.iterator();
			while(it.hasNext()) {
				AttributeModifier mod = it.next();
				if (mod.getSlot() == slot) {
					mods.remove(mod);
				}
			}
		}
		return null;
	}

	@Override
	public CompoundTag getCustomTagContainer() {
		if (customTags == null) 
			customTags = new CompoundTag("AtlasCustomTags");
		return customTags;
	}
	
	@Override
	public String getLocalizedName() {
		return hasLanguageHandler() ? langHandler.getDefaultPack().get(nameKey) : null;
	}

	@Override
	public String getLocalizedName(String local) {
		return hasLanguageHandler() ? langHandler.getPack(local).get(nameKey) : null;
	}

	@Override
	public LanguageHandler getLanguageHandler() {
		return langHandler;
	}

	@Override
	public void setLanguageHandler(LanguageHandler handler) {
		this.langHandler = handler;	
	}

	@Override
	public boolean hasLanguageHandler() {
		return langHandler == null;
	}

	@Override
	public boolean hasLocalizedName() {
		return hasLanguageHandler() ? langHandler.getDefaultPack().hasKey(nameKey) : false;
	}
	
	@Override
	public boolean hasLocalizedName(String local) {
		return hasLanguageHandler() ? langHandler.getPack(local).hasKey(nameKey) : false;
	}
	
	@Override
	public boolean hasLocalizedNameKey() {
		return nameKey == null; 
	}
	
	@Override
	public String getLocalizedNameKey() {
		return nameKey;
	}
	
	@Override
	public void setLocalizedNameKey(String key) {
		this.nameKey = key;
	}

	@Override
	public boolean isUnbreakable() {
		return unbreakable;
	}

	@Override
	public boolean hasEnchant(Enchantment ench) {
		Validate.notNull(ench, "Enchant can not be null!");
		return this.enchants.containsKey(ench);
	}

	@Override
	public boolean hasConflictingEnchant(Enchantment ench) {
		Validate.notNull(ench, "Enchant can not be null!");
		for (Enchantment e : enchants.keySet()) {
			if (ench.conflictsWith(e)) return true;
		}
		return false;
	}

	@Override
	public boolean removeAttributeModifier(Attribute attribute) {
		Validate.notNull(attribute, "Attribute can not be null!");
		return attributes.remove(attribute);
	}

	@Override
	public boolean removeAttributeModifier(Attribute attribute, AttributeModifier modifier) {
		Validate.notNull(attribute, "Attribute can not be null!");
		Validate.notNull(modifier, "AttributeModifier can not be null!");
		return getAttributeModifiers(attribute).remove(modifier);
	}

	@Override
	public boolean removeAttributeModifier(EquipmentSlot slot) {
		Validate.notNull(slot, "EquipmentSlot can not be null!");
		boolean changes = false;
		for (Attribute a : attributes.keySet()) {
			List<AttributeModifier> mods = attributes.getValues(a);
			Iterator<AttributeModifier> it = mods.iterator();
			while(it.hasNext()) {
				AttributeModifier mod = it.next();
				if (mod.getSlot() == slot) {
					mods.remove(mod);
					changes = true;
				}
			}
		}
		return changes;
	}

	@Override
	public boolean removeEnchant(Enchantment ench) {
		Validate.notNull(ench, "Enchant can not be null!");
		return this.enchants.remove(ench) != null;
	}

	@Override
	public void removeItemFlags(ItemFlag... itemflags) {
		Validate.notNull(itemflags, "ItemFlag can not be null!");
		for(ItemFlag flag : itemflags) {
			this.flags.remove(flag);
		}
	}

	@Override
	public void setAttributeModifiers(Multimap<Attribute, AttributeModifier> attributeModifiers) {
		Validate.notNull(attributeModifiers, "AttributeModifier can not be null!");
		this.attributes = attributeModifiers;
	}

	@Override
	public void setCustomModelData(Integer data) {
		this.costommodeldata = data;
	}

	@Override
	public void setUnbreakable(boolean unbreakable) {
		this.unbreakable = unbreakable;
	}

	@Override
	public NBT toNBT() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DataOutput toNBT(DataOutput output) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setDisplayName(String name) {
		this.displayname = name;
	}

	@Override
	public void setLore(List<String> lore) {
		this.lore = lore;
	}

	@Override
	public void addEnchant(Enchantment enchantment, int level) {
		this.enchants.put(enchantment, level);
	}

	@Override
	public void addItemFlags(ItemFlag... flags) {
		for (ItemFlag flag : flags) {
			this.flags.add(flag);
		}
	}

	@Override
	public boolean hasDisplayName() {
		return displayname != null;
	}

	@Override
	public String getDisplayName() {
		return displayname;
	}

	@Override
	public boolean hasEnchants() {
		return !this.enchants.isEmpty();
	}

	@Override
	public Map<Enchantment, Integer> getEnchants() {
		return enchants;
	}

	@Override
	public int getEnchantLevel(Enchantment enchantment) {
		return this.enchants.get(enchantment);
	}

	@Override
	public boolean hasLore() {
		return lore != null;
	}

	@Override
	public List<String> getLore() {
		return lore;
	}

	@Override
	public boolean hasCustomModelData() {
		return costommodeldata != null;
	}

	@Override
	public int getCustomModelData() {
		return costommodeldata;
	}

	@Override
	public Set<ItemFlag> getItemFlags() {
		return flags;
	}

	@Override
	public boolean hasItemFlag(ItemFlag flag) {
		return this.flags.contains(flag);
	}

	@Override
	public boolean hasAttributeModifiers() {
		return !attributes.isEmpty();
	}

	@Override
	public Multimap<Attribute, AttributeModifier> getAttributeModifiers() {
		return attributes;
	}

	@Override
	public List<AttributeModifier> getAttributeModifiers(Attribute attribute) {
		return attributes.getValues(attribute);
	}
}
