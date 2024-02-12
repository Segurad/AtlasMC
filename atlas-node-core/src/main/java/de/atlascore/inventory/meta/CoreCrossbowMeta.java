package de.atlascore.inventory.meta;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.atlasmc.Material;
import de.atlasmc.inventory.ItemStack;
import de.atlasmc.inventory.meta.CrossbowMeta;
import de.atlasmc.inventory.meta.ItemMeta;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.io.NBTWriter;

public class CoreCrossbowMeta extends CoreDamageableMeta implements CrossbowMeta {
	
	protected static final CharKey 
	NBT_CHARGED_PROJECTILES = CharKey.literal("ChargedProjectiles"), 
	NBT_CHARGED = CharKey.literal("Charged");
	
	static {
		NBT_FIELDS.setField(NBT_CHARGED, (holder, reader) -> {
			if (holder instanceof CrossbowMeta) {
				((CrossbowMeta) holder).setCharged(reader.readByteTag() == 1);
			} else ((ItemMeta) holder).getCustomTagContainer().addCustomTag(reader.readNBT());
		});
		NBT_FIELDS.setField(NBT_CHARGED_PROJECTILES, (holder, reader) -> {
			if (!(holder instanceof CrossbowMeta)) {
				((ItemMeta) holder).getCustomTagContainer().addCustomTag(reader.readNBT());
				return;
			}
			List<ItemStack> projectiles = ((CrossbowMeta) holder).getChargedProjectiles();
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
				projectiles.add(item);
			}
		});
	}
	
	private List<ItemStack> projectiles;
	private boolean charged;
	
	public CoreCrossbowMeta(Material material) {
		super(material);
	}

	@Override
	public void addChargedProjectile(ItemStack item) {
		if (projectiles == null) projectiles = new ArrayList<>();
		projectiles.add(item);
	}

	@Override
	public List<ItemStack> getChargedProjectiles() {
		if (projectiles == null) projectiles = new ArrayList<>();
		return projectiles;
	}

	@Override
	public boolean hasChargedProjectiles() {
		return projectiles != null && !projectiles.isEmpty();
	}

	@Override
	public void setChargedProjectiles(List<ItemStack> projectiles) {
		this.projectiles = projectiles;
	}
	
	@Override
	public CoreCrossbowMeta clone() {
		CoreCrossbowMeta clone = (CoreCrossbowMeta) super.clone();
		if (clone == null)
			return null;
		if (hasChargedProjectiles()) {
			List<ItemStack> projectiles = new ArrayList<>(getProjectileCount());
			for (ItemStack i : getChargedProjectiles()) {
				projectiles.add(i.clone());
			}
		}
		return clone;
	}

	@Override
	public boolean isCharged() {
		return charged;
	}

	@Override
	public void setCharged(boolean charged) {
		this.charged = charged;
	}

	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		if (hasChargedProjectiles()) {
			writer.writeListTag(NBT_CHARGED_PROJECTILES, TagType.COMPOUND, projectiles.size());
			for (ItemStack i : projectiles) {
				i.toNBT(writer, systemData);
				writer.writeEndTag();
			}
		}
		if (isCharged()) {
			writer.writeByteTag(NBT_CHARGED, 1);
		}
	}

	@Override
	public int getProjectileCount() {
		return projectiles == null ? 0 : projectiles.size();
	}
	
	@Override
	public boolean isSimilar(ItemMeta meta, boolean ignoreDamage) {
		if (!super.isSimilar(meta, ignoreDamage))
			return false;
		CrossbowMeta xbowMeta = (CrossbowMeta) meta;
		if (isCharged() != xbowMeta.isCharged())
			return false;
		if (hasChargedProjectiles() != xbowMeta.hasChargedProjectiles())
			return false;
		if (hasChargedProjectiles() && !getChargedProjectiles().equals(xbowMeta.getChargedProjectiles()))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + (charged ? 1231 : 1237);
		result = prime * result + ((projectiles == null) ? 0 : projectiles.hashCode());
		return result;
	}

}
