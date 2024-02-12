package de.atlascore.block.tile;

import java.io.IOException;

import de.atlasmc.Location;
import de.atlasmc.Material;
import de.atlasmc.block.tile.EndGateway;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.ChildNBTFieldContainer;
import de.atlasmc.util.nbt.NBTFieldContainer;
import de.atlasmc.util.nbt.io.NBTWriter;
import de.atlasmc.world.Chunk;

public class CoreEndGateway extends CoreTileEntity implements EndGateway {

	protected static final ChildNBTFieldContainer<CoreEndGateway> NBT_FIELDS;
	
	protected static final CharKey
	AGE = CharKey.literal("Age"),
	EXACT_TELEPORT = CharKey.literal("ExactTeleport"),
	RELATIVE_COORDINATES = CharKey.literal("RelativeCoordinates"),
	EXIT_PORTAL = CharKey.literal("ExitPortal"),
	PORTAL_X = CharKey.literal("X"),
	PORTAL_Y = CharKey.literal("Y"),
	PORTAL_Z = CharKey.literal("Z");
	
	static {
		NBT_FIELDS = new ChildNBTFieldContainer<>(CoreTileEntity.NBT_FIELDS);
		NBT_FIELDS.setField(AGE, (holder, reader) -> {
			holder.setAge(reader.readLongTag());
		});
		NBT_FIELDS.setField(EXACT_TELEPORT, (holder, reader) -> {
			holder.setExactTeleport(reader.readByteTag() == 1);
		});
		NBT_FIELDS.setField(RELATIVE_COORDINATES, (holder, reader) -> {
			holder.setRelativeCoordinates(reader.readByteTag() == 1);
		});
		NBT_FIELDS.setContainer(EXIT_PORTAL)
		.setField(PORTAL_X, (holder, reader) -> {
			holder.exit.x = reader.readIntTag();
		}).setField(PORTAL_Y, (holder, reader) -> {
			holder.exit.y = reader.readIntTag();
		}).setField(PORTAL_Z, (holder, reader) -> {
			holder.exit.z = reader.readIntTag();
		});
	}
	
	private long age;
	private boolean exactTeleport, relativeCoordinates;
	private final Location exit;
	
	public CoreEndGateway(Material type) {
		super(type);
		exit = new Location(null, Double.NaN, Double.NaN, Double.NaN);
	}

	@Override
	public long getAge() {
		return age;
	}

	@Override
	public void setAge(long age) {
		this.age = age;
	}

	@Override
	public boolean isExactTeleport() {
		return exactTeleport;
	}

	@Override
	public void setExactTeleport(boolean exact) {
		this.exactTeleport = exact;
	}

	@Override
	public boolean isRelativeCoordinates() {
		return relativeCoordinates;
	}

	@Override
	public void setRelativeCoordinates(boolean relative) {
		this.relativeCoordinates = relative;
	}

	@Override
	public Location getExitPortal() {
		return exit;
	}

	@Override
	public void setExitPortal(Location loc) {
		exit.set(loc);
	}
	
	@Override
	public void setLocation(Chunk chunk, int x, int y, int z) {
		super.setLocation(chunk, x, y, z);
		if (exit.getWorld() == null && chunk != null) {
			if (exit.isInvalid()) {
				exit.set(getWorld(), getX(), getY(), getZ());
			} else {
				exit.set(getWorld(), exit.x, exit.y, exit.z);
			}
		}
			
	}
	
	@Override
	protected NBTFieldContainer<? extends CoreEndGateway> getFieldContainerRoot() {
		return NBT_FIELDS;
	}
	
	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		super.toNBT(writer, systemData);
		if (!systemData) 
			return;
		writer.writeLongTag(AGE, getAge());
		writer.writeByteTag(EXACT_TELEPORT, isExactTeleport());
		writer.writeByteTag(RELATIVE_COORDINATES, isRelativeCoordinates());
		writer.writeCompoundTag(EXIT_PORTAL);
		writer.writeIntTag(PORTAL_X, exit.getBlockX());
		writer.writeIntTag(PORTAL_Y, exit.getBlockY());
		writer.writeIntTag(PORTAL_Z, exit.getBlockZ());
		writer.writeEndTag();
	}

}
