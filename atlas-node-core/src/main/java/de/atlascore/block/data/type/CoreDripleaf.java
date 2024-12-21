package de.atlascore.block.data.type;

import de.atlascore.block.data.CoreWaterloggedDirectional4Faces;
import de.atlasmc.Material;
import de.atlasmc.block.data.type.Dripleaf;

public class CoreDripleaf extends CoreWaterloggedDirectional4Faces implements Dripleaf {
	
	public CoreDripleaf(Material material) {
		super(material);
	}
	
	@Override
	public CoreDripleaf clone() {
		return (CoreDripleaf) super.clone();
	}

}
