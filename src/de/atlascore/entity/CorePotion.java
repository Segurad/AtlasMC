package de.atlascore.entity;

import java.io.IOException;
import java.util.UUID;

import de.atlasmc.Material;
import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.Potion;
import de.atlasmc.entity.ThrowableProjectile;
import de.atlasmc.inventory.ItemStack;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.ChildNBTFieldContainer;
import de.atlasmc.util.nbt.NBTFieldContainer;
import de.atlasmc.util.nbt.io.NBTWriter;
import de.atlasmc.world.World;

public class CorePotion extends CoreThrowableProjectile implements Potion {
	
	protected static final NBTFieldContainer NBT_FIELDS;
	
	protected static final CharKey
	NBT_POTION = CharKey.of("Potion");
	
	static {
		NBT_FIELDS = new ChildNBTFieldContainer(CoreThrowableProjectile.NBT_FIELDS);
		NBT_FIELDS.setField(NBT_POTION, (holder, reader) -> {
			reader.readNextEntry();
			Material mat = null;
			if (!NBT_ID.equals(reader.getFieldName())) {
				reader.mark();
				reader.search(NBT_ID);
				mat = Material.getByName(reader.readStringTag());
				reader.reset();
			} else mat = Material.getByName(reader.readStringTag());
			ItemStack item = new ItemStack(mat);
			item.fromNBT(reader);
			((ThrowableProjectile) holder).setItem(item);
		});
	}
	
	public CorePotion(EntityType type, UUID uuid, World world) {
		super(type, uuid, world);
	}
	
	@Override
	protected NBTFieldContainer getFieldContainerRoot() {
		return NBT_FIELDS;
	}

	@Override
	public ProjectileType getProjectileType() {
		return ProjectileType.POTION;
	}
	
	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		super.toNBT(writer, systemData);
		if (getItem() != null) {
			writer.writeCompoundTag(NBT_POTION);
			getItem().toNBT(writer, systemData);
			writer.writeEndTag();
		}
	}

}
