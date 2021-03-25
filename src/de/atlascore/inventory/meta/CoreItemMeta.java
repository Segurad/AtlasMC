package de.atlascore.inventory.meta;

import java.io.IOException;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import de.atlasmc.Material;
import de.atlasmc.attribute.Attribute;
import de.atlasmc.attribute.AttributeModifier;
import de.atlasmc.chat.LanguageHandler;
import de.atlasmc.chat.MessageUtil;
import de.atlasmc.enchantments.Enchantment;
import de.atlasmc.inventory.EquipmentSlot;
import de.atlasmc.inventory.ItemFlag;
import de.atlasmc.inventory.meta.ItemMeta;
import de.atlasmc.inventory.meta.lore.Lore;
import de.atlasmc.util.Validate;
import de.atlasmc.util.map.ArrayListMultimap;
import de.atlasmc.util.map.ListMultimap;
import de.atlasmc.util.map.Multimap;
import de.atlasmc.util.nbt.CompoundTag;
import de.atlasmc.util.nbt.NBTReader;
import de.atlasmc.util.nbt.NBTWriter;
import de.atlasmc.util.nbt.TagType;

public class CoreItemMeta implements ItemMeta {
	
	private boolean unbreakable;
	private String displayname, nameKey;
	private LanguageHandler langHandler;
	private Lore lore;
	private int flags;
	private Integer customModelData;
	private Map<Enchantment, Integer> enchants;
	private ListMultimap<Attribute, AttributeModifier> attributes;
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
		if (attributes == null)
			attributes = new ArrayListMultimap<>();
		return this.attributes.put(attribute, modifier);
	}

	@Override
	public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot) {
		Validate.notNull(slot, "EquipmentSlot can not be null!");
		Multimap<Attribute, AttributeModifier> map = new ArrayListMultimap<>();
		if (attributes == null) return map;
		for (Attribute a : attributes.keySet()) {
			List<AttributeModifier> mods = attributes.get(a);
			for (AttributeModifier mod : mods) {
				if (mod.getSlot() == slot)
					map.put(a, mod);
			}
		}
		return map;
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
		return langHandler != null;
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
		if (enchants == null) return false;
		return this.enchants.containsKey(ench);
	}

	@Override
	public boolean hasConflictingEnchant(Enchantment ench) {
		Validate.notNull(ench, "Enchant can not be null!");
		if (enchants == null) return false;
		for (Enchantment e : enchants.keySet()) {
			if (ench.conflictsWith(e)) return true;
		}
		return false;
	}

	@Override
	public boolean removeAttributeModifier(Attribute attribute) {
		Validate.notNull(attribute, "Attribute can not be null!");
		if (attributes == null) return false;
		return attributes.remove(attribute) != null;
	}

	@Override
	public boolean removeAttributeModifier(Attribute attribute, AttributeModifier modifier) {
		Validate.notNull(attribute, "Attribute can not be null!");
		Validate.notNull(modifier, "AttributeModifier can not be null!");
		if (attributes == null) return false;
		List<AttributeModifier> mods = getAttributeModifiers(attribute);
		if (mods == null) return false;
		return mods.remove(modifier);
	}

	@Override
	public boolean removeAttributeModifier(EquipmentSlot slot) {
		Validate.notNull(slot, "EquipmentSlot can not be null!");
		if (attributes == null) return false;
		boolean changes = false;
		for (Attribute a : attributes.keySet()) {
			List<AttributeModifier> mods = attributes.get(a);
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
		if (enchants == null) return false;
		return this.enchants.remove(ench) != null;
	}

	@Override
	public void removeItemFlags(ItemFlag... itemflags) {
		for(ItemFlag flag : itemflags) {
			this.flags &= (getBitModifier(flag) ^ 0xFFFFFFFF);
		}
	}

	@Override
	public void setAttributeModifiers(Multimap<Attribute, AttributeModifier> attributeModifiers) {
		Validate.notNull(attributeModifiers, "AttributeModifier can not be null!");
		this.attributes.clear();
		this.attributes.putAll(attributeModifiers);
	}

	@Override
	public void setCustomModelData(Integer data) {
		this.customModelData = data;
	}

	@Override
	public void setUnbreakable(boolean unbreakable) {
		this.unbreakable = unbreakable;
	}

	@Override
	public void toNBT(NBTWriter writer, String local, boolean systemData) throws IOException {
		Validate.notNull(writer, "NBTWriter can not be null!");
		writer.writeCompoundTag(null);
		if (hasCustomModelData()) writer.writeIntTag("CustomModelData", customModelData);
		if (hasDisplayName() || hasLore() || hasLocalizedName()) {
			writer.writeCompoundTag("display");
			String name = getLocalizedName(local);
			if (name == null) {
				writer.writeStringTag("Name", MessageUtil.formatMessage(name));
			} else if (hasDisplayName()) {
				writer.writeStringTag("Name", MessageUtil.formatMessage(displayname));
			}
			if (hasLore()) {
				writer.writeListTag("Lore", TagType.STRING, lore.countLines());
				for (String s : lore) {
					writer.writeStringTag(null, MessageUtil.formatMessage(s));
				}
			}
			writer.writeEndTag();
		}
		if (hasEnchants()) {
			writer.writeListTag("Enchantments", TagType.COMPOUND, getEnchants().size());
			for (Enchantment ench : getEnchants().keySet()) {
				writer.writeCompoundTag(null);
				writer.writeStringTag("id", ench.getNamespacedName().toLowerCase());
				writer.writeShortTag("lvl", (short) getEnchantLevel(ench));
				writer.writeEndTag();
			}
		}
		if (hasAttributeModifiers()) {
			writer.writeListTag("AttributeModifiers", TagType.COMPOUND, 0);
			Multimap<Attribute, AttributeModifier> attributes = getAttributeModifiers();
			for (Attribute attribute : attributes.keySet()) {
				final String namespaced = attribute.getNamespacedName().toLowerCase();
				final String name = attribute.name();
				for (AttributeModifier mod : attributes.get(attribute)) {
					writer.writeCompoundTag(null);
					writer.writeDoubleTag("Amount", mod.getAmount());
					writer.writeStringTag("AttributeName", namespaced);
					writer.writeStringTag("Name", name);
					writer.writeIntTag("Operation", mod.getOperation().ordinal());
					writer.writeStringTag("Slot", mod.getSlot().name().toLowerCase());
					final UUID uuid = mod.getUUID();
					writer.writeIntArrayTag("UUID", new int[] {
							(int) (uuid.getMostSignificantBits()>>32),
							(int) uuid.getMostSignificantBits(),
							(int) (uuid.getLeastSignificantBits()>>32),
							(int) uuid.getLeastSignificantBits()
					});
				}
			}
		}
		if (hasItemFlags()) writer.writeIntTag("HideFlags", flags);
		if (isUnbreakable()) writer.writeByteTag("Unbreakable", 1);
		writer.writeEndTag();
	}
	
	@Override
	public void fromNBT(NBTReader reader) {
		Validate.notNull(reader, "NBTReader can not be null!");
		// TODO:
	}

	@Override
	public void setDisplayName(String name) {
		this.displayname = name;
	}

	@Override
	public void setLore(Lore lore) {
		this.lore = lore;
	}

	@Override
	public void addEnchant(Enchantment enchantment, int level) {
		this.enchants.put(enchantment, level);
	}

	@Override
	public void addItemFlags(ItemFlag... flags) {
		for (ItemFlag flag : flags) {
			this.flags |= getBitModifier(flag);
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
		if (enchants == null) return 0;
		return this.enchants.get(enchantment);
	}

	@Override
	public boolean hasLore() {
		return lore != null && !lore.isEmpty();
	}

	@Override
	public Lore getLore() {
		return lore;
	}

	@Override
	public boolean hasCustomModelData() {
		return customModelData != null;
	}

	@Override
	public int getCustomModelData() {
		return customModelData;
	}

	@Override
	public Set<ItemFlag> getItemFlags() {
		Set<ItemFlag> flags = EnumSet.noneOf(ItemFlag.class);
		for (ItemFlag flag : ItemFlag.values()) {
			if (hasItemFlag(flag)) flags.add(flag);
		}
		return flags;
	}

	@Override
	public boolean hasItemFlag(ItemFlag flag) {
		int value = getBitModifier(flag);
		return (this.flags & value) == value;
	}
	
	private byte getBitModifier(ItemFlag flag) { 
		return (byte)(1 << flag.ordinal()); 
	}

	@Override
	public boolean hasAttributeModifiers() {
		return attributes != null && !attributes.isEmpty();
	}

	@Override
	public Multimap<Attribute, AttributeModifier> getAttributeModifiers() {
		if (attributes == null) attributes = new ArrayListMultimap<>();
		return attributes;
	}

	@Override
	public List<AttributeModifier> getAttributeModifiers(Attribute attribute) {
		if (attributes == null) return null;
		return attributes.get(attribute);
	}

	@Override
	public boolean hasItemFlags() {
		return flags != 0;
	}
}
