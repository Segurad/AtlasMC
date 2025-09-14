package de.atlasmc.core.node.block.data;

import java.util.Set;

import de.atlasmc.node.Axis;
import de.atlasmc.node.block.BlockType;

public class CoreAxisOrientableXZ extends CoreAxisOrientable {

	private static final Set<Axis> ALLOWED_AXIS =
			Set.of(Axis.X,
					Axis.Z);
	
	public CoreAxisOrientableXZ(BlockType type) {
		super(type, Axis.X);
	}

	@Override
	public Set<Axis> getAxes() {
		return ALLOWED_AXIS;
	}
	
	@Override
	public int getStateID() {
		return super.getStateID() + (axis == Axis.X ? 0:1); 
	}

}
