package de.atlascore.inventory.meta;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import de.atlasmc.Material;
import de.atlasmc.enchantments.Enchantment;
import de.atlasmc.inventory.meta.EnchantmentStorageMeta;
import de.atlasmc.inventory.meta.ItemMeta;
import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.io.NBTWriter;

public class CoreEnchantmentStorageMeta extends CoreItemMeta implements EnchantmentStorageMeta {
	
	protected static final String STORED_ENCHANTS = "StoredEnchantments";
	
	static {
		NBT_FIELDS.setField(STORED_ENCHANTS, (holder, reader) -> {
			if (holder instanceof EnchantmentStorageMeta) {
				Map<Enchantment, Integer> enchants = ((EnchantmentStorageMeta) holder).getStoredEnchants();
				reader.readNextEntry();
				while (reader.getRestPayload() > 0) {
					Enchantment ench = null;
					int lvl = -1;
					while (reader.getType() != TagType.TAG_END) {
						switch (reader.getFieldName()) {
						case NBT_ID:
							ench = Enchantment.getEnchantment(reader.readStringTag());
							break;
						case NBT_LVL:
							lvl = reader.readShortTag();
							break;
						default:
							reader.skipTag();
							break;
						}
					}
					reader.readNextEntry();
					if (ench == null) 
						continue;
					enchants.put(ench, lvl);
				}
			} else ((ItemMeta) holder).getCustomTagContainer().addCustomTag(reader.readNBT());
		});
	}
	
	private Map<Enchantment, Integer> enchantments;
	
	public CoreEnchantmentStorageMeta(Material material) {
		super(material);
	}

	@Override
	public void addStoredEnchant(Enchantment ench, int level) {
		if (ench == null) 
			throw new IllegalArgumentException("Enchantment can not be null!");
		getEnchants().put(ench, level);
	}

	@Override
	public CoreEnchantmentStorageMeta clone() {
		CoreEnchantmentStorageMeta clone = (CoreEnchantmentStorageMeta) super.clone();
		if (clone == null)
			return null;
		if (hasEnchants())
			clone.enchantments = new HashMap<>(enchantments);
		return clone;
	}

	@Override
	public int getStoredEnchantLevel(Enchantment ench) {
		if (ench == null) throw new IllegalArgumentException("Enchantment can not be null!");
		return hasEnchant(ench) ? enchantments.get(ench) : 0;
	}

	@Override
	public Map<Enchantment, Integer> getStoredEnchants() {
		if (enchantments == null) enchantments = new HashMap<Enchantment, Integer>();
		return enchantments;
	}

	@Override
	public boolean hasConflictingStoredEnchant(Enchantment ench) {
		if (ench == null) throw new IllegalArgumentException("Enchantment can not be null!");
		if (!hasEnchants()) return false;
		for (Enchantment e : enchantments.keySet()) {
			if (ench.conflictsWith(e)) return true;
		}
		return false;
	}

	@Override
	public boolean hasStoredEnchants() {
		return enchantments != null && !enchantments.isEmpty();
	}

	@Override
	public void removeStoredEnchant(Enchantment ench) {
		if (enchantments != null) enchantments.remove(ench);
	}

	@Override
	public boolean hasStoredEnchant(Enchantment ench) {
		if (ench == null) throw new IllegalArgumentException("Enchantment can not be null!");
		if (!hasEnchants()) return false;
		return enchantments.containsKey(ench);
	}

	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		super.toNBT(writer, systemData);
		writer.writeListTag(STORED_ENCHANTS, TagType.COMPOUND, enchantments.size());
		for (Enchantment ench : getEnchants().keySet()) {
			writer.writeStringTag(NBT_ID, ench.getNamespacedName());
			writer.writeShortTag(NBT_LVL, (short) getEnchantLevel(ench));
			writer.writeEndTag();
		}
	}
}
