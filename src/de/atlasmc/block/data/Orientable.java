package de.atlasmc.block.data;

import java.util.Set;

import de.atlasmc.Axis;

public interface Orientable extends BlockData {
	
	public Set<Axis> getAxes();
	public Axis getAxis();
	public void setAxis(Axis axis);

}
