package de.atlasmc.block.data;

import de.atlasmc.Material;
import de.atlasmc.util.configuration.Configuration;

public class LightableBlockDataFactory extends ClassBlockDataFactory {

	private final boolean lit;
	
	public <L extends Lightable> LightableBlockDataFactory(Class<L> dataInterface, Class<? extends L> data, boolean lit) {
		super(dataInterface, data);
		this.lit = lit;
	}
	
	public LightableBlockDataFactory(Configuration cfg) throws ClassNotFoundException {
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
