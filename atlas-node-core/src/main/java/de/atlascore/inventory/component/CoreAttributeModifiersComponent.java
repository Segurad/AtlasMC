package de.atlascore.inventory.component;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import de.atlasmc.NamespacedKey;
import de.atlasmc.attribute.Attribute;
import de.atlasmc.attribute.AttributeModifier;
import de.atlasmc.attribute.AttributeModifier.Operation;
import de.atlasmc.inventory.EquipmentSlot;
import de.atlasmc.inventory.component.AbstractItemComponent;
import de.atlasmc.inventory.component.AttributeModifiersComponent;
import de.atlasmc.inventory.component.ComponentType;
import de.atlasmc.util.map.ArrayListMultimap;
import de.atlasmc.util.map.ListMultimap;
import de.atlasmc.util.map.Multimap;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.NBTField;
import de.atlasmc.util.nbt.NBTFieldSet;
import de.atlasmc.util.nbt.NBTUtil;
import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;
import io.netty.buffer.ByteBuf;
import static de.atlasmc.io.protocol.ProtocolUtil.*;

public class CoreAttributeModifiersComponent extends AbstractItemComponent implements AttributeModifiersComponent {

	protected static final NBTFieldSet<CoreAttributeModifiersComponent> NBT_FIELDS;
	protected static final CharKey 
	NBT_MODIFIERS = CharKey.literal("modifiers"),
	NBT_TYPE = CharKey.literal("type"),
	NBT_SLOT = CharKey.literal("slot"),
	NBT_ID = CharKey.literal("id"),
	NBT_AMOUNT = CharKey.literal("amount"),
	NBT_OPERATION = CharKey.literal("operation"),
	NBT_SHOW_IN_TOOLTIP = CharKey.literal("show_in_tooltip");
	
	static {
		NBT_FIELDS = NBTFieldSet.newSet();
		NBT_FIELDS.setField(NBT_MODIFIERS, (holder, reader) -> {
			Multimap<Attribute, AttributeModifier> attributes = holder.getAttributeModifiers();
			reader.readNextEntry();
			while (reader.getRestPayload() > 0) {
				reader.readNextEntry();
				Attribute attribute = null;
				double amount = 0;
				NamespacedKey id = null;
				EquipmentSlot slot = null;
				Operation operation = null;
				while (reader.getType() != TagType.TAG_END) {
					final CharSequence value = reader.getFieldName();
					if (NBT_AMOUNT.equals(value))
						amount = reader.readDoubleTag();
					else if (NBT_TYPE.equals(value))
						attribute = Attribute.getByName(reader.readStringTag());
					else if (NBT_OPERATION.equals(value))
						operation = Operation.getByName(reader.readStringTag());
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
			reader.readNextEntry();
		});
		NBT_FIELDS.setField(NBT_SHOW_IN_TOOLTIP, (holder, reader) -> {
			holder.setShowTooltip(reader.readBoolean());
		});
	}
	
	private ListMultimap<Attribute, AttributeModifier> attributes;
	private boolean showTooltip = true;
	
	public CoreAttributeModifiersComponent(NamespacedKey key) {
		super(key);
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
	public void setAttributeModifiers(Multimap<Attribute, AttributeModifier> attributeModifiers) {
		if (attributeModifiers == null) 
			throw new IllegalArgumentException("AttributeModifier can not be null!");
		this.attributes.clear();
		this.attributes.putAll(attributeModifiers);
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
	public boolean isShowTooltip() {
		return showTooltip;
	}

	@Override
	public void setShowTooltip(boolean show) {
		this.showTooltip = show;
	}
	
	@Override
	public CoreAttributeModifiersComponent clone() {
		CoreAttributeModifiersComponent clone =  (CoreAttributeModifiersComponent) super.clone();
		if (clone == null)
			return null;
		if (hasAttributeModifiers()) {
			clone.attributes = null;
			Multimap<Attribute, AttributeModifier> attributes = clone.getAttributeModifiers();
			for (Entry<Attribute, Collection<AttributeModifier>> entry : this.attributes.entrySet()) {
				Attribute attribute = entry.getKey();
				List<AttributeModifier> modifiers = (List<AttributeModifier>) entry.getValue();
				final int size = modifiers.size();
				for (int i = 0; i < size; i++) {
					attributes.put(attribute, modifiers.get(i).clone());
				}
			}
		}
		return clone;
	}
	
	@Override
	public void fromNBT(NBTReader reader) throws IOException {
		if (reader.getType() == TagType.LIST) { // in some cases the tag may be a list instead of a compound
			NBTField<CoreAttributeModifiersComponent> field = NBT_FIELDS.getField(NBT_MODIFIERS);
			field.setField(this, reader);
		} else {
			reader.readNextEntry();
			NBTUtil.readNBT(NBT_FIELDS, this, reader);;
		}
	}

	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		writer.writeCompoundTag(key.toString());
		if (hasAttributeModifiers()) {
			Multimap<Attribute, AttributeModifier> attributes = getAttributeModifiers();
			writer.writeListTag(NBT_MODIFIERS, TagType.COMPOUND, attributes.valuesSize());
			for (Attribute attribute : attributes.keySet()) {
				final String rawname = attribute.getName();
				for (AttributeModifier mod : attributes.get(attribute)) {
					writer.writeStringTag(NBT_TYPE, rawname);
					writer.writeStringTag(NBT_SLOT, mod.getSlot().getName());
					writer.writeNamespacedKey(NBT_ID, mod.getID());
					writer.writeDoubleTag(NBT_AMOUNT, mod.getAmount());
					writer.writeStringTag(NBT_OPERATION, mod.getOperation().getName());
					writer.writeEndTag();
				}
			}
		}
		if (!showTooltip)
			writer.writeByteTag(NBT_SHOW_IN_TOOLTIP, false);
		writer.writeEndTag();
	}

	@Override
	public ComponentType getType() {
		return ComponentType.ATTRIBUTE_MODIFIERS;
	}
	
	@Override
	public boolean isServerOnly() {
		return false;
	}

	@Override
	public void read(ByteBuf buf) {
		final int count = readVarInt(buf);
		if (count == 0)
			return;
		Multimap<Attribute, AttributeModifier> modifiers = getAttributeModifiers();
		modifiers.clear();
		for (int i = 0; i < count; i++) {
			Attribute attribute = Attribute.getByID(readVarInt(buf));
			NamespacedKey modifierID = readIdentifier(buf);
			double amount = buf.readDouble();
			Operation operation = Operation.getByID(readVarInt(buf));
			EquipmentSlot slot = EquipmentSlot.getByID(readVarInt(buf));
			AttributeModifier modifier = new AttributeModifier(modifierID, amount, operation, slot);
			modifiers.put(attribute, modifier);
		}
		showTooltip = buf.readBoolean();
	}

	@Override
	public void write(ByteBuf buf) {
		if (attributes == null || attributes.isEmpty()) {
			writeVarInt(0, buf);
			return;
		}
		writeVarInt(attributes.valuesSize(), buf);
		for (Entry<Attribute, Collection<AttributeModifier>> entry : attributes.entrySet()) {
			final Attribute key = entry.getKey();
			final List<AttributeModifier> modifiers = (List<AttributeModifier>) entry.getValue();
			final int size = modifiers.size();
			for (int i = 0; i < size; i++) {
				writeVarInt(key.getID(), buf);
				AttributeModifier modifier = modifiers.get(i);
				writeIdentifier(modifier.getID(), buf);
				buf.writeDouble(modifier.getAmount());
				writeVarInt(modifier.getOperation().getID(), buf);
				writeVarInt(modifier.getSlot().getID(), buf);
			}
		}
		buf.writeBoolean(showTooltip);
	}

}
