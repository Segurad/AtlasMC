package de.atlasmc;

import java.io.IOException;
import java.util.List;

import de.atlasmc.util.Validate;
import de.atlasmc.util.nbt.NBTHolder;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;

public class FireworkEffect implements NBTHolder {
	
	// TODO
	private List<Color> colors, fadeColors;
	private boolean flicker, trail;
	private Type type;
	
	protected static final String
			FLICKER = "Flicker",
			TRAIL = "Trail",
			TYPE = "Type";
	
	public static enum Type {
		BALL,
		BALL_LARGE,
		STAR,
		CREEPER,
		BURST
	}
	
	public FireworkEffect() {
		type = Type.BALL;
	}
	
	public Type getType() {
		return type;
	}
	
	public void setType(Type type) {
		Validate.notNull(type, "Type can not be null!");
		this.type = type;
	}
	
	public boolean hasFlicker() {
		return flicker;
	}
	
	public void setFlicker(boolean flicker) {
		this.flicker = flicker;
	}
	
	public boolean hasTrail() {
		return trail;
	}
	
	public void setTrail(boolean trail) {
		this.trail = trail;
	}
	
	@Override
	public void toNBT(NBTWriter writer, String local, boolean systemData) throws IOException {
		if (hasFlicker()) writer.writeByteTag(FLICKER, 1);
		if (hasTrail()) writer.writeByteTag(TRAIL, 1);
		writer.writeByteTag(TYPE, type.ordinal());
	}

	@Override
	public void fromNBT(NBTReader reader) throws IOException {
		// TODO
	}

}
