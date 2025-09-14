package de.atlasmc.node.block.data;

import java.util.Set;

import de.atlasmc.node.Axis;

public interface AxisOrientable extends BlockData {
	
	/**
	 * Returns a Set of all applicable Axis
	 * @return set of Axis
	 */
	Set<Axis> getAxes();
	
	Axis getAxis();
	
	void setAxis(Axis axis);

}
