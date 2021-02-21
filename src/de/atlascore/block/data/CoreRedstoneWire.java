package de.atlascore.block.data;

import java.util.EnumSet;
import java.util.Set;

import de.atlasmc.Material;
import de.atlasmc.block.BlockFace;
import de.atlasmc.block.data.RedstoneWire;
import de.atlasmc.util.Validate;

public class CoreRedstoneWire extends CoreAnaloguePowerable implements RedstoneWire {

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
		Validate.notNull(face, "BlockFace can not be null!");
		Validate.isFalse(face == BlockFace.UP || face == BlockFace.DOWN, "No valid BlockFace: " + face.name());
		return connections[face.ordinal()];
	}

	@Override
	public void setFace(BlockFace face, Connection connection) {
		Validate.notNull(connection, "Connection can not be null!");
		Validate.notNull(face, "BlockFace can not be null!");
		Validate.isFalse(face == BlockFace.UP || face == BlockFace.DOWN, "No valid BlockFace: " + face.name());
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

}
