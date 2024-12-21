package de.atlasmc.block.data.property;

import de.atlasmc.block.BlockFace;
import de.atlasmc.block.data.BlockData;
import de.atlasmc.block.data.type.RedstoneWire;
import de.atlasmc.block.data.type.RedstoneWire.Connection;
import de.atlasmc.block.data.type.Wall;
import de.atlasmc.block.data.type.Wall.Height;

class ConnectionProperty extends AbstractMultiEnumProperty {

	private BlockFace face;
	
	public ConnectionProperty(BlockFace face) {
		super(face.name(), Connection.class, Height.class);
	}

	@Override
	public void set(BlockData data, Enum<?> value) {
		if (data instanceof RedstoneWire wire) {
			wire.setFace(face, (Connection) value);
		} else if (data instanceof Wall wall) {
			wall.setHeight(face, (Height) value);
		}
	}

	@Override
	public Enum<?> get(BlockData data) {
		if (data instanceof RedstoneWire wire) { 
			return wire.getFace(face);
		} else if (data instanceof Wall wall) {
			return wall.getHeight(face);
		}
		return null;
	}

	@Override
	public boolean supports(BlockData data) {
		return data instanceof RedstoneWire || data instanceof Wall;
	}

}
