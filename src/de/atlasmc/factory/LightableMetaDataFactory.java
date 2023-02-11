package de.atlasmc.factory;

import de.atlasmc.Material;
import de.atlasmc.block.data.BlockData;
import de.atlasmc.block.data.Lightable;

public class LightableMetaDataFactory extends ClassMetaDataFactory {

	private final boolean lit;
	
	public <L extends Lightable> LightableMetaDataFactory(Class<L> dataInterface, Class<? extends L> data, boolean lit) {
		super(dataInterface, data);
		this.lit = lit;
	}
	
	@Override
	public BlockData createData(Material material) {
		Lightable data = (Lightable) super.createData(material);
		data.setLit(lit);
		return data;
	}

}
