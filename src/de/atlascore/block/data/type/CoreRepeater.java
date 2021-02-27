package de.atlascore.block.data.type;

import de.atlascore.block.data.CoreDirectional4Faces;
import de.atlasmc.Material;
import de.atlasmc.block.data.type.Repeater;
import de.atlasmc.util.Validate;

public class CoreRepeater extends CoreDirectional4Faces implements Repeater {

	private boolean powered, locked;
	private int delay;
	
	public CoreRepeater(Material material) {
		super(material);
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
		Validate.isTrue(delay > 0 && delay < 5, "Delay is not between 1 and 4: " + delay);
		this.delay = delay;
	}

	@Override
	public void setLocked(boolean locked) {
		this.locked = locked;
	}
	
	@Override
	public int getStateID() {
		return getMaterial().getBlockID()+
				(powered?0:1)+
				(locked?0:2)+
				getFaceValue()*4+
				delay*16;
	}

}
