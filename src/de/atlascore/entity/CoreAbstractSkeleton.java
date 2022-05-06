package de.atlascore.entity;

import java.io.IOException;
import java.util.UUID;

import de.atlasmc.entity.AbstractSkeleton;
import de.atlasmc.entity.EntityType;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.ChildNBTFieldContainer;
import de.atlasmc.util.nbt.NBTFieldContainer;
import de.atlasmc.util.nbt.io.NBTWriter;
import de.atlasmc.world.World;

public class CoreAbstractSkeleton extends CoreMob implements AbstractSkeleton {

	protected static final NBTFieldContainer NBT_FIELDS;
	
	protected static final CharKey
	NBT_CAN_PICKUP_LOOT = CharKey.of("CanPickUpLoot");
	
	static {
		NBT_FIELDS = new ChildNBTFieldContainer(CoreMob.NBT_FIELDS);
		NBT_FIELDS.setField(NBT_CAN_PICKUP_LOOT, (holder, reader) -> {
			if (holder instanceof AbstractSkeleton) {
				((AbstractSkeleton) holder).setCanPickupLoot(reader.readByteTag() == 1);
			} else reader.skipTag();
		});
	}
	
	private boolean canPickupLoot;
	
	public CoreAbstractSkeleton(EntityType type, UUID uuid, World world) {
		super(type, uuid, world);
	}
	
	@Override
	protected NBTFieldContainer getFieldContainerRoot() {
		return NBT_FIELDS;
	}

	@Override
	public boolean canPickupLoot() {
		return canPickupLoot;
	}

	@Override
	public void setCanPickupLoot(boolean canPickup) {
		this.canPickupLoot = canPickup;
	}

	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		super.toNBT(writer, systemData);
		if (canPickupLoot)
			writer.writeByteTag(NBT_CAN_PICKUP_LOOT, true);
	}
	
}
