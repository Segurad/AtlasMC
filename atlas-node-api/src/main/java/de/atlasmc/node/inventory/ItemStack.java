package de.atlasmc.node.inventory;

import static de.atlasmc.io.PacketUtil.*;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

import de.atlasmc.NamespacedKey;
import de.atlasmc.io.codec.StreamCodec;
import de.atlasmc.io.codec.StreamSerializable;
import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.nbt.codec.NBTSerializable;
import de.atlasmc.node.inventory.component.ComponentType;
import de.atlasmc.node.inventory.component.ItemComponent;
import de.atlasmc.node.inventory.component.ItemComponentHolder;
import de.atlasmc.node.inventory.component.MaxStackSizeComponent;
import de.atlasmc.util.annotation.NotNull;
import de.atlasmc.util.annotation.Nullable;
import de.atlasmc.util.codec.CodecContext;
import de.atlasmc.util.map.key.CharKey;
import io.netty.buffer.ByteBuf;
import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import it.unimi.dsi.fastutil.objects.ObjectArraySet;

public class ItemStack implements NBTSerializable, StreamSerializable, ItemComponentHolder {

	public static final NBTCodec<ItemStack> NBT_HANDLER;
	
	public static final StreamCodec<ItemStack> STREAM_CODEC = new StreamCodec<ItemStack>() {
		
		@Override
		public boolean serialize(ItemStack value, ByteBuf output, CodecContext context) throws IOException {
			if (value == null || value.isEmtpy()) {
				writeVarInt(0, output);
				return true;
			}
			writeVarInt(value.getAmount(), output);
			writeVarInt(value.getType().getID(), output);
			final int ptrCompCount = output.readerIndex();
			writeVarInt(0, output);
			final int ptrIgnoredCount = output.readerIndex();
			writeVarInt(0, output);
			if (value.hasComponents()) {
				int count = 0;
				Map<ComponentType, ItemComponent> components = value.getComponents();
				for (ItemComponent comp : components.values()) {
					count++;
					@SuppressWarnings("unchecked")
					StreamCodec<ItemComponent> codec = (StreamCodec<ItemComponent>) comp.getStreamCodec();
					codec.serialize(comp, output, context);
				}
				if (count > 127) {
					/*
					 * Varints up to 127 only take one byte. To prevent data corruption throw exception
					 */
					throw new IllegalStateException("More than 127 ItemComponent written!");
				} else if (count > 0) {
					final int  ptrEnd = output.writerIndex();
					output.writerIndex(ptrCompCount);
					writeVarInt(count, output);
					output.writerIndex(ptrEnd);
				}
			}
			if (value.hasIgnoredComponents()) {
				Set<ComponentType> ignored = value.getIgnoredComponents();
				for (ComponentType type : ignored)
					writeVarInt(type.getID(), output);
				final int  ptrEnd = output.writerIndex();
				output.writerIndex(ptrIgnoredCount);
				writeVarInt(ignored.size(), output);
				output.writerIndex(ptrEnd);
			}
			return true;
		}
		
		@Override
		public Class<? extends ItemStack> getType() {
			return ItemStack.class;
		}
		
		@Override
		public ItemStack deserialize(ItemStack value, ByteBuf input, CodecContext context) throws IOException {
			final int amount = readVarInt(input);
			if (amount == 0)
				return null;
			ItemType itemType = ItemType.getByID(readVarInt(input));
			ItemStack item = new ItemStack(itemType, amount);
			final int compCount = readVarInt(input);
			final int ignoredCount = readVarInt(input);
			for (int i = 0; i < compCount; i++) {
				ItemComponent comp = ItemComponent.STREAM_CODEC.deserialize(input, context);
				item.setComponent(comp);
			}
			for (int i = 0; i < ignoredCount; i++) {
				ComponentType type = ComponentType.getByID(readVarInt(input));
				item.addIgnoredComponent(type);
			}
			return item;
		}
	};
	
	protected static final CharKey
	NBT_COUNT = CharKey.literal("count"),
	NBT_ID = CharKey.literal("id"),
	NBT_SLOT = CharKey.literal("Slot"),
	NBT_COMPONENTS = CharKey.literal("components"),
	NBT_IGNORED_COMPONENTS = CharKey.literal("ignored-components");
	
	static {
		NBT_HANDLER = NBTCodec
				.builder(ItemStack.class)
				.intField("count", ItemStack::getAmount, ItemStack::setAmount, 1)
				.include(ItemComponentHolder.NBT_HANDLER)
				.build();
	}
	
	private int amount;
	private ItemType type;
	private Map<ComponentType, ItemComponent> components;
	private Set<ComponentType> ignoredComponents;

	/**
	 * Creates a ItemStack of the Type {@link Material#AIR} with amount of 1
	 */
	public ItemStack() {
		this(ItemType.AIR.get(), 1);
	}
	
	public ItemStack(ItemType material) {
		this(material, 1);
	}

	public ItemStack(ItemType material, int amount) {
		if (material == null) 
			throw new IllegalArgumentException("Material can not be null!");
		type = material;
		setAmount(amount);
	}
	
	/**
	 * Returns a map containing all components of this ItemStack
	 * @return components
	 */
	@NotNull
	@Override
	public Map<ComponentType, ItemComponent> getComponents() {
		if (components == null)
			components = new Object2ObjectArrayMap<>();
		return components;
	}
	
	@Override
	public boolean hasComponents() {
		return components != null && !components.isEmpty();
	}
	
	/**
	 * Returns the component with the given key or null.
	 * If {@link #getComponent(NamespacedKey)} returns null and the key is not in {@link #getIgnoredComponents()}
	 * {@link Material#getDefaultComponent(NamespacedKey)} will be used
	 * @param <T> to cast
	 * @param key of the component
	 * @return component or null
	 */
	@Nullable
	public <T extends ItemComponent> T getEffectiveComponent(ComponentType key) {
		ItemComponent component = null;
		if (components == null) {
			component = components.get(key);
		}
		if (component == null) {
			component = type.getDefaultComponent(key);
			if (ignoredComponents != null && ignoredComponents.contains(component.getType()))
				return null;
		}
		if (component == null)
			return null;
		@SuppressWarnings("unchecked")
		T type = (T) component;
		return type;
	}
	
	/**
	 * Returns a set containing key of all ignored default components.
	 * @return keys
	 */
	@NotNull
	public Set<ComponentType> getIgnoredComponents() {
		if (ignoredComponents == null)
			ignoredComponents = new ObjectArraySet<>();
		return ignoredComponents;
	}

	/**
	 * Whether or not ignored default components are present
	 * @return true if ignored components
	 */
	public boolean hasIgnoredComponents() {
		return ignoredComponents != null && !ignoredComponents.isEmpty();
	}
	
	/**
	 * Adds the given type to the ignored components set.
	 * @param type
	 */
	public void addIgnoredComponent(ComponentType type) {
		if (type == null)
			throw new IllegalArgumentException("Type can not be null!");
		getIgnoredComponents().add(type);
	}
	
	/**
	 * Removes the given type from the ignored components set.
	 * @param type
	 */
	public void removeIgnoredComponent(ComponentType type) {
		if (type == null)
			throw new IllegalArgumentException("Type can not be null!");
		if (ignoredComponents == null)
			return;
		ignoredComponents.remove(type);
	}

	/**
	 * Returns the type of this ItemStack
	 * @return type
	 */
	@NotNull
	public ItemType getType() {
		return type;
	}
	
	/**
	 * Returns the amount of this ItemStack.
	 * @return amount
	 */
	public int getAmount() {
		return amount;
	}
	
	public int getMaxAmount() {
		MaxStackSizeComponent comp = getEffectiveComponent(ComponentType.MAX_STACK_SIZE.get());
		return comp == null ? 1 : comp.getMaxStackSize();
	}
	
	public void setAmount(int amount) {
		if (amount < 0) 
			throw new IllegalArgumentException("Amount can not be lower than 0: " + amount);
		this.amount = amount;
	}

	/**
	 * Compares a ItemStack with this ItemStack a returns whether or not it is similar.
	 * Ignoring stack size and damage
	 * @param item
	 * @return true if similar
	 */
	public boolean isSimilar(ItemStack item) {
		return isSimilar(item, true);
	}
	
	public boolean isEmtpy() {
		return getType() == ItemType.AIR.get() || getAmount() == 0;
	}
	
	public ItemStack clone() {
		try {
			ItemStack clone = (ItemStack) super.clone();
			if (components != null) 
				clone.components = new Object2ObjectArrayMap<>(components);
			if (ignoredComponents != null)
				clone.ignoredComponents = new ObjectArraySet<>(ignoredComponents);
			return clone;
		} catch (CloneNotSupportedException e) {}
		return null;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (obj == this)
			return true;
		if (!(obj instanceof ItemStack))
			return false;
		ItemStack item = (ItemStack) obj;
		return isSimilar(item, false);
	}
	
	/**
	 * Compares a ItemStack with this ItemStack a returns whether or not it is similar
	 * @param item the ItemStack that should be compared
	 * @param ignoreAmount whether or not the amount should be ignored in this comparison
	 * @param ignoreDamage whether or not the damage values should be ignored in this comparison
	 * @return true if similar
	 */
	public boolean isSimilar(ItemStack item, boolean ignoreAmount) {
		if (item == null) 
			return false;
		if (item == this) 
			return true;
		if (item.getClass() != getClass())
			return false;
		if (type != item.getType()) 
			return false; 
		if (!ignoreAmount && amount != item.getAmount())
			return false;
		if (components != null) {
			if (item.components == null)
				return false;
			if (!components.equals(item.components))
				return false;
		} else if (item.components != null) {
			return false;
		}
		return true;
	}
	
	@Override
	public int hashCode() {
		int h = 1;
		h = 31 * h + amount;
		h = 31 * h + type.hashCode();
		if (components != null)
			h = 31 * h + components.hashCode();
		if (ignoredComponents != null)
			h = 31 * h + ignoredComponents.hashCode();
		return h;
	}

	@Override
	public NBTCodec<? extends ItemStack> getNBTCodec() {
		return NBT_HANDLER;
	}
	
	@Override
	public StreamCodec<? extends ItemStack> getStreamCodec() {
		return STREAM_CODEC;
	}
	
}
