package de.atlascore.block.data.type;

import de.atlascore.block.data.CoreBlockData;
import de.atlasmc.Material;
import de.atlasmc.block.data.type.Jigsaw;
import de.atlasmc.util.Validate;

public class CoreJigsaw extends CoreBlockData implements Jigsaw {

	private Orientation orientation;
	
	public CoreJigsaw(Material material) {
		super(material);
		orientation = Orientation.NORTH_UP;
	}

	@Override
	public Orientation getOrientation() {
		return orientation;
	}

	@Override
	public void setOrientation(Orientation orientation) {
		Validate.notNull(orientation, "Orientation can not be null!");
		this.orientation = orientation;
	}

}
