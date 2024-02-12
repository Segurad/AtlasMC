package de.atlascore.block.data.type;

import de.atlascore.block.data.CoreWaterloggedDirectional4Faces;
import de.atlasmc.Material;
import de.atlasmc.block.data.type.WallSign;

public class CoreWallSign extends CoreWaterloggedDirectional4Faces implements WallSign {

	public CoreWallSign(Material material) {
		super(material);
	}

}
