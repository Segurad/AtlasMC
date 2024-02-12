package de.atlascore.block.data.type;

import java.io.IOException;

import de.atlascore.block.data.CoreWaterlogged;
import de.atlasmc.Material;
import de.atlasmc.block.BlockFace;
import de.atlasmc.block.data.type.Wall;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.ChildNBTFieldContainer;
import de.atlasmc.util.nbt.NBTFieldContainer;
import de.atlasmc.util.nbt.io.NBTWriter;

public class CoreWall extends CoreWaterlogged implements Wall {

	protected static final ChildNBTFieldContainer<CoreWall> NBT_FIELDS;
	
	protected static final CharKey
	NORTH = CharKey.literal("north"),
	EAST = CharKey.literal("east"),
	WEST = CharKey.literal("west"),
	SOUTH = CharKey.literal("south"),
	UP = CharKey.literal("up");
	
	static {
		NBT_FIELDS = new ChildNBTFieldContainer<>(CoreWaterlogged.NBT_FIELDS);
		NBT_FIELDS.setField(NORTH, (holder, reader) -> {
			holder.setHeight(BlockFace.NORTH, Height.getByName(reader.readStringTag()));
		});
		NBT_FIELDS.setField(EAST, (holder, reader) -> {
			holder.setHeight(BlockFace.EAST, Height.getByName(reader.readStringTag()));
		});
		NBT_FIELDS.setField(SOUTH, (holder, reader) -> {
			holder.setHeight(BlockFace.SOUTH, Height.getByName(reader.readStringTag()));
		});
		NBT_FIELDS.setField(WEST, (holder, reader) -> {
			holder.setHeight(BlockFace.WEST, Height.getByName(reader.readStringTag()));
		});
		NBT_FIELDS.setField(UP, (holder, reader) -> {
			holder.setUp(reader.readByteTag() == 1);
		});
	}
	
	private boolean up;
	private final Height[] heights;
	
	public CoreWall(Material material) {
		super(material);
		heights = new Height[] {
				Height.NONE,
				Height.NONE,
				Height.NONE,
				Height.NONE
		};
		up = true;
	}

	@Override
	public Height getHeight(BlockFace face) {
		if (face == null) 
			throw new IllegalArgumentException("BlockFace can not be null!");
		if (face.ordinal() > 4) 
			throw new IllegalArgumentException("BlockFace is not valid: " + face.name());
		return heights[face.ordinal()];
	}

	@Override
	public boolean isUp() {
		return up;
	}

	@Override
	public void setHeight(BlockFace face, Height height) {
		if (face == null) 
			throw new IllegalArgumentException("BlockFace can not be null!");
		if (height == null) 
			throw new IllegalArgumentException("Height can not be null!");
		if (face.ordinal() > 4) 
			throw new IllegalArgumentException("BlockFace is not valid: " + face.name());
		heights[face.ordinal()] = height;
	}

	@Override
	public void setUp(boolean up) {
		this.up = up;
	}
	
	@Override
	public int getStateID() {
		return getMaterial().getBlockStateID()+
				heights[3].ordinal()+ // WEST
				(isWaterlogged()?0:3)+ 
				(up?0:6)+
				heights[2].ordinal()*12+ // SOUTH
				heights[0].ordinal()*36+ // NORTH
				heights[1].ordinal()*108; // EAST
	}
	
	@Override
	protected NBTFieldContainer<? extends CoreWall> getFieldContainerRoot() {
		return NBT_FIELDS;
	}
	
	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		super.toNBT(writer, systemData);
		if (getHeight(BlockFace.NORTH) != Height.NONE) 
			writer.writeStringTag(NORTH, getHeight(BlockFace.NORTH).name().toLowerCase());
		if (getHeight(BlockFace.EAST) != Height.NONE) 
			writer.writeStringTag(EAST, getHeight(BlockFace.EAST).name().toLowerCase());
		if (getHeight(BlockFace.SOUTH) != Height.NONE)
			writer.writeStringTag(SOUTH, getHeight(BlockFace.SOUTH).name().toLowerCase());
		if (getHeight(BlockFace.WEST) != Height.NONE) 
			writer.writeStringTag(WEST, getHeight(BlockFace.WEST).name().toLowerCase());
		if (isUp()) 
			writer.writeByteTag(UP, true);
	}

}
