package de.atlascore.block.data;

import java.util.Set;

import de.atlasmc.Axis;
import de.atlasmc.Material;

public class CoreOrientableXZ extends CoreOrientable {

	private static final Set<Axis> ALLOWED_AXIS =
			Set.of(Axis.X,
					Axis.Z);
	
	public CoreOrientableXZ(Material material) {
		super(material, Axis.X);
	}

	@Override
	public Set<Axis> getAxes() {
		return ALLOWED_AXIS;
	}
	
	@Override
	public int getStateID() {
		return super.getStateID() + (getAxis() == Axis.X ? 0:1); 
	}

}
