package de.atlascore.entity;

import java.io.IOException;
import java.util.UUID;

import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.MinecartHopper;
import de.atlasmc.event.inventory.InventoryType;
import de.atlasmc.factory.ContainerFactory;
import de.atlasmc.inventory.Inventory;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.ChildNBTFieldContainer;
import de.atlasmc.util.nbt.NBTFieldContainer;
import de.atlasmc.util.nbt.io.NBTWriter;
import de.atlasmc.world.World;

public class CoreMinecartHopper extends CoreAbstractMinecartContainer implements MinecartHopper {

	protected static final NBTFieldContainer NBT_FIELDS;
	
	protected static final CharKey
	NBT_ENABLED = CharKey.of("Enabled");
	// NBT_TRANSFER_COOLDOWN = "TransferCooldown"; TODO unnecessary
	
	static {
		NBT_FIELDS = new ChildNBTFieldContainer(CoreAbstractMinecartContainer.NBT_FIELDS);
		NBT_FIELDS.setField(NBT_ENABLED, (holder, reader) -> {
			((MinecartHopper) holder).setEnabled(reader.readByteTag() == 1);
		});
	}
	
	private boolean enabled;
	
	public CoreMinecartHopper(EntityType type, UUID uuid, World world) {
		super(type, uuid, world);
	}
	
	@Override
	protected NBTFieldContainer getFieldContainerRoot() {
		return NBT_FIELDS;
	}

	@Override
	protected Inventory createInventory() {
		return ContainerFactory.GENERIC_INV_FACTORY.create(InventoryType.HOPPER, this);
	}

	@Override
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}

	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		super.toNBT(writer, systemData);
		if (isEnabled())
			writer.writeByteTag(NBT_ENABLED, true);
	}
	
}
