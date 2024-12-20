package de.atlascore.inventory.meta;

import java.io.IOException;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import de.atlasmc.Material;
import de.atlasmc.NamespacedKey;
import de.atlasmc.attribute.Attribute;
import de.atlasmc.attribute.AttributeModifier;
import de.atlasmc.attribute.AttributeModifier.Operation;
import de.atlasmc.chat.Chat;
import de.atlasmc.chat.ChatUtil;
import de.atlasmc.enchantments.Enchantment;
import de.atlasmc.inventory.EquipmentSlot;
import de.atlasmc.inventory.ItemFlag;
import de.atlasmc.inventory.meta.ItemMeta;
import de.atlasmc.util.map.ArrayListMultimap;
import de.atlasmc.util.map.ListMultimap;
import de.atlasmc.util.map.Multimap;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.AbstractNBTBase;
import de.atlasmc.util.nbt.CustomTagContainer;
import de.atlasmc.util.nbt.NBTFieldContainer;
import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.io.NBTWriter;
import de.atlasmc.util.nbt.tag.NBT;

public class CoreItemMeta extends AbstractNBTBase implements ItemMeta {
	
	protected static final NBTFieldContainer<CoreItemMeta> NBT_FIELDS;
	protected static final CharKey 
	NBT_DISPLAY = CharKey.literal("display"),
	NBT_NAME = CharKey.literal("Name"),
	NBT_LORE = CharKey.literal("Lore"),
	NBT_CAN_DESTROY = CharKey.literal("CanDestroy"),
	NBT_CUSTOM_MODEL_DATA = CharKey.literal("CustomModelData"),
	NBT_UNBREAKABLE = CharKey.literal("Unbreakable"),
	NBT_HIDE_FLAGS = CharKey.literal("HideFlags"),
	NBT_ATLASMC = CharKey.literal("AtlasMC"),
	NBT_ENCHANTS = CharKey.literal("Enchantments"),
	NBT_ID = CharKey.literal("id"),
	NBT_LVL = CharKey.literal("lvl"),
	NBT_ATTRIBUTE_MODIFIERS = CharKey.literal("AttributeModifiers"),
	NBT_ATTRIBUTE_NAME = CharKey.literal("AttibuteName"),
	NBT_AMOUNT = CharKey.literal("Amount"),
	NBT_OPERATION = CharKey.literal("Operation"),
	NBT_UUID = CharKey.literal("UUID"),
	NBT_SLOT = CharKey.literal("Slot");
			
	static {
		NBT_FIELDS = NBTFieldContainer.newContainer();
		NBT_FIELDS.setField(NBT_CAN_DESTROY, (holder, reader) -> {
			List<Material> canDestroy = holder.getCanDestroy();
			while (reader.getRestPayload() > 0) {
				canDestroy.add(Material.getByName(reader.readStringTag()));
			}
		});
		NBT_FIELDS.setField(NBT_CUSTOM_MODEL_DATA, (holder, reader) -> {
			holder.setCustomModelData(reader.readIntTag());
		});
		NBTFieldContainer<CoreItemMeta> display = NBTFieldContainer.newContainer();
		NBT_FIELDS.setContainer(NBT_DISPLAY, display);
		display.setField(NBT_NAME, (holder, reader) -> {
			holder.setDisplayName(ChatUtil.toChat(reader.readStringTag()));
		});
		display.setField(NBT_LORE, (holder, reader) -> {
			List<Chat> lore = new ArrayList<>(reader.getRestPayload());
			while (reader.getRestPayload() > 0) {
				lore.add(ChatUtil.toChat(reader.readStringTag()));
			}
			holder.setLore(lore);
		});
		NBT_FIELDS.setField(NBT_UNBREAKABLE, (holder, reader) -> {
			holder.setUnbreakable(reader.readByteTag() == 1);
		});
		NBT_FIELDS.setField(NBT_HIDE_FLAGS, (holder, reader) -> {
			holder.flags = reader.readIntTag();
		});
		NBT_FIELDS.setContainer(NBT_ATLASMC).setUnknownFieldHandler((holder, reader) -> {
			holder.getCustomTagContainer().addSystemTag(reader.readNBT());
		});
		NBT_FIELDS.setField(NBT_ENCHANTS, (holder, reader) -> {
			Map<Enchantment, Integer> enchants = ((ItemMeta) holder).getEnchants();
			reader.readNextEntry();
			while (reader.getRestPayload() > 0) {
				Enchantment ench = null;
				int lvl = -1;
				while (reader.getType() != TagType.TAG_END) {
					final CharSequence value = reader.getFieldName();
					if (NBT_ID.equals(value))
						ench = Enchantment.getEnchantment(reader.readStringTag());
					else if (NBT_LVL.equals(value))
						lvl = reader.readShortTag();
					else
						reader.skipTag();
				}
				reader.readNextEntry();
				if (ench == null) continue;
				enchants.put(ench, lvl);
			}
		});
		NBT_FIELDS.setField(NBT_ATTRIBUTE_MODIFIERS, (holder, reader) -> {
			Multimap<Attribute, AttributeModifier> attributes = holder.getAttributeModifiers();
			reader.readNextEntry();
			while (reader.getRestPayload() > 0) {
				Attribute attribute = null;
				double amount = 0;
				NamespacedKey id = null;
				EquipmentSlot slot = null;
				Operation operation = null;
				while (reader.getType() != TagType.TAG_END) {
					final CharSequence value = reader.getFieldName();
					if (NBT_AMOUNT.equals(value))
						amount = reader.readDoubleTag();
					else if (NBT_ATTRIBUTE_NAME.equals(value))
						attribute = Attribute.getByName(reader.readStringTag());
					else if (NBT_OPERATION.equals(value))
						operation = Operation.getByID(reader.readIntTag());
					else if (NBT_ID.equals(value))
						id = reader.readNamespacedKey();
					else if (NBT_SLOT.equals(value))
						slot = EquipmentSlot.valueOf(reader.readStringTag().toUpperCase());
					else
						reader.skipTag();
				}
				reader.readNextEntry();
				attributes.put(attribute, new AttributeModifier(id, amount, operation, slot));
			}
		});
		NBTFieldContainer<CoreItemMeta> atlas = NBTFieldContainer.newContainer();
		NBT_FIELDS.setContainer(NBT_ATLASMC, atlas);
		atlas.setUnknownFieldHandler((holder, reader) -> {
			holder.getCustomTagContainer().addCustomTag(reader.readNBT());
		});
	}
	
	private boolean unbreakable;
	private Chat displayname;
	private List<Chat> lore;
	private int flags;
	private Integer customModelData;
	private Map<Enchantment, Integer> enchants;
	private ListMultimap<Attribute, AttributeModifier> attributes;
	private List<Material> canDestroy;
	private CustomTagContainer customTags;
	
	public CoreItemMeta(Material material) {}
	
	@Override
	public CoreItemMeta clone() {
		CoreItemMeta clone = null;
		try {
			clone = (CoreItemMeta) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		if (clone == null)
			return null;
		if (hasCanDestroy())
			clone.setCanDestroy(new ArrayList<>(canDestroy));
		if (hasAttributeModifiers())
			clone.setAttributeModifiers(new ArrayListMultimap<>(attributes.asMap()));
		if (hasCanDestroy())
			clone.setCanDestroy(new ArrayList<>(canDestroy));
		if (hasCustomTagContainer())
			clone.customTags = customTags.clone();
		if (hasLore())
			clone.setLore(new ArrayList<>(lore));
		return clone;
	}

	@Override
	public boolean addAttributeModifier(Attribute attribute, AttributeModifier modifier) {
		if (attribute == null) 
			throw new IllegalArgumentException("Attribute can not be null!");
		if (modifier == null) 
			throw new IllegalArgumentException("AttributeModifier can not be null!");
		if (attributes == null)
			attributes = new ArrayListMultimap<>();
		return this.attributes.put(attribute, modifier);
	}

	@Override
	public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot) {
		if (slot == null) 
			throw new IllegalArgumentException("EquipmentSlot can not be null!");
		if (attributes == null || attributes.isEmpty()) 
			return Multimap.of();
		Multimap<Attribute, AttributeModifier> map = null;
		for (Attribute a : attributes.keySet()) {
			List<AttributeModifier> mods = attributes.get(a);
			for (AttributeModifier mod : mods) {
				if (mod.getSlot() != slot)
					continue;
				if (map == null)
					map = new ArrayListMultimap<>();
				map.put(a, mod);
			}
		}
		return map != null ? map : Multimap.of(); 
	}

	@Override
	public boolean hasCustomTagContainer() {
		return customTags != null;
	}
	
	@Override
	public CustomTagContainer getCustomTagContainer() {
		if (customTags == null)  
			customTags = new CustomTagContainer();
		return customTags;
	}

	@Override
	public boolean isUnbreakable() {
		return unbreakable;
	}

	@Override
	public boolean hasEnchant(Enchantment ench) {
		if (ench == null) throw new IllegalArgumentException("Enchantment can not be null!");
		if (enchants == null) return false;
		return this.enchants.containsKey(ench);
	}

	@Override
	public boolean hasConflictingEnchant(Enchantment ench) {
		if (ench == null) 
			throw new IllegalArgumentException("Enchantment can not be null!");
		if (!hasEnchants()) 
			return false;
		for (Enchantment e : enchants.keySet()) {
			if (ench.conflictsWith(e)) 
				return true;
		}
		return false;
	}

	@Override
	public boolean removeAttributeModifier(Attribute attribute) {
		if (attribute == null) 
			throw new IllegalArgumentException("Attribute can not be null!");
		if (attributes == null) 
			return false;
		return attributes.remove(attribute) != null;
	}

	@Override
	public boolean removeAttributeModifier(Attribute attribute, AttributeModifier modifier) {
		if (attribute == null) 
			throw new IllegalArgumentException("Attribute can not be null!");
		if (modifier == null) 
			throw new IllegalArgumentException("AttributeModifier can not be null!");
		if (attributes == null) 
			return false;
		List<AttributeModifier> mods = getAttributeModifiers(attribute);
		if (mods == null) 
			return false;
		return mods.remove(modifier);
	}

	@Override
	public boolean removeAttributeModifier(EquipmentSlot slot) {
		if (slot == null) 
			throw new IllegalArgumentException("EquipmentSlot can not be null!");
		if (!hasAttributeModifiers()) 
			return false;
		boolean changes = false;
		for (Attribute a : attributes.keySet()) {
			List<AttributeModifier> mods = attributes.get(a);
			if (mods.isEmpty())
				continue;
			Iterator<AttributeModifier> modIt = mods.iterator();
			while (modIt.hasNext()) {
				AttributeModifier mod = modIt.next();
				if (mod.getSlot() == slot && mods.remove(mod)) {
					changes = true;
				}
			}
		}
		return changes;
	}

	@Override
	public boolean removeEnchant(Enchantment ench) {
		if (ench == null) 
			throw new IllegalArgumentException("Enchant can not be null!");
		if (enchants == null) 
			return false;
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
		if (attributeModifiers == null) 
			throw new IllegalArgumentException("AttributeModifier can not be null!");
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
	public void setDisplayName(Chat name) {
		this.displayname = name;
	}

	@Override
	public void setLore(List<Chat> lore) {
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
	public Chat getDisplayName() {
		return displayname;
	}

	@Override
	public boolean hasEnchants() {
		return enchants != null && !enchants.isEmpty();
	}

	@Override
	public Map<Enchantment, Integer> getEnchants() {
		if (enchants == null) 
			enchants = new HashMap<>();
		return enchants;
	}

	@Override
	public int getEnchantLevel(Enchantment enchantment) {
		if (enchants == null) 
			return 0;
		return this.enchants.get(enchantment);
	}

	@Override
	public boolean hasLore() {
		return lore != null && !lore.isEmpty();
	}

	@Override
	public List<Chat> getLore() {
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
		for (ItemFlag flag : ItemFlag.getValues()) {
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
		if (attributes == null) 
			attributes = new ArrayListMultimap<>();
		return attributes;
	}

	@Override
	public List<AttributeModifier> getAttributeModifiers(Attribute attribute) {
		if (attribute == null)
			throw new IllegalArgumentException("Attribute can not be null!");
		if (attributes == null || attributes.isEmpty()) 
			return List.of();
		List<AttributeModifier> list = attributes.get(attribute);
		return list == null ? List.of() : list;
	}

	@Override
	public boolean hasItemFlags() {
		return flags != 0;
	}

	@Override
	public boolean hasCanDestroy() {
		return canDestroy != null && !canDestroy.isEmpty();
	}

	@Override
	public List<Material> getCanDestroy() {
		if (canDestroy == null) 
			canDestroy = new ArrayList<>();
		return canDestroy;
	}

	@Override
	public void setCanDestroy(List<Material> canDestroy) {
		this.canDestroy = canDestroy;
	}
	
	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		if (writer == null) 
			throw new IllegalArgumentException("NBTWriter can not be null!");
		if (hasCanDestroy()) {
			writer.writeListTag(NBT_CAN_DESTROY, TagType.STRING, canDestroy.size());
			for (Material m : canDestroy) {
				writer.writeStringTag(null, m.getNamespacedKeyRaw());
			}
		}
		if (hasCustomModelData()) 
			writer.writeIntTag(NBT_CUSTOM_MODEL_DATA, customModelData);
		if (hasDisplayCompound()) {
			writer.writeCompoundTag(NBT_DISPLAY);
			writeDisplayCompound(writer, systemData);
			writer.writeEndTag();
		}
		if (hasItemFlags()) 
			writer.writeIntTag(NBT_HIDE_FLAGS, flags);
		if (isUnbreakable()) 
			writer.writeByteTag(NBT_UNBREAKABLE, 1);
		if (hasEnchants()) {
			writer.writeListTag(NBT_ENCHANTS, TagType.COMPOUND, getEnchants().size());
			for (Enchantment ench : getEnchants().keySet()) {
				writer.writeStringTag(NBT_ID, ench.getNamespacedName());
				writer.writeShortTag(NBT_LVL, (short) getEnchantLevel(ench));
				writer.writeEndTag();
			}
		}
		if (hasAttributeModifiers()) {
			writer.writeListTag(NBT_ATTRIBUTE_MODIFIERS, TagType.COMPOUND, 0);
			Multimap<Attribute, AttributeModifier> attributes = getAttributeModifiers();
			for (Attribute attribute : attributes.keySet()) {
				final String rawname = attribute.getName();
				for (AttributeModifier mod : attributes.get(attribute)) {
					writer.writeDoubleTag(NBT_AMOUNT, mod.getAmount());
					writer.writeStringTag(NBT_ATTRIBUTE_NAME, rawname);
					writer.writeIntTag(NBT_OPERATION, mod.getOperation().getID());
					writer.writeStringTag(NBT_SLOT, mod.getSlot().name().toLowerCase());
					writer.writeNamespacedKey(NBT_ID, mod.getID());
					writer.writeEndTag();
				}
			}
		}
		if (hasCustomData()) {
			writeCustomData(writer);
		}
		if (systemData && hasSystemData()) {
			writeSystemData(writer);
		}
	}
	
	/**
	 * Returns true if custom data is present. 
	 * In {@link CustomTagContainer#getCustomTags()} or some child implementation.
	 * @return true if custom data is present
	 */
	protected boolean hasCustomData() {
		return customTags != null && customTags.hasCustomTags();
	}
	
	/**
	 * Writes the custom data of {@link CustomTagContainer#getCustomTags()} and child implementations.
	 * @param writer
	 * @throws IOException
	 */
	protected void writeCustomData(NBTWriter writer) throws IOException {
		if (customTags != null && customTags.hasCustomTags()) {
			for (NBT nbt : customTags.getCustomTags()) {
				writer.writeNBT(nbt);
			}
		}
	}
	
	/**
	 * Returns true if custom system data is present. 
	 * In {@link CustomTagContainer#getSystemTags()} or some child implementation.
	 * @implNote custom system data will not be send to client
	 * @return true if custom data is present
	 */
	protected boolean hasSystemData() {
		return customTags != null && customTags.hasSystemTags();
	}
	
	/**
	 * Writes the custom system data of {@link CustomTagContainer#getSystemTags()} and child implementations.
	 * @param writer
	 * @implNote custom system data will not be send to client. 
	 * This data will be written to a CompoundTag with id {@link #NBT_ATLASMC}
	 * @throws IOException
	 */
	protected void writeSystemData(NBTWriter writer) throws IOException {
		writer.writeCompoundTag(NBT_ATLASMC);
		if (customTags != null && customTags.hasSystemTags()) {
			for (NBT nbt : customTags.getSystemTags()) {
				writer.writeNBT(nbt);
			}
		}
		writer.writeEndTag();
	}
	
	protected boolean hasDisplayCompound() {
		return hasDisplayName() || hasLore();
	}
	
	protected void writeDisplayCompound(NBTWriter writer, boolean systemData) throws IOException {
		if (hasDisplayName()) {
			writer.writeStringTag(NBT_NAME, getDisplayName().toJsonText());
		}
		if (hasLore()) {
			writer.writeListTag(NBT_LORE, TagType.STRING, lore.size());
			for (Chat c : lore) {
				writer.writeStringTag(null, c.toJsonText());
			}
		}
	}
	
	@Override
	protected boolean useCustomTagContainer() {
		return true;
	}
	
	@Override
	protected NBTFieldContainer<? extends CoreItemMeta> getFieldContainerRoot() {
		return NBT_FIELDS;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((attributes == null) ? 0 : attributes.hashCode());
		result = prime * result + ((canDestroy == null) ? 0 : canDestroy.hashCode());
		result = prime * result + ((customModelData == null) ? 0 : customModelData.hashCode());
		result = prime * result + ((customTags == null) ? 0 : customTags.hashCode());
		result = prime * result + ((displayname == null) ? 0 : displayname.hashCode());
		result = prime * result + ((enchants == null) ? 0 : enchants.hashCode());
		result = prime * result + flags;
		result = prime * result + ((lore == null) ? 0 : lore.hashCode());
		result = prime * result + (unbreakable ? 1231 : 1237);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (obj == this)
			return true;
		if (!(obj instanceof ItemMeta))
			return false;
		return isSimilar((ItemMeta) obj, false);
	}

	@Override
	public boolean isSimilar(ItemMeta meta, boolean ignoreDamage) {
		if (meta == null)
			return false;
		if (meta == this)
			return true;
		if (meta.getClass() != getClass())
			return false;
		if (hasAttributeModifiers() != meta.hasAttributeModifiers())
			return false;
		if (hasAttributeModifiers()) {
			Multimap<Attribute, AttributeModifier> metaAttributes = meta.getAttributeModifiers();
			if (!attributes.equals(metaAttributes))
				return false; 
		}
		if (hasCanDestroy() != meta.hasCanDestroy())
			return false;
		if (hasCanDestroy() && !getCanDestroy().equals(meta.getCanDestroy()))
			return false;
		if (getCustomModelData() != meta.getCustomModelData())
			return false;
		if (hasCustomTagContainer() != meta.hasCustomTagContainer())
			return false;
		if (hasCustomTagContainer() && !getCustomTagContainer().equals(meta.getCustomTagContainer()))
			return false;
		if (hasDisplayName() != meta.hasDisplayName())
			return false;
		if (hasDisplayName() && !getDisplayName().equals(meta.getDisplayName()))
			return false;
		if (hasEnchants() != meta.hasEnchants())
			return false;
		if (hasEnchants() && !getEnchants().equals(meta.getEnchants()))
			return false;
		if (getItemFlagsRaw() != meta.getItemFlagsRaw())
			return false;
		if (hasLore() != meta.hasLore())
			return false;
		if (hasLore() && !getLore().equals(meta.getLore()))
			return false;
		if (isUnbreakable() != meta.isUnbreakable())
			return false;
		return true;
	}

	@Override
	public int getItemFlagsRaw() {
		return flags;
	}
}
