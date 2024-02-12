package de.atlascore.inventory.meta;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.atlasmc.Material;
import de.atlasmc.inventory.ItemStack;
import de.atlasmc.inventory.meta.BundleMeta;
import de.atlasmc.inventory.meta.ItemMeta;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.io.NBTWriter;

public class CoreBundleMeta extends CoreItemMeta implements BundleMeta {

	private static final CharKey
	NBT_ITEMS = CharKey.literal("Items");
	
	static {
		NBT_FIELDS.setField(NBT_ITEMS, (holder, reader) -> {
			if (!(holder instanceof BundleMeta)) {
				((ItemMeta) holder).getCustomTagContainer().addCustomTag(reader.readNBT());
				return;
			}
			BundleMeta meta = (BundleMeta) holder;
			reader.readNextEntry();
			while (reader.getRestPayload() > 0) {
				Material mat = null;
				if (!NBT_ID.equals(reader.getFieldName())) {
					reader.mark();
					reader.search(NBT_ID);
					mat = Material.getByName(reader.readStringTag());
					reader.reset();
				} else mat = Material.getByName(reader.readStringTag());
				ItemStack item = new ItemStack(mat);
				item.fromNBT(reader);
				meta.addItem(item);
			}
		});
	}
	
	private List<ItemStack> items;
	
	public CoreBundleMeta(Material material) {
		super(material);
	}

	@Override
	public void addItem(ItemStack item) {
		if (item == null)
			throw new IllegalArgumentException("Item can not be null!");
		if (items == null)
			items = new ArrayList<>();
		items.add(item);
	}

	@Override
	public List<ItemStack> getItems() {
		return items;
	}

	@Override
	public boolean hasItems() {
		return items != null && !items.isEmpty();
	}

	@Override
	public void setItems(List<ItemStack> items) {
		if (items == null)
			throw new IllegalArgumentException("Items can not be null!");
		if (this.items == null)
			this.items = new ArrayList<>(items.size());
		this.items.addAll(items);
	}
	
	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		super.toNBT(writer, systemData);
		if (hasItems()) {
			writer.writeListTag(NBT_ITEMS, TagType.COMPOUND, items.size());
			for (ItemStack item : items) {
				item.toNBT(writer, systemData);
				writer.writeEndTag();
			}
		}
	}
	
	@Override
	public boolean isSimilar(ItemMeta meta, boolean ignoreDamage) {
		if (!super.isSimilar(meta, ignoreDamage))
			return false;
		BundleMeta bundleMeta = (BundleMeta) meta;
		if (hasItems()) {
			if (!bundleMeta.hasItems()) {
				return false;
			} else {
				return items.equals(bundleMeta.getItems());
			}
		} else {
			return !bundleMeta.hasItems();
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((items == null) ? 0 : items.hashCode());
		return result;
	}
	
	public CoreBundleMeta clone() {
		CoreBundleMeta clone = (CoreBundleMeta) super.clone();
		if (items != null) {
			if (items.isEmpty()) {
				clone.items = null;
			} else {
				clone.setItems(items);
			}
		}
		return clone;
	}

}
