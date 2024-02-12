package de.atlasmc.block.data;

import java.util.Set;

import de.atlasmc.Axis;

public interface Orientable extends BlockData {
	
	/**
	 * Returns a Set of all applicable Axis
	 * @return set of Axis
	 */
	public Set<Axis> getAxes();
	
	public Axis getAxis();
	
	public void setAxis(Axis axis);

}
