package de.atlascore.block.data.type;

import de.atlascore.block.data.CoreDirectional4Faces;
import de.atlasmc.Material;
import de.atlasmc.block.data.type.Door;
import de.atlasmc.util.Validate;

public class CoreDoor extends CoreDirectional4Faces implements Door {
	
	private Half half;
	private Hinge hinge;
	private boolean open, powered;
	
	public CoreDoor(Material material) {
		super(material);
		half = Half.BOTTOM;
		hinge = Hinge.LEFT;
	}

	@Override
	public Half getHalf() {
		return half;
	}

	@Override
	public void setHalf(Half half) {
		Validate.notNull(half, "Half can not be null!");
		this.half = half;
	}

	@Override
	public boolean isOpen() {
		return open;
	}

	@Override
	public void setOpen(boolean open) {
		this.open = open;
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
	public Hinge getHinge() {
		return hinge;
	}

	@Override
	public void setHinge(Hinge hinge) {
		Validate.notNull(hinge, "Hinge can not be null!");
		this.hinge = hinge;
	}
	
	@Override
	public int getStateID() {
		return getMaterial().getBlockID()+
				(powered?0:1)+
				(open?0:2)+
				hinge.ordinal()*4+
				half.ordinal()*8+
				getFaceValue()*16;
	}

}
