package de.atlascore.entity;

import java.io.IOException;
import java.util.UUID;

import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.MinecartTNT;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.NBTFieldSet;
import de.atlasmc.util.nbt.io.NBTWriter;

public class CoreMinecartTNT extends CoreAbstractMinecart implements MinecartTNT {

	protected static final NBTFieldSet<CoreMinecartTNT> NBT_FIELDS;
	
	protected static final CharKey
	NBT_FUSE = CharKey.literal("Fuse");
	
	static {
		NBT_FIELDS = CoreAbstractMinecart.NBT_FIELDS.fork();
		NBT_FIELDS.setField(NBT_FUSE, (holder, reader) -> {
			holder.setFuseTime(reader.readIntTag());
		});
	}
	
	private int fuseTime = -1;
	
	public CoreMinecartTNT(EntityType type, UUID uuid) {
		super(type, uuid);
	}
	
	@Override
	protected NBTFieldSet<? extends CoreMinecartTNT> getFieldSetRoot() {
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
