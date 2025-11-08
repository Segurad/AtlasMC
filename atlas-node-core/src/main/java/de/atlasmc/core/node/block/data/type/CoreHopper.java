package de.atlasmc.core.node.block.data.type;

import java.util.List;
import java.util.Set;

import de.atlasmc.core.node.block.data.CoreAbstractDirectional;
import de.atlasmc.node.block.BlockFace;
import de.atlasmc.node.block.BlockType;
import de.atlasmc.node.block.data.property.PropertyType;
import de.atlasmc.node.block.data.type.Hopper;

public class CoreHopper extends CoreAbstractDirectional implements Hopper {
	
	private static final Set<BlockFace> ALLOWED_FACES =
			Set.of(BlockFace.NORTH,
					BlockFace.EAST,
					BlockFace.SOUTH,
					BlockFace.WEST,
					BlockFace.DOWN);
	
	protected static final List<PropertyType<?>> PROPERTIES;
	
	static {
		PROPERTIES = merge(CoreAbstractDirectional.PROPERTIES, PropertyType.ENABLED);
	}
	
	private boolean enabled;
	
	public CoreHopper(BlockType type) {
		super(type, BlockFace.DOWN);
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
		return type.getBlockStateID()+
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
	public List<PropertyType<?>> getProperties() {
		return PROPERTIES;
	}

}
