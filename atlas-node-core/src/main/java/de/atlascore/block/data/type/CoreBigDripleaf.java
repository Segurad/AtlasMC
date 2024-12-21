package de.atlascore.block.data.type;

import java.util.List;

import de.atlascore.block.data.CoreWaterloggedDirectional4Faces;
import de.atlasmc.Material;
import de.atlasmc.block.data.property.BlockDataProperty;
import de.atlasmc.block.data.type.BigDripleaf;

public class CoreBigDripleaf extends CoreWaterloggedDirectional4Faces implements BigDripleaf {

	protected static final List<BlockDataProperty<?>> PROPERTIES;
	
	static {
		PROPERTIES = merge(CoreWaterloggedDirectional4Faces.PROPERTIES, BlockDataProperty.TILT);
	}
	
	private Tilt tilt;
	
	public CoreBigDripleaf(Material material) {
		super(material);
		tilt = Tilt.NONE;
	}

	@Override
	public Tilt getTilt() {
		return tilt;
	}

	@Override
	public void setTilt(Tilt tilt) {
		if (tilt == null)
			throw new IllegalArgumentException("Tilt can not be null!");
		this.tilt = tilt;
	}
	
	@Override
	public int getStateID() {
		return getMaterial().getBlockStateID() + (isWaterlogged()?0:1)+tilt.getID()*2+getFaceValue()*8;
	}
	
	@Override
	public CoreBigDripleaf clone() {
		return (CoreBigDripleaf) super.clone();
	}
	
	@Override
	public List<BlockDataProperty<?>> getProperties() {
		return PROPERTIES;
	}

}
