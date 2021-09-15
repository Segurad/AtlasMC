package de.atlascore.block.tile;

import java.io.IOException;

import de.atlasmc.Material;
import de.atlasmc.block.tile.TileEntity;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;

public class CoreTileEntity implements TileEntity {

	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void fromNBT(NBTReader reader) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Material getType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getX() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getY() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getZ() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public TileEntity clone() {
		try {
			return (TileEntity) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return null;
	}

}
