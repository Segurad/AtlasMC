package de.atlascore.block.data;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import de.atlasmc.Material;
import de.atlasmc.block.BlockFace;
import de.atlasmc.block.data.MultipleFacing;
import de.atlasmc.util.Validate;
import de.atlasmc.util.nbt.io.NBTWriter;

public abstract class CoreAbstractMultipleFacing extends CoreBlockData implements MultipleFacing {

	private Set<BlockFace> faces;
	
	protected static final String
	NORTH = "north",
	SOUTH = "south",
	EAST = "east",
	WEST = "west",
	UP = "up",
	DOWN = "down";
	
	static {
		NBT_FIELDS.setField(NORTH, (holder, reader) -> {
			if (MultipleFacing.class.isInstance(holder)) {
				((MultipleFacing) holder).setFace(BlockFace.NORTH, reader.readByteTag() == 1);
			} else reader.skipTag();
		});
		NBT_FIELDS.setField(SOUTH, (holder, reader) -> {
			if (MultipleFacing.class.isInstance(holder)) {
				((MultipleFacing) holder).setFace(BlockFace.SOUTH, reader.readByteTag() == 1);
			} else reader.skipTag();
		});
		NBT_FIELDS.setField(EAST, (holder, reader) -> {
			if (MultipleFacing.class.isInstance(holder)) {
				((MultipleFacing) holder).setFace(BlockFace.EAST, reader.readByteTag() == 1);
			} else reader.skipTag();
		});
		NBT_FIELDS.setField(WEST, (holder, reader) -> {
			if (MultipleFacing.class.isInstance(holder)) {
				((MultipleFacing) holder).setFace(BlockFace.WEST, reader.readByteTag() == 1);
			} else reader.skipTag();
		});
		NBT_FIELDS.setField(UP, (holder, reader) -> {
			if (MultipleFacing.class.isInstance(holder)) {
				((MultipleFacing) holder).setFace(BlockFace.UP, reader.readByteTag() == 1);
			} else reader.skipTag();
		});
		NBT_FIELDS.setField(DOWN, (holder, reader) -> {
			if (MultipleFacing.class.isInstance(holder)) {
				((MultipleFacing) holder).setFace(BlockFace.DOWN, reader.readByteTag() == 1);
			} else reader.skipTag();
		});
	}
	
	public CoreAbstractMultipleFacing(Material material) {
		this(material, 6);
	}
	
	protected CoreAbstractMultipleFacing(Material material, int faces) {
		super(material);
		this.faces = new HashSet<BlockFace>(faces);
	}

	@Override
	public abstract Set<BlockFace> getAllowedFaces();

	@Override
	public Set<BlockFace> getFaces() {
		return faces;
	}

	@Override
	public boolean hasFace(BlockFace face) {
		Validate.notNull(face, "BlockFace can not be null!");
		return faces.contains(face);
	}

	@Override
	public void setFace(BlockFace face, boolean has) {
		Validate.notNull(face, "BlockFace can not be null!");
		Validate.isTrue(isValid(face), "BlockFace is not valid: " + face.name());
		if (has) {
			faces.add(face);
		} else faces.remove(face);
	}
	
	@Override
	public abstract int getStateID();
	
	@Override
	public abstract boolean isValid(BlockFace face);
	
	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		super.toNBT(writer, systemData);
		if (hasFace(BlockFace.NORTH)) writer.writeByteTag(NORTH, true);
		if (hasFace(BlockFace.SOUTH)) writer.writeByteTag(SOUTH, true);
		if (hasFace(BlockFace.EAST)) writer.writeByteTag(EAST, true);
		if (hasFace(BlockFace.WEST)) writer.writeByteTag(WEST, true);
		if (hasFace(BlockFace.UP)) writer.writeByteTag(UP, true);
		if (hasFace(BlockFace.DOWN)) writer.writeByteTag(DOWN, true);
	}

}
