package de.atlasmc.core.node.block.data.type;

import java.util.EnumSet;
import java.util.List;
import java.util.Set;

import de.atlasmc.core.node.block.data.CoreAnaloguePowerable;
import de.atlasmc.node.block.BlockFace;
import de.atlasmc.node.block.BlockType;
import de.atlasmc.node.block.data.property.PropertyType;
import de.atlasmc.node.block.data.type.RedstoneWire;

public class CoreRedstoneWire extends CoreAnaloguePowerable implements RedstoneWire {

	protected static final List<PropertyType<?>> PROPERTIES;
	
	static {
		PROPERTIES = merge(CoreAnaloguePowerable.PROPERTIES, 
				PropertyType.CON_EAST,
				PropertyType.CON_NORTH,
				PropertyType.CON_SOUTH,
				PropertyType.CON_WEST);
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
	public List<PropertyType<?>> getProperties() {
		return PROPERTIES;
	}

}
