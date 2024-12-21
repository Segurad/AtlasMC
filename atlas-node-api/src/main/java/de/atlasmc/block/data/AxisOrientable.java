package de.atlasmc.block.data;

import java.util.Set;

import de.atlasmc.Axis;

public interface AxisOrientable extends BlockData {
	
	/**
	 * Returns a Set of all applicable Axis
	 * @return set of Axis
	 */
	Set<Axis> getAxes();
	
	Axis getAxis();
	
	void setAxis(Axis axis);

}
