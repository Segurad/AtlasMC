package de.atlasmc.block.blockpredicate;

import de.atlasmc.block.BlockType;
import de.atlasmc.block.data.BlockData;
import de.atlasmc.block.tile.TileEntity;

public class TileBlockPredicate implements BlockPredicate {

	private TileEntity tile;
	
	public TileBlockPredicate(TileEntity tile) {
		setTile(tile);
	}
	
	public TileEntity getTile() {
		return tile;
	}
	
	public void setTile(TileEntity tile) {
		if (tile == null)
			throw new IllegalArgumentException("Tile can not be null!");
		this.tile = tile;
	}
	
	@Override
	public boolean matches(TileEntity tile) {
		return this.tile.equals(tile);
	}

	@Override
	public boolean matches(BlockData data) {
		return false;
	}

	@Override
	public boolean matches(BlockType type) {
		return false;
	}

}
