package de.atlasmc.core.node.block.data.type;

import java.util.List;

import de.atlasmc.core.node.block.data.CoreDirectional4Faces;
import de.atlasmc.node.block.BlockType;
import de.atlasmc.node.block.data.property.PropertyType;
import de.atlasmc.node.block.data.type.Repeater;

public class CoreRepeater extends CoreDirectional4Faces implements Repeater {

	protected static final List<PropertyType<?>> PROPERTIES;
	
	static {
		PROPERTIES = merge(CoreDirectional4Faces.PROPERTIES, 
				PropertyType.POWERED,
				PropertyType.LOCKED,
				PropertyType.DELAY);
	}
	
	private boolean powered;
	private boolean locked;
	private int delay;
	
	public CoreRepeater(BlockType type) {
		super(type);
		delay = 1;
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
	public int getDelay() {
		return delay;
	}

	@Override
	public int getMaxDelay() {
		return 4;
	}

	@Override
	public int getMinDelay() {
		return 1;
	}

	@Override
	public boolean isLocked() {
		return locked;
	}

	@Override
	public void setDelay(int delay) {
		if (delay < 1 || delay > 4) 
			throw new IllegalArgumentException("Delay is not between 1 and 4: " + delay);
		this.delay = delay;
	}

	@Override
	public void setLocked(boolean locked) {
		this.locked = locked;
	}
	
	@Override
	public int getStateID() {
		return getType().getBlockStateID()+
				(powered?0:1)+
				(locked?0:2)+
				getFaceValue()*4+
				(delay-1)*16;
	}
	
	@Override
	public List<PropertyType<?>> getProperties() {
		return PROPERTIES;
	}

}
