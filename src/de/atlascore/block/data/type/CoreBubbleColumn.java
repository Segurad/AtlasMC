package de.atlascore.block.data.type;

import de.atlascore.block.data.CoreBlockData;
import de.atlasmc.Material;
import de.atlasmc.block.data.type.BubbleColumn;

public class CoreBubbleColumn extends CoreBlockData implements BubbleColumn {

	private boolean drag;
	
	public CoreBubbleColumn(Material material) {
		super(material);
		drag = true;
	}

	@Override
	public boolean isDrag() {
		return drag;
	}

	@Override
	public void setDrag(boolean drag) {
		this.drag = drag;
	}

	@Override
	public int getStateID() {
		return super.getStateID()+(drag?0:1);
	}
}
