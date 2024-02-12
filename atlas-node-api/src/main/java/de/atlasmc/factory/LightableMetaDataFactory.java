package de.atlasmc.factory;

import de.atlasmc.Material;
import de.atlasmc.block.data.BlockData;
import de.atlasmc.block.data.Lightable;
import de.atlasmc.inventory.meta.ItemMeta;
import de.atlasmc.util.configuration.Configuration;

public class LightableMetaDataFactory extends ClassMetaDataFactory {

	private final boolean lit;
	
	public <I extends ItemMeta, L extends Lightable> LightableMetaDataFactory(Class<I> metaInterface, Class<? extends I> meta, Class<L> dataInterface, Class<? extends L> data, boolean lit) {
		super(metaInterface, meta, dataInterface, data);
		this.lit = lit;
	}
	
	public LightableMetaDataFactory(Configuration cfg) throws ClassNotFoundException {
		super(cfg);
		lit = cfg.getBoolean("lit");
	}
	
	@Override
	public BlockData createData(Material material) {
		Lightable data = (Lightable) super.createData(material);
		data.setLit(lit);
		return data;
	}

}
