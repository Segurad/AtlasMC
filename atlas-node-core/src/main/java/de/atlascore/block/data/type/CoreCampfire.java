package de.atlascore.block.data.type;

import java.util.List;

import de.atlascore.block.data.CoreWaterloggedDirectional4Faces;
import de.atlasmc.Material;
import de.atlasmc.block.data.property.BlockDataProperty;
import de.atlasmc.block.data.type.Campfire;

public class CoreCampfire extends CoreWaterloggedDirectional4Faces implements Campfire {

	protected static final List<BlockDataProperty<?>> PROPERTIES;
	
	static {
		PROPERTIES = merge(CoreWaterloggedDirectional4Faces.PROPERTIES, 
				BlockDataProperty.LIT,
				BlockDataProperty.SIGNAL_FIRE);
	}
	
	private boolean lit;
	private boolean signalFire;
	
	public CoreCampfire(Material material) {
		super(material);
		lit = true;
	}

	@Override
	public boolean isLit() {
		return lit;
	}

	@Override
	public void setLit(boolean lit) {
		this.lit = lit;
	}

	@Override
	public boolean isSignalFire() {
		return signalFire;
	}

	@Override
	public void setSignalFire(boolean signalFire) {
		this.signalFire = signalFire;
	}
	
	@Override
	public int getStateID() {
		return getMaterial().getBlockStateID()+
				(isWaterlogged()?0:1)+
				(signalFire?0:2)+
				(lit?0:4)+
				getFaceValue()*8;
	}
	
	@Override
	public List<BlockDataProperty<?>> getProperties() {
		return PROPERTIES;
	}

}
