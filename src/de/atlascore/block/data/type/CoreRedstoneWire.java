package de.atlascore.block.data.type;

import java.io.IOException;
import java.util.EnumSet;
import java.util.Set;

import de.atlascore.block.data.CoreAnaloguePowerable;
import de.atlasmc.Material;
import de.atlasmc.block.BlockFace;
import de.atlasmc.block.data.type.RedstoneWire;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.ChildNBTFieldContainer;
import de.atlasmc.util.nbt.NBTFieldContainer;
import de.atlasmc.util.nbt.io.NBTWriter;

public class CoreRedstoneWire extends CoreAnaloguePowerable implements RedstoneWire {

	protected static final ChildNBTFieldContainer NBT_FIELDS;
	
	protected static final CharKey
	NORTH = CharKey.of("north"),
	EAST = CharKey.of("east"),
	SOUTH = CharKey.of("south"),
	WEST = CharKey.of("west");
	
	static {
		NBT_FIELDS = new ChildNBTFieldContainer(CoreAnaloguePowerable.NBT_FIELDS);
		NBT_FIELDS.setField(NORTH, (holder, reader) -> {
			((RedstoneWire) holder).setFace(BlockFace.NORTH, Connection.getByName(reader.readStringTag()));
		});
		NBT_FIELDS.setField(EAST, (holder, reader) -> {
			((RedstoneWire) holder).setFace(BlockFace.EAST, Connection.getByName(reader.readStringTag()));
		});
		NBT_FIELDS.setField(SOUTH, (holder, reader) -> {
			((RedstoneWire) holder).setFace(BlockFace.SOUTH, Connection.getByName(reader.readStringTag()));
		});
		NBT_FIELDS.setField(WEST, (holder, reader) -> {
			((RedstoneWire) holder).setFace(BlockFace.WEST, Connection.getByName(reader.readStringTag()));
		});
	}
	
	private Connection[] connections;
	
	public CoreRedstoneWire(Material material) {
		super(material);
		this.connections = new Connection[] {
			Connection.NONE,
			Connection.NONE,	
			Connection.NONE,	
			Connection.NONE
		};
	}

	@Override
	public Set<BlockFace> getAllowedFaces() {
		return EnumSet.range(BlockFace.NORTH, BlockFace.WEST);
	}

	@Override
	public Connection getFace(BlockFace face) {
		if (face == null) throw new IllegalArgumentException("BlockFace can not be null!");
		if (face.ordinal() > 4) throw new IllegalArgumentException("No valid BlockFace: " + face.name());
		return connections[face.ordinal()];
	}

	@Override
	public void setFace(BlockFace face, Connection connection) {
		if (connection == null) throw new IllegalArgumentException("Connection can not be null!");
		if (face == null) throw new IllegalArgumentException("BlockFace can not be null!");
		if (face.ordinal() > 4) throw new IllegalArgumentException("No valid BlockFace: " + face.name());
		connections[face.ordinal()] = connection;
	}
	
	@Override
	public int getStateID() {
		return getMaterial().getBlockID()+
				connections[3].ordinal()+ //WEST
				connections[2].ordinal()*3+ //SOUTH
				getPower()*9+
				connections[0].ordinal()*144+ //NORTH
				connections[1].ordinal()*432; //EAST
	}
	
	@Override
	protected NBTFieldContainer getFieldContainerRoot() {
		return NBT_FIELDS;
	}
	
	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		super.toNBT(writer, systemData);
		if (getFace(BlockFace.NORTH) != Connection.NONE) writer.writeStringTag(NORTH, getFace(BlockFace.NORTH).name().toLowerCase());
		if (getFace(BlockFace.EAST) != Connection.NONE) writer.writeStringTag(EAST, getFace(BlockFace.EAST).name().toLowerCase());
		if (getFace(BlockFace.SOUTH) != Connection.NONE) writer.writeStringTag(SOUTH, getFace(BlockFace.SOUTH).name().toLowerCase());
		if (getFace(BlockFace.WEST) != Connection.NONE) writer.writeStringTag(WEST, getFace(BlockFace.WEST).name().toLowerCase());
	}

}
