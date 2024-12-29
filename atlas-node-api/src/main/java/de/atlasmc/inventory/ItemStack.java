package de.atlasmc.inventory;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

import de.atlasmc.Material;
import de.atlasmc.NamespacedKey;
import de.atlasmc.inventory.component.ComponentType;
import de.atlasmc.inventory.component.ItemComponent;
import de.atlasmc.inventory.component.ItemComponentFactory;
import de.atlasmc.registry.Registries;
import de.atlasmc.registry.Registry;
import de.atlasmc.util.annotation.NotNull;
import de.atlasmc.util.annotation.Nullable;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.NBTException;
import de.atlasmc.util.nbt.NBTField;
import de.atlasmc.util.nbt.NBTFieldContainer;
import de.atlasmc.util.nbt.NBTHolder;
import de.atlasmc.util.nbt.NBTUtil;
import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;
import de.atlasmc.util.nbt.tag.NBT;
import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import it.unimi.dsi.fastutil.objects.ObjectArraySet;

public class ItemStack implements NBTHolder {

	protected static final NBTFieldContainer<ItemStack> NBT_FIELDS;
	
	protected static final CharKey
	NBT_COUNT = CharKey.literal("count"),
	NBT_ID = CharKey.literal("id"),
	NBT_SLOT = CharKey.literal("Slot"),
	NBT_COMPONENTS = CharKey.literal("components"),
	NBT_IGNORED_COMPONENTS = CharKey.literal("ignored-components");
	
	static {
		NBT_FIELDS = NBTFieldContainer.newContainer();
		NBT_FIELDS.setField(NBT_ID, NBTField.skip());
		NBT_FIELDS.setField(NBT_COUNT, (holder, reader) -> {
			holder.amount = reader.readIntTag();
		});
		NBT_FIELDS.setField(NBT_SLOT, (holder, reader) -> {
			holder.slot = reader.readByteTag();
		});
		NBT_FIELDS.setField(NBT_COMPONENTS, (holder, reader) -> {
			reader.readNextEntry();
			Registry<ItemComponentFactory> registry = Registries.getRegistry(ItemComponentFactory.class);
			while (reader.getType() != TagType.TAG_END) {
				NamespacedKey key = NamespacedKey.of(reader.getFieldName());
				ItemComponentFactory factory = registry.get(key);
				holder.setComponent(factory.createComponent());
			}
			reader.readNextEntry();
		});
		NBT_FIELDS.setField(NBT_IGNORED_COMPONENTS, (holder, reader) -> {
			reader.readNextEntry();
			while (reader.getRestPayload() > 0) {
				
			}
		});
	}
	
	private short slot;
	private int amount;
	private Material type;
	private Map<NamespacedKey, ItemComponent> components;
	private Set<ComponentType> ignoredComponents;

	/**
	 * Creates a ItemStack of the Type {@link Material#AIR} with amount of 1
	 */
	public ItemStack() {
		this(Material.AIR, 1);
	}
	
	public ItemStack(Material material) {
		this(material, 1);
	}
	
	public ItemStack(NamespacedKey key) {
		this(key, 1);
	}
	
	public ItemStack(NamespacedKey key, int amount) {
		this(Material.get(key), amount);
	}

	public ItemStack(Material material, int amount) {
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
	public Map<NamespacedKey, ItemComponent> getComponents() {
		if (components == null)
			components = new Object2ObjectArrayMap<>();
		return components;
	}
	
	/**
	 * Returns whether or not this ItemStack has components
	 * @return true if components present
	 */
	public boolean hasComponents() {
		return components != null && !components.isEmpty();
	}
	
	/**
	 * Returns whether or not a component with the given key is present
	 * @param key to check
	 * @return true if present
	 */
	public boolean hasComponent(NamespacedKey key) {
		if (components == null)
			return false;
		return components.containsKey(key);
	}
	
	/**
	 * Returns the component with the given key or null.
	 * If no component with the given key is present a component will be created
	 * @param <T> to cast
	 * @param key of the component
	 * @return component or null
	 */
	@Nullable
	public <T extends ItemComponent> T getComponent(NamespacedKey key) {
		if (key == null)
			throw new IllegalArgumentException("Key can not be null!");
		if (components != null || !components.isEmpty()) {
			@SuppressWarnings("unchecked")
			T component = (T) components.get(key);
			if (component != null)
				return component;
		}
		Registry<ItemComponentFactory> registry = Registries.getRegistry(ItemComponentFactory.class);
		ItemComponentFactory factory = registry.get(key);
		if (factory == null)
			return null;
		@SuppressWarnings("unchecked")
		T component = (T) factory.createComponent();
		getComponents().put(key, component);
		return component;
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
	public <T extends ItemComponent> T getEffectiveComponent(NamespacedKey key) {
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
	 * Sets a new ItemComponent and returns the previous value
	 * @param component to set
	 * @return component or null
	 */
	public ItemComponent setComponent(ItemComponent component) {
		if (component == null)
			throw new IllegalArgumentException("Component can not be null!");
		return getComponents().put(component.getNamespacedKey(), component);
	}
	
	/**
	 * Removes the component with the given key
	 * @param key to remove
	 * @return component or null
	 */
	public ItemComponent removeComponent(NamespacedKey key) {
		if (key == null)
			throw new IllegalArgumentException("Key can not be null!");
		if (components == null)
			return null;
		return components.remove(key);
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
	public Material getType() {
		return type;
	}
	
	/**
	 * Returns the amount of this ItemStack.
	 * @return amount
	 */
	public int getAmount() {
		return amount;
	}
	
	public void setAmount(int amount) {
		if (amount < 1) 
			throw new IllegalArgumentException("Amount must be higher than 0: " + amount);
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

	
	public int getMaxStackSize() {
		return type.getMaxAmount();
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
	
	/**
	 * Same as {@link #toNBT(NBTWriter, boolean)} but does write the slot number
	 * @param writer
	 * @param systemData
	 * @param slot the slot number or -999 for none
	 * @throws IOException 
	 */
	public void toSlot(NBTWriter writer, boolean systemData, int slot) throws IOException {
		if (systemData) {
			writer.writeStringTag(NBT_ID, getType().getNamespacedKeyRaw());
		} else {
			writer.writeStringTag(NBT_ID, getType().getClientKey().toString());
		}
		writer.writeByteTag(NBT_COUNT, (byte) getAmount());
		if (slot != -999) 
			writer.writeByteTag(NBT_SLOT, slot);
		if(components != null && !components.isEmpty()) {
			writer.writeCompoundTag(NBT_COMPONENTS);
			for (ItemComponent comp : components.values()) {
				comp.toNBT(writer, systemData);
			}
			writer.writeEndTag();
		}
		if (systemData && ignoredComponents != null && !ignoredComponents.isEmpty()) {
			writer.writeListTag(NBT_IGNORED_COMPONENTS, TagType.STRING, ignoredComponents.size());
			for (ComponentType type : ignoredComponents) {
				writer.writeStringTag(null, type.getKey().toString());
			}
		}
	}

	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		toSlot(writer, systemData, -999);
	}

	@Override
	public void fromNBT(NBTReader reader) throws IOException {
		NBTUtil.readNBT(NBT_FIELDS, this, reader);
	}
	
	/**
	 * Same as {@link #fromSlot(NBTReader)} but does return the slot number while reading slot NBT
	 * @param reader the NBTReader should be used
	 * @return the slot number or -999 if not present
	 * @throws IOException 
	 */
	public int fromSlot(NBTReader reader) throws IOException {
		slot = -999;
		NBTUtil.readNBT(NBT_FIELDS, this, reader);
		return slot;
	}
	
	public static ItemStack getFromNBT(NBTReader reader) throws IOException {
		return getFromNBT(reader, true);
	}
	
	/**
	 * 
	 * @param reader
	 * @param readData whether or not {@link #fromNBT(NBT)} should be called
	 * @return
	 * @throws IOException
	 */
	public static ItemStack getFromNBT(NBTReader reader, boolean readData) throws IOException {
		if (reader.getType() == TagType.TAG_END) { // Empty Tag 
			reader.readNextEntry();
			return null;
		}
		String rawMaterial = null;
		if (!NBT_ID.equals(reader.getFieldName())) {
			reader.mark();
			reader.search(NBT_ID);
			rawMaterial = reader.readStringTag();
			reader.reset();
		} else {
			rawMaterial = reader.readStringTag();
		}
		if (rawMaterial == null) {
			throw new NBTException("NBT did not container id field!");
		}
		Material material = Material.getByName(rawMaterial);
		if (material == null) {
			throw new NBTException("Not material found with name: " + rawMaterial);
		}
		ItemStack item = new ItemStack(material);
		item.fromNBT(reader);
		return item;
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
	
}
