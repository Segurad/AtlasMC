package de.atlasmc.core.node.block.data.type;

import java.util.List;

import de.atlasmc.core.node.block.data.CoreDirectional4Faces;
import de.atlasmc.node.block.BlockType;
import de.atlasmc.node.block.data.property.PropertyType;
import de.atlasmc.node.block.data.type.Comparator;

public class CoreComparator extends CoreDirectional4Faces implements Comparator {

	protected static final List<PropertyType<?>> PROPERTIES;
	
	static {
		PROPERTIES = merge(CoreDirectional4Faces.PROPERTIES, 
				PropertyType.MODE,
				PropertyType.POWERED);
	}
	
	private Mode mode;
	private boolean powered;
	
	public CoreComparator(BlockType type) {
		super(type);
		mode = Mode.COMPARE;
	}

	@Override
	public boolean isPowered() {
		return powered;
	}

	@Override
	public void setPowered(boolean powered) {
		this.powered = powered;
	}

	@Override
	public Mode getMode() {
		return mode;
	}

	@Override
	public void setMode(Mode mode) {
		if (mode == null) throw new IllegalArgumentException("Mode can not be null!");
		this.mode = mode;
	}
	
	@Override
	public int getStateID() {
		return getType().getBlockStateID()+
				(powered?0:1)+
				mode.ordinal()*2+
				getFaceValue()*4;
	}
	
	@Override
	public List<PropertyType<?>> getProperties() {
		return PROPERTIES;
	}

}
