package de.atlasmc.core.node.inventory.component;

import de.atlasmc.node.block.tile.TileEntity;
import de.atlasmc.node.inventory.component.AbstractItemComponent;
import de.atlasmc.node.inventory.component.BlockEntityComponent;
import de.atlasmc.node.inventory.component.ComponentType;

public class CoreBlockEntityComponent extends AbstractItemComponent implements BlockEntityComponent {

	private TileEntity tile;
	
	public CoreBlockEntityComponent(ComponentType type) {
		super(type);
	}

	@Override
	public TileEntity getTileEntity() {
		return tile;
	}

	@Override
	public void setTileEntity(TileEntity tile) {
		this.tile = tile;
	}
	
	@Override
	public CoreBlockEntityComponent clone() {
		CoreBlockEntityComponent clone = (CoreBlockEntityComponent) super.clone();
		if (clone == null)
			return null;
		if (tile != null) {
			clone.tile = tile.clone();
		}
		return clone;
	}

}
