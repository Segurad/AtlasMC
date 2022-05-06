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

	protected static final ChildNBTFieldContainer NBT_FIELDS;
	
	protected static final CharKey
	AGE = CharKey.of("Age"),
	EXACT_TELEPORT = CharKey.of("ExactTeleport"),
	RELATIVE_COORDINATES = CharKey.of("RelativeCoordinates"),
	EXIT_PORTAL = CharKey.of("ExitPortal"),
	PORTAL_X = CharKey.of("X"),
	PORTAL_Y = CharKey.of("Y"),
	PORTAL_Z = CharKey.of("Z");
	
	static {
		NBT_FIELDS = new ChildNBTFieldContainer(CoreTileEntity.NBT_FIELDS);
		NBT_FIELDS.setField(AGE, (holder, reader) -> {
			if (holder instanceof EndGateway)
			((EndGateway) holder).setAge(reader.readLongTag());
			else reader.skipTag();
		});
		NBT_FIELDS.setField(EXACT_TELEPORT, (holder, reader) -> {
			if (holder instanceof EndGateway)
			((EndGateway) holder).setExactTeleport(reader.readByteTag() == 1);
			else reader.skipTag();
		});
		NBT_FIELDS.setField(RELATIVE_COORDINATES, (holder, reader) -> {
			if (holder instanceof EndGateway)
			((EndGateway) holder).setRelativeCoordinates(reader.readByteTag() == 1);
			else reader.skipTag();
		});
		NBT_FIELDS.setContainer(EXIT_PORTAL)
		.setField(PORTAL_X, (holder, reader) -> {
			if (holder instanceof CoreEndGateway)
			((CoreEndGateway) holder).exit.setX(reader.readIntTag());
			else reader.skipTag();
		}).setField(PORTAL_Y, (holder, reader) -> {
			if (holder instanceof CoreEndGateway)
			((CoreEndGateway) holder).exit.setY(reader.readIntTag());
			else reader.skipTag();
		}).setField(PORTAL_Z, (holder, reader) -> {
			if (holder instanceof CoreEndGateway)
			((CoreEndGateway) holder).exit.setZ(reader.readIntTag());
			else reader.skipTag();
		});
	}
	
	private long age;
	private boolean exactTeleport, relativeCoordinates;
	private final Location exit;
	
	public CoreEndGateway(Material type, Chunk chunk, int x, int y, int z) {
		super(type, chunk, x, y, z);
		exit = new Location(chunk.getWorld(), x, y, z);
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
		exit.setLocation(loc);
	}
	
	@Override
	protected NBTFieldContainer getFieldContainerRoot() {
		return NBT_FIELDS;
	}
	
	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		super.toNBT(writer, systemData);
		if (!systemData) return;
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
