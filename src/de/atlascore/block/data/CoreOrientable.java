package de.atlascore.block.data;

import java.util.EnumSet;
import java.util.Set;

import de.atlasmc.Axis;
import de.atlasmc.Material;
import de.atlasmc.block.data.Orientable;

public class CoreOrientable extends CoreBlockData implements Orientable {

	private Axis axis;
	
	public CoreOrientable(Material material) {
		super(material);
		axis = Axis.Y;
	}

	@Override
	public Set<Axis> getAxes() {
		return EnumSet.allOf(Axis.class);
	}

	@Override
	public Axis getAxis() {
		return axis;
	}

	@Override
	public void setAxis(Axis axis) {
		this.axis = axis;
	}
	
	@Override
	public int getStateID() {
		return super.getStateID()+axis.ordinal();
	}

}
