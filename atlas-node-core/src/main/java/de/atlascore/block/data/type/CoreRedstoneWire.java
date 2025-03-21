package de.atlascore.block.data.type;

import java.util.EnumSet;
import java.util.List;
import java.util.Set;

import de.atlascore.block.data.CoreAnaloguePowerable;
import de.atlasmc.block.BlockFace;
import de.atlasmc.block.BlockType;
import de.atlasmc.block.data.property.BlockDataProperty;
import de.atlasmc.block.data.type.RedstoneWire;

public class CoreRedstoneWire extends CoreAnaloguePowerable implements RedstoneWire {

	protected static final List<BlockDataProperty<?>> PROPERTIES;
	
	static {
		PROPERTIES = merge(CoreAnaloguePowerable.PROPERTIES, 
				BlockDataProperty.CON_EAST,
				BlockDataProperty.CON_NORTH,
				BlockDataProperty.CON_SOUTH,
				BlockDataProperty.CON_WEST);
	}
	
	private Connection[] connections;
	
	public CoreRedstoneWire(BlockType type) {
		super(type);
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
		if (face == null) 
			throw new IllegalArgumentException("BlockFace can not be null!");
		if (face.ordinal() > 4) 
			throw new IllegalArgumentException("No valid BlockFace: " + face.name());
		return connections[face.ordinal()];
	}

	@Override
	public void setFace(BlockFace face, Connection connection) {
		if (connection == null) 
			throw new IllegalArgumentException("Connection can not be null!");
		if (face == null) 
			throw new IllegalArgumentException("BlockFace can not be null!");
		if (face.ordinal() > 4) 
			throw new IllegalArgumentException("No valid BlockFace: " + face.name());
		connections[face.ordinal()] = connection;
	}
	
	@Override
	public int getStateID() {
		return type.getBlockStateID()+
				connections[3].ordinal()+ //WEST
				connections[2].ordinal()*3+ //SOUTH
				power*9+
				connections[0].ordinal()*144+ //NORTH
				connections[1].ordinal()*432; //EAST
	}
	
	@Override
	public List<BlockDataProperty<?>> getProperties() {
		return PROPERTIES;
	}

}
