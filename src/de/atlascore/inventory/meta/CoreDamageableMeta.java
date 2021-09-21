package de.atlascore.inventory.meta;

import java.io.IOException;

import de.atlasmc.Material;
import de.atlasmc.inventory.meta.DamageableMeta;
import de.atlasmc.inventory.meta.ItemMeta;
import de.atlasmc.util.nbt.io.NBTWriter;

public class CoreDamageableMeta extends CoreItemMeta implements DamageableMeta {

	private int damage;
	
	protected static final String DAMAGE = "Damage";
	
	static {
		NBT_FIELDS.setField(DAMAGE, (holder, reader) -> {
			if (holder instanceof DamageableMeta) {
				((DamageableMeta) holder).setDamage(reader.readIntTag());
			} else ((ItemMeta) holder).getCustomTagContainer().addCustomTag(reader.readNBT());
		});
	}
	
	public CoreDamageableMeta(Material material) {
		super(material);
	}

	@Override
	public CoreDamageableMeta clone() {
		return (CoreDamageableMeta) super.clone();
	}

	@Override
	public void setDamage(int damage) {
		this.damage = damage;
	}

	@Override
	public int getDamage() {
		return damage;
	}

	@Override
	public boolean hasDamage() {
		return damage > 0;
	}
	
	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		super.toNBT(writer, systemData);
		writer.writeIntTag(DAMAGE, damage);
	}

}
