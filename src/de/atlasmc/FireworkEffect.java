package de.atlasmc;

import java.io.IOException;
import java.util.List;

import de.atlasmc.util.nbt.NBTHolder;
import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;

public class FireworkEffect implements NBTHolder {
	
	private int[] colors, fadeColors;
	private boolean flicker, trail;
	private Type type;
	
	protected static final String
	NBT_COLORS = "Color",
	NBT_FADE_COLOR = "FadeColor",
	NBT_FLICKER = "Flicker",
	NBT_TRAIL = "Trail",
	NBT_TYPE = "Type";
	
	public static enum Type {
		BALL,
		BALL_LARGE,
		STAR,
		CREEPER,
		BURST;

		private static List<Type> VALUES;
		
		public int getID() {
			return ordinal();
		}
		
		public static Type getByID(int id) {
			return getValues().get(id);
		}
		
		/**
		 * Returns a immutable List of all Types.<br>
		 * This method avoid allocation of a new array not like {@link #values()}.
		 * @return list
		 */
		public static List<Type> getValues() {
			if (VALUES == null)
				VALUES = List.of(values());
			return VALUES;
		}
		
		/**
		 * Releases the system resources used from the values cache
		 */
		public static void freeValues() {
			VALUES = null;
		}
	}
	
	public FireworkEffect() {
		type = Type.BALL;
	}
	
	public Type getType() {
		return type;
	}
	
	public void setType(Type type) {
		if (type == null) throw new IllegalArgumentException("Type can not be null!");
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
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		if (colors != null)
			writer.writeIntArrayTag(NBT_COLORS, colors);
		if (fadeColors != null)
			writer.writeIntArrayTag(NBT_FADE_COLOR, fadeColors);
		if (hasFlicker()) 
			writer.writeByteTag(NBT_FLICKER, 1);
		if (hasTrail()) 
			writer.writeByteTag(NBT_TRAIL, 1);
		writer.writeByteTag(NBT_TYPE, type.getID());
	}

	@Override
	public void fromNBT(NBTReader reader) throws IOException {
		while (reader.getType() != TagType.TAG_END) {
			switch (reader.getFieldName()) {
			case NBT_COLORS:
				colors = reader.readIntArrayTag();
				break;
			case NBT_FADE_COLOR:
				fadeColors = reader.readIntArrayTag();
				break;
			case NBT_FLICKER:
				flicker = reader.readByteTag() == 1;
				break;
			case NBT_TRAIL:
				trail = reader.readByteTag() == 1;
			case NBT_TYPE:
				type = Type.getByID(reader.readByteTag());
				break;
			default:
				reader.skipTag();
			}
		}
		reader.readNextEntry();
	}
	
	public FireworkEffect clone() {
		FireworkEffect clone = null;
		try {
			clone = (FireworkEffect) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		if (clone == null)
			return null;
		if (colors != null)
			clone.colors = colors.clone();
		if (fadeColors != null)
			clone.fadeColors = colors.clone();
		return clone;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (obj == this)
			return true;
		if (obj.getClass() != getClass())
			return false;
		FireworkEffect effect = (FireworkEffect) obj;
		if (getType() != effect.getType())
			return false;
		if (hasFlicker() != effect.hasFlicker())
			return false;
		if (hasTrail() != effect.hasTrail())
			return false;
		// TODO color and fade color
		return true;
	}

}
