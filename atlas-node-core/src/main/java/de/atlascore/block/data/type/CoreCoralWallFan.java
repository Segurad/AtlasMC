package de.atlascore.block.data.type;

import de.atlascore.block.data.CoreWaterloggedDirectional4Faces;
import de.atlasmc.Material;
import de.atlasmc.block.data.type.CoralWallFan;

public class CoreCoralWallFan extends CoreWaterloggedDirectional4Faces implements CoralWallFan {
	
	public CoreCoralWallFan(Material material) {
		super(material);
		setWaterlogged(true);
	}

}
