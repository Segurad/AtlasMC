package de.atlascore.block.data.type;

import java.io.IOException;

import de.atlascore.block.data.CoreDirectional6Faces;
import de.atlasmc.Material;
import de.atlasmc.block.data.type.Dispenser;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.io.NBTWriter;

public class CoreDispenser extends CoreDirectional6Faces implements Dispenser {

	protected static final CharKey
	TRIGGERED = CharKey.of("triggered");
	
	static {
		NBT_FIELDS.setField(TRIGGERED, (holder, reader) -> {
			if (holder instanceof Dispenser)
			((Dispenser) holder).setTriggered(reader.readByteTag() == 1);
			else reader.skipTag();
		});
	}
	
	public boolean triggered;
	
	public CoreDispenser(Material material) {
		super(material);
	}

	@Override
	public boolean isTriggered() {
		return triggered;
	}

	@Override
	public void setTriggered(boolean triggered) {
		this.triggered = triggered;
	}

	@Override
	public int getStateID() {
		return getMaterial().getBlockID()+getFaceValue(getFacing())*2+(triggered?0:1);
	}
	
	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		super.toNBT(writer, systemData);
		if (isTriggered()) writer.writeByteTag(TRIGGERED, true);
	}

}
