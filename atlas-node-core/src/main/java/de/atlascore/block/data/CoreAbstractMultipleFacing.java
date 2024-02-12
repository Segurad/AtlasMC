package de.atlascore.block.data;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import de.atlasmc.Material;
import de.atlasmc.block.BlockFace;
import de.atlasmc.block.data.MultipleFacing;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.ChildNBTFieldContainer;
import de.atlasmc.util.nbt.NBTFieldContainer;
import de.atlasmc.util.nbt.io.NBTWriter;

public abstract class CoreAbstractMultipleFacing extends CoreBlockData implements MultipleFacing {
	
	protected static final ChildNBTFieldContainer<CoreAbstractMultipleFacing> NBT_FIELDS;
	
	protected static final CharKey
	NBT_NORTH = CharKey.literal("north"),
	NBT_SOUTH = CharKey.literal("south"),
	NBT_EAST = CharKey.literal("east"),
	NBT_WEST = CharKey.literal("west"),
	NBT_UP = CharKey.literal("up"),
	NBT_DOWN = CharKey.literal("down");
	
	static {
		NBT_FIELDS = new ChildNBTFieldContainer<>(CoreBlockData.NBT_FIELDS);
		NBT_FIELDS.setField(NBT_NORTH, (holder, reader) -> {
			holder.setFace(BlockFace.NORTH, reader.readByteTag() == 1);
		});
		NBT_FIELDS.setField(NBT_SOUTH, (holder, reader) -> {
			holder.setFace(BlockFace.SOUTH, reader.readByteTag() == 1);
		});
		NBT_FIELDS.setField(NBT_EAST, (holder, reader) -> {
			holder.setFace(BlockFace.EAST, reader.readByteTag() == 1);
		});
		NBT_FIELDS.setField(NBT_WEST, (holder, reader) -> {
			holder.setFace(BlockFace.WEST, reader.readByteTag() == 1);
		});
		NBT_FIELDS.setField(NBT_UP, (holder, reader) -> {
			holder.setFace(BlockFace.UP, reader.readByteTag() == 1);
		});
		NBT_FIELDS.setField(NBT_DOWN, (holder, reader) -> {
			holder.setFace(BlockFace.DOWN, reader.readByteTag() == 1);
		});
	}
	
	private Set<BlockFace> faces;
	
	public CoreAbstractMultipleFacing(Material material) {
		this(material, 6);
	}
	
	protected CoreAbstractMultipleFacing(Material material, int faces) {
		super(material);
		this.faces = new HashSet<>(faces);
	}

	@Override
	public abstract Set<BlockFace> getAllowedFaces();

	@Override
	public Set<BlockFace> getFaces() {
		return faces;
	}

	@Override
	public boolean hasFace(BlockFace face) {
		if (face == null) 
			throw new IllegalArgumentException("BlockFace can not be null!");
		return faces.contains(face);
	}

	@Override
	public void setFace(BlockFace face, boolean has) {
		if (face == null) 
			throw new IllegalArgumentException("BlockFace can not be null!");
		if (!isValid(face)) 
			throw new IllegalArgumentException("BlockFace is not valid: " + face.name());
		if (has) {
			faces.add(face);
		} else faces.remove(face);
	}
	
	@Override
	public abstract int getStateID();
	
	@Override
	public abstract boolean isValid(BlockFace face);
	
	@Override
	protected NBTFieldContainer<? extends CoreAbstractMultipleFacing> getFieldContainerRoot() {
		return NBT_FIELDS;
	}
	
	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		super.toNBT(writer, systemData);
		if (hasFace(BlockFace.NORTH)) 
			writer.writeByteTag(NBT_NORTH, true);
		if (hasFace(BlockFace.SOUTH)) 
			writer.writeByteTag(NBT_SOUTH, true);
		if (hasFace(BlockFace.EAST)) 
			writer.writeByteTag(NBT_EAST, true);
		if (hasFace(BlockFace.WEST)) 
			writer.writeByteTag(NBT_WEST, true);
		if (hasFace(BlockFace.UP)) 
			writer.writeByteTag(NBT_UP, true);
		if (hasFace(BlockFace.DOWN)) 
			writer.writeByteTag(NBT_DOWN, true);
	}

}
