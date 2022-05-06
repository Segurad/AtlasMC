package de.atlascore.block.data.type;

import java.io.IOException;

import de.atlascore.block.data.CoreDirectional4Faces;
import de.atlasmc.Material;
import de.atlasmc.block.data.type.Repeater;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.io.NBTWriter;

public class CoreRepeater extends CoreDirectional4Faces implements Repeater {

	protected static final CharKey
	LOCKED = CharKey.of("locked"),
	DELAY = CharKey.of("delay");
	
	static {
		NBT_FIELDS.setField(LOCKED, (holder, reader) -> {
			if (holder instanceof Repeater)
			((Repeater) holder).setLocked(reader.readByteTag() == 1);
			else reader.skipTag();
		});
		NBT_FIELDS.setField(DELAY, (holder, reader) -> {
			if (holder instanceof Repeater)
			((Repeater) holder).setDelay(reader.readIntTag());
			else reader.skipTag();
		});
	}
	
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
		if (delay < 1 || delay > 4) throw new IllegalArgumentException("Delay is not between 1 and 4: " + delay);
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
	
	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		super.toNBT(writer, systemData);
		if (isLocked()) writer.writeByteTag(LOCKED, true);
		if (getDelay() > 1) writer.writeByteTag(DELAY, getDelay());
	}

}
