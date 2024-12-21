package de.atlascore.block.data.type;

import java.util.List;
import java.util.Set;

import de.atlascore.block.data.CoreAbstractDirectional;
import de.atlasmc.Material;
import de.atlasmc.block.BlockFace;
import de.atlasmc.block.data.property.BlockDataProperty;
import de.atlasmc.block.data.type.Hopper;

public class CoreHopper extends CoreAbstractDirectional implements Hopper {
	
	private static final Set<BlockFace> ALLOWED_FACES =
			Set.of(BlockFace.NORTH,
					BlockFace.EAST,
					BlockFace.SOUTH,
					BlockFace.WEST,
					BlockFace.DOWN);
	
	protected static final List<BlockDataProperty<?>> PROPERTIES;
	
	static {
		PROPERTIES = merge(CoreAbstractDirectional.PROPERTIES, BlockDataProperty.ENABLED);
	}
	
	private boolean enabled;
	
	public CoreHopper(Material material) {
		super(material, BlockFace.DOWN);
		this.enabled = true;
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}

	@Override
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;		
	}

	@Override
	public Set<BlockFace> getFaces() {
		return ALLOWED_FACES;
	}

	@Override
	public int getStateID() {
		return getMaterial().getBlockStateID()+
				getFaceValue()+
				(enabled?0:5);
	}

	@Override
	protected int getFaceValue(BlockFace face) {
		switch(face) {
		case DOWN: return 0;
		case NORTH: return 1;
		case SOUTH: return 2;
		case WEST: return 3;
		case EAST: return 4;
		default: return -1;
		}
	}
	
	@Override
	public List<BlockDataProperty<?>> getProperties() {
		return PROPERTIES;
	}

}
