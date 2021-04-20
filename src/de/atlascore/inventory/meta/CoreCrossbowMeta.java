package de.atlascore.inventory.meta;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.atlasmc.Material;
import de.atlasmc.inventory.ItemStack;
import de.atlasmc.inventory.meta.CrossbowMeta;
import de.atlasmc.inventory.meta.ItemMeta;
import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.io.NBTWriter;

public class CoreCrossbowMeta extends CoreDamageableMeta implements CrossbowMeta {

	private List<ItemStack> projectiles;
	private boolean charged;
	
	protected static final String 
			CHARGED_PROJECTILES = "ChargedProjectiles", 
			CHARGED = "Charged";
	
	static {
		NBT_FIELDS.setField(CHARGED, (holder, reader) -> {
			if (CrossbowMeta.class.isInstance(holder)) {
				((CrossbowMeta) holder).setCharged(reader.readByteTag() == 1);
			} else ((ItemMeta) holder).getCustomTagContainer().addCustomTag(reader.readNBT());
		});
		NBT_FIELDS.setField(CHARGED_PROJECTILES, (holder, reader) -> {
			if (!CrossbowMeta.class.isInstance(holder)) {
				((ItemMeta) holder).getCustomTagContainer().addCustomTag(reader.readNBT());
				return;
			}
			List<ItemStack> projectiles = ((CrossbowMeta) holder).getChargedProjectiles();
			while (reader.getRestPayload() > 0) {
				ItemStack item = new ItemStack(Material.AIR);
				item.fromNBT(reader);
				projectiles.add(item);
			}
		});
	}
	
	public CoreCrossbowMeta(Material material) {
		super(material);
	}

	@Override
	public void addChargedProjectile(ItemStack item) {
		if (projectiles == null) projectiles = new ArrayList<ItemStack>();
		projectiles.add(item);
	}

	@Override
	public List<ItemStack> getChargedProjectiles() {
		if (projectiles == null) projectiles = new ArrayList<ItemStack>();
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
		if (hasChargedProjectiles()) {
			List<ItemStack> projectiles = clone.getChargedProjectiles();
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
			writer.writeListTag(CHARGED_PROJECTILES, TagType.COMPOUND, projectiles.size());
			for (ItemStack i : projectiles) {
				i.toNBT(writer, systemData);
				writer.writeEndTag();
			}
		}
		if (isCharged()) {
			writer.writeByteTag(CHARGED, 1);
		}
	}
}
