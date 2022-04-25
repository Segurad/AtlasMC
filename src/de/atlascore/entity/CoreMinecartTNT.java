package de.atlascore.entity;

import java.io.IOException;
import java.util.UUID;

import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.MinecartTNT;
import de.atlasmc.util.nbt.ChildNBTFieldContainer;
import de.atlasmc.util.nbt.NBTFieldContainer;
import de.atlasmc.util.nbt.io.NBTWriter;
import de.atlasmc.world.World;

public class CoreMinecartTNT extends CoreAbstractMinecart implements MinecartTNT {

	protected static final NBTFieldContainer NBT_FIELDS;
	
	protected static final String
	NBT_FUSE = "Fuse";
	
	static {
		NBT_FIELDS = new ChildNBTFieldContainer(CoreAbstractMinecart.NBT_FIELDS);
		NBT_FIELDS.setField(NBT_FUSE, (holder, reader) -> {
			((MinecartTNT) holder).setFuseTime(reader.readIntTag());
		});
	}
	
	private int fuseTime = -1;
	
	public CoreMinecartTNT(EntityType type, UUID uuid, World world) {
		super(type, uuid, world);
	}
	
	@Override
	protected NBTFieldContainer getFieldContainerRoot() {
		return NBT_FIELDS;
	}

	@Override
	public void setFuseTime(int ticks) {
		this.fuseTime = ticks;
	}

	@Override
	public int getFuseTime() {
		return fuseTime;
	}
	
	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		super.toNBT(writer, systemData);
		writer.writeIntTag(NBT_FUSE, getFuseTime());
	}

}
