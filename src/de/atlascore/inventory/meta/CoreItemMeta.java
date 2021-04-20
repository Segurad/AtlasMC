package de.atlascore.inventory.meta;

import java.io.IOException;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import de.atlasmc.Material;
import de.atlasmc.attribute.Attribute;
import de.atlasmc.attribute.AttributeModifier;
import de.atlasmc.attribute.AttributeModifier.Operation;
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
import de.atlasmc.util.nbt.CustomTagContainer;
import de.atlasmc.util.nbt.NBT;
import de.atlasmc.util.nbt.NBTException;
import de.atlasmc.util.nbt.NBTField;
import de.atlasmc.util.nbt.NBTFieldContainer;
import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;

public class CoreItemMeta implements ItemMeta {
	
	private boolean unbreakable;
	private String displayname;
	private Lore lore;
	private int flags;
	private Integer customModelData;
	private Map<Enchantment, Integer> enchants;
	private ListMultimap<Attribute, AttributeModifier> attributes;
	private List<Material> canDestroy;
	private CustomTagContainer customTags;
	protected static final NBTFieldContainer NBT_FIELDS;
	protected static final String 
			DISPLAY = "display",
			NAME = "Name",
			LORE = "Lore",
			CAN_DESTROY = "CanDestroy",
			CUSTOM_MODEL_DATA = "CustomModelData",
			UNBREAKABLE = "Unbreakable",
			HIDE_FLAGS = "HideFlags",
			ATLASMC = "AtlasMC",
			ENCHANTS = "Enchantments",
			ID = "id",
			LVL = "lvl",
			ATTRIBUTE_MODIFIERS = "AttributeModifiers",
			ATTRIBUTE_NAME = "AttibuteName",
			AMOUNT = "Amount",
			OPERATION = "Operation",
			NBT_UUID = "UUID",
			SLOT = "Slot";
			
	static {
		NBT_FIELDS = new NBTFieldContainer();
		NBT_FIELDS.setField(CAN_DESTROY, (holder, reader) -> {
			List<Material> canDestroy = ((ItemMeta) holder).getCanDestroy();
			while (reader.getRestPayload() > 0) {
				canDestroy.add(Material.getMaterial(reader.readStringTag()));
			}
		});
		NBT_FIELDS.setField(CUSTOM_MODEL_DATA, (holder, reader) -> {
			((ItemMeta) holder).setCustomModelData(reader.readIntTag());
		});
		NBTFieldContainer display = new NBTFieldContainer();
		NBT_FIELDS.setContainer(DISPLAY, display);
		display.setField(NAME, (holder, reader) -> {
			((ItemMeta) holder).setDisplayName(reader.readStringTag());
		});
		display.setField(LORE, (holder, reader) -> {
			Lore lore = ((ItemMeta) holder).getLore();
			while (reader.getRestPayload() > 0) {
				lore.addLine(reader.readStringTag());
			}
		});
		NBT_FIELDS.setField(UNBREAKABLE, (holder, reader) -> {
			((ItemMeta) holder).setUnbreakable(reader.readByteTag() == 1);
		});
		NBT_FIELDS.setField(HIDE_FLAGS, (holder, reader) -> {
			((CoreItemMeta) holder).flags = reader.readIntTag();
		});
		NBT_FIELDS.setField(ATLASMC, (holder, reader) -> {
			((ItemMeta) holder).getCustomTagContainer().addSystemTag(reader.readNBT());
		});
		NBT_FIELDS.setField(ENCHANTS, (holder, reader) -> {
			Map<Enchantment, Integer> enchants = ((ItemMeta) holder).getEnchants();
			while (reader.getRestPayload() > 0) {
				Enchantment ench = null;
				int lvl = -1;
				for (int i = 0; i < 2; i++) {
					if (reader.getFieldName().equals(ID)) {
						ench = Enchantment.getEnchantment(reader.readStringTag());
					} else if (reader.getFieldName().equals(LVL)) {
						lvl = reader.readShortTag();
					} else throw new NBTException("Unknown Enchantment Field: " + reader.getFieldName());
				}
				if (reader.getType() != TagType.TAG_END)
					throw new NBTException("Error while reading Enchantment Field! Expected TAG_END but read: " + reader.getType().name());
				reader.readNextEntry();
				if (ench == null) continue;
				enchants.put(ench, lvl);
			}
		});
		NBT_FIELDS.setField(ATTRIBUTE_MODIFIERS, (holder, reader) -> {
			Multimap<Attribute, AttributeModifier> attributes = ((ItemMeta) holder).getAttributeModifiers();
			while (reader.getRestPayload() > 0) {
				Attribute attribute = null;
				double amount = 0;
				String name = null;
				UUID uuid = null;
				EquipmentSlot slot = null;
				Operation operation = null;
				for (int i = 0; i < 5; i++) {
					final String field = reader.getFieldName();
					if (field.equals(AMOUNT)) {
						amount = reader.readDoubleTag();
					} else if (field.equals(ATTRIBUTE_NAME)) {
						attribute = Attribute.getByName(reader.readStringTag());
					} else if (field.equals(NAME)) {
						name = reader.readStringTag();
					} else if (field.equals(OPERATION)) {
						operation = Operation.byID(reader.readIntTag());
					} else if (field.equals(NBT_UUID)) {
						uuid = reader.readUUID();
					} else if (field.equals(SLOT)) {
						slot = EquipmentSlot.valueOf(reader.readStringTag().toUpperCase().replace("_", ""));
					} else throw new NBTException("Unknown Attribute Field: " + reader.getFieldName());
				}
				if (reader.getType() != TagType.TAG_END)
					throw new NBTException("Error while reading Attriubte Field! Expected TAG_END but read: " + reader.getType().name());
				reader.readNextEntry();
				attributes.put(attribute, new AttributeModifier(uuid, name, amount, operation, slot));
			}
		});
		NBTFieldContainer atlas = new NBTFieldContainer();
		NBT_FIELDS.setContainer(ATLASMC, atlas);
		atlas.setUnknownFieldHandler((holder, reader) -> {
			((ItemMeta) holder).getCustomTagContainer().addCustomTag(reader.readNBT());
		});
	}
	
	public CoreItemMeta(Material material) {
		
	}
	
	@Override
	public CoreItemMeta clone() {
		CoreItemMeta clone = null;
		try {
			clone = (CoreItemMeta) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		if (clone != null) {
			if (hasCanDestroy()) {
				clone.getCanDestroy().addAll(canDestroy);
			}
			if (hasAttributeModifiers()) {
				Multimap<Attribute, AttributeModifier> attrs = clone.getAttributeModifiers();
				attrs.putAll(attributes);
			}
			if (hasCustomTagContainer()) {
				clone.customTags = null; // TODO
			}
		}
		return clone;
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
	public boolean hasCustomTagContainer() {
		return customTags != null;
	}
	
	@Override
	public CustomTagContainer getCustomTagContainer() {
		if (customTags == null)  customTags = new CustomTagContainer();
		return customTags;
	}

	@Override
	public boolean isUnbreakable() {
		return unbreakable;
	}

	@Override
	public boolean hasEnchant(Enchantment ench) {
		Validate.notNull(ench, "Enchantment can not be null!");
		if (enchants == null) return false;
		return this.enchants.containsKey(ench);
	}

	@Override
	public boolean hasConflictingEnchant(Enchantment ench) {
		Validate.notNull(ench, "Enchantment can not be null!");
		if (!hasEnchants()) return false;
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
		return enchants != null && !enchants.isEmpty();
	}

	@Override
	public Map<Enchantment, Integer> getEnchants() {
		if (enchants == null) enchants = new HashMap<>();
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

	@Override
	public boolean hasCanDestroy() {
		return canDestroy != null && !canDestroy.isEmpty();
	}

	@Override
	public List<Material> getCanDestroy() {
		if (canDestroy == null) canDestroy = new ArrayList<Material>();
		return canDestroy;
	}

	@Override
	public void setCanDestroy(List<Material> canDestroy) {
		this.canDestroy = canDestroy;
	}
	
	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		Validate.notNull(writer, "NBTWriter can not be null!");
		if (hasCanDestroy()) {
			writer.writeListTag(CAN_DESTROY, TagType.STRING, canDestroy.size());
			for (Material m : canDestroy) {
				writer.writeStringTag(null, m.getNamespacedName());
			}
		}
		if (hasCustomModelData()) writer.writeIntTag(CUSTOM_MODEL_DATA, customModelData);
		if (hasDisplayCompound()) {
			writer.writeCompoundTag(DISPLAY);
			writeDisplayCompound(writer, systemData);
			writer.writeEndTag();
		}
		if (hasItemFlags()) writer.writeIntTag(HIDE_FLAGS, flags);
		if (isUnbreakable()) writer.writeByteTag(UNBREAKABLE, 1);
		if (hasEnchants()) {
			writer.writeListTag(ENCHANTS, TagType.COMPOUND, getEnchants().size());
			for (Enchantment ench : getEnchants().keySet()) {
				writer.writeStringTag(ID, ench.getNamespacedName());
				writer.writeShortTag(LVL, (short) getEnchantLevel(ench));
				writer.writeEndTag();
			}
		}
		if (hasAttributeModifiers()) {
			writer.writeListTag(ATTRIBUTE_MODIFIERS, TagType.COMPOUND, 0);
			Multimap<Attribute, AttributeModifier> attributes = getAttributeModifiers();
			for (Attribute attribute : attributes.keySet()) {
				final String rawname = attribute.getRawName();
				for (AttributeModifier mod : attributes.get(attribute)) {
					writer.writeDoubleTag(AMOUNT, mod.getAmount());
					writer.writeStringTag(ATTRIBUTE_NAME, rawname);
					writer.writeStringTag(NAME, mod.getName());
					writer.writeIntTag(OPERATION, mod.getOperation().ordinal());
					writer.writeStringTag(SLOT, mod.getSlot().name().toLowerCase());
					writer.writeUUID(NBT_UUID, mod.getUUID());
					writer.writeEndTag();
				}
			}
		}
		if (hasCustomTagContainer()) {
			CustomTagContainer container = getCustomTagContainer();
			if (container.hasCustomTags()) {
				for (NBT nbt : container.getCustomTags()) {
					writer.writeNBT(nbt);
				}
			}
		}
		if (hasSystemDataCompound()) {
			writer.writeCompoundTag(ATLASMC);
			writeSystemDataCompound(writer);
			writer.writeEndTag();
		}
	}
	
	@Override
	public void fromNBT(NBTReader reader) throws IOException {
		Validate.notNull(reader, "NBTReader can not be null!");
		final int depth = reader.getDepth();
		final ArrayList<NBTFieldContainer> containers = new ArrayList<NBTFieldContainer>();
		NBTFieldContainer highestContainer = NBT_FIELDS;
		while (depth <= reader.getDepth()) {
			TagType type = reader.getType();
			if (type == TagType.TAG_END) {
				if (containers.isEmpty()) break;
				highestContainer = containers.remove(containers.size()-1);
				reader.readNextEntry();
				continue;
			}
			final String key = reader.getFieldName();
			NBTFieldContainer container = highestContainer.getContainer(key);
			if (container != null) {
				containers.add(highestContainer);
				highestContainer = container;
				continue;
			}
			NBTField field = highestContainer.getField(key);
			if (field != null) {
				field.setField(this, reader);
			} else if (highestContainer.hasUnknownFieldHandler()) {
				highestContainer.getUnknownFieldHandler().setField(this, reader);
			} else getCustomTagContainer().addCustomTag(reader.readNBT());
		}
	}
	
	protected boolean hasDisplayCompound() {
		return hasDisplayName() || hasLore();
	}
	
	protected void writeDisplayCompound(NBTWriter writer, boolean systemData) throws IOException {
		if (hasDisplayName()) {
			writer.writeStringTag(NAME, MessageUtil.formatMessage(displayname));
		}
		if (hasLore()) {
			writer.writeListTag(LORE, TagType.STRING, lore.countLines());
			for (String s : lore) {
				writer.writeStringTag(null, MessageUtil.formatMessage(s));
			}
		}
	}
	
	protected boolean hasSystemDataCompound() {
		return hasCustomTagContainer() && customTags.hasSystemTags();
	}
	
	protected void writeSystemDataCompound(NBTWriter writer) throws IOException {
		if (customTags != null && customTags.hasSystemTags()) {
			for (NBT nbt : customTags.getSystemTags()) {
				writer.writeNBT(nbt);
			}
		}
	}
}
