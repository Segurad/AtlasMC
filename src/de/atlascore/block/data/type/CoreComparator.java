package de.atlascore.block.data.type;

import de.atlascore.block.data.CoreDirectional4Faces;
import de.atlasmc.Material;
import de.atlasmc.block.data.type.Comparator;
import de.atlasmc.util.Validate;

public class CoreComparator extends CoreDirectional4Faces implements Comparator {

	private Mode mode;
	private boolean powered;
	
	public CoreComparator(Material material) {
		super(material);
	}

	@Override
	public boolean isPowered() {
		return powered;
	}

	@Override
	public void setPowered(boolean powered) {
		this.powered = powered;
	}

	@Override
	public Mode getMode() {
		return mode;
	}

	@Override
	public void setMode(Mode mode) {
		Validate.notNull(mode, "Mode can not be null!");
		this.mode = mode;
	}
	
	@Override
	public int getStateID() {
		return getMaterial().getBlockID()+
				(powered?0:1)+
				mode.ordinal()*2+
				getFaceValue()*4;
	}

}
