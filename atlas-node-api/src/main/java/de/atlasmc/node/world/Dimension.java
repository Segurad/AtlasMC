package de.atlasmc.node.world;

import java.io.IOException;

import de.atlasmc.NamespacedKey;
import de.atlasmc.NamespacedKey.Namespaced;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.AbstractNBTBase;
import de.atlasmc.util.nbt.NBTFieldSet;
import de.atlasmc.util.nbt.NBTHolder;
import de.atlasmc.util.nbt.io.NBTWriter;

public class Dimension extends AbstractNBTBase implements NBTHolder, Namespaced {
	
	protected final static NBTFieldSet<Dimension> NBT_FIELDS;
	
	protected static final CharKey
	NBT_NAME = CharKey.literal("name"),
	NBT_ID = CharKey.literal("id"),
	NBT_ELEMENT = CharKey.literal("element"),
	NBT_PIGLIN_SAFE = CharKey.literal("piglin_safe"),
	NBT_NATURAL = CharKey.literal("natural"),
	NBT_AMBIENT_LIGHT = CharKey.literal("ambient_light"),
	NBT_FIXED_TIME = CharKey.literal("fixed_time"),
	NBT_INFINIBURN = CharKey.literal("infiniburn"),
	NBT_RESPAWN_ANCHOR_WORKS = CharKey.literal("respawn_anchor_works"),
	NBT_HAS_SKYLIGHT = CharKey.literal("has_skylight"),
	NBT_BED_WORKS = CharKey.literal("bed_works"),
	NBT_EFFECTS = CharKey.literal("effects"),
	NBT_HAS_RAIDS = CharKey.literal("has_raids"),
	NBT_LOGICAL_HEIGHT = CharKey.literal("logical_height"),
	NBT_COORDINATE_SCALE = CharKey.literal("coordinate_scale"),
	NBT_ULTRAWARM = CharKey.literal("ultrawarm"),
	NBT_HAS_CEILING = CharKey.literal("has_ceiling"),
	NBT_MIN_Y = CharKey.literal("min_y"),
	NBT_HEIGHT = CharKey.literal("height");
	
	static {
		NBT_FIELDS = NBTFieldSet.newSet();
		NBT_FIELDS.setField(NBT_NAME, (holder, reader) -> {
			holder.name = reader.readNamespacedKey();
		});
		NBT_FIELDS.setField(NBT_ID, (holder, reader) -> {
			holder.id = reader.readIntTag();
		});
		NBTFieldSet<Dimension> DATA = NBT_FIELDS.setSet(NBT_ELEMENT);
		DATA.setField(NBT_AMBIENT_LIGHT, (holder, reader) -> {
			holder.ambientLight = reader.readFloatTag();
		});
		DATA.setField(NBT_FIXED_TIME, (holder, reader) -> {
			holder.fixedTime = reader.readLongTag();
		});
		DATA.setField(NBT_HAS_SKYLIGHT, (holder, reader) -> {
			holder.skylight = reader.readByteTag() == 1;
		});
		DATA.setField(NBT_EFFECTS, (holder, reader) -> {
			holder.effects = reader.readNamespacedKey();
		});
		DATA.setField(NBT_LOGICAL_HEIGHT, (holder, reader) -> {
			holder.logicalHeight = reader.readIntTag();
		});
		DATA.setField(NBT_COORDINATE_SCALE, (holder, reader) -> {
			holder.coordinateScale = reader.readFloatTag();
		});
		DATA.setField(NBT_HAS_CEILING, (holder, reader) -> {
			holder.ceiling = reader.readByteTag() == 1;
		});
		DATA.setField(NBT_MIN_Y, (holder, reader) -> {
			holder.minY = reader.readIntTag();
		});
		DATA.setField(NBT_HEIGHT, (holder, reader) -> {
			holder.height = reader.readIntTag();
		});
	}
	
	private NamespacedKey name;
	private int id;
	private NamespacedKey effects;
	private boolean skylight;
	private boolean ceiling;
	private float ambientLight;
	private float coordinateScale;
	private long fixedTime;
	private int logicalHeight;
	private int minY;
	private int height;
	
	public Dimension(NamespacedKey name, int id) {
		this.name = name;
		this.id = id;
	}
	
	public int getID() {
		return id;
	}
	
	public NamespacedKey getEffects() {
		return effects;
	}
	
	public boolean hasSkyLight() {
		return skylight;
	}
	
	public boolean hasCeiling() {
		return ceiling;
	}
	
	public float getAmbientLight() {
		return ambientLight;
	}
	
	public long getFixedTime() {
		return fixedTime;
	}
	
	public int getLogicalHeight() {
		return logicalHeight;
	}
	
	public void setEffects(NamespacedKey effects) {
		this.effects = effects;
	}
	
	public void setSkylight(boolean skylight) {
		this.skylight = skylight;
	}
	
	public void setCeiling(boolean ceiling) {
		this.ceiling = ceiling;
	}
	
	public void setAmbientLight(float ambientLight) {
		this.ambientLight = ambientLight;
	}
	
	public void setCoordinateScale(float coordinateScale) {
		this.coordinateScale = coordinateScale;
	}
	
	public void setFixedTime(long fixedTime) {
		this.fixedTime = fixedTime;
	}
	
	public void setLogicalHeight(int logicalHeight) {
		this.logicalHeight = logicalHeight;
	}
	
	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		writer.writeStringTag(NBT_NAME, name.toString());
		writer.writeIntTag(NBT_ID, id);
		writer.writeCompoundTag(NBT_ELEMENT);
		writeDimensionData(writer, systemData);
		writer.writeEndTag();
	}
	
	protected void writeDimensionData(NBTWriter writer, boolean systemData) throws IOException {
		writer.writeFloatTag(NBT_AMBIENT_LIGHT, ambientLight);
		if (fixedTime >= 0) 
			writer.writeLongTag(NBT_FIXED_TIME, fixedTime);
		writer.writeByteTag(NBT_HAS_SKYLIGHT, skylight);
		writer.writeStringTag(NBT_EFFECTS, effects.toString());
		writer.writeIntTag(NBT_LOGICAL_HEIGHT, logicalHeight);
		writer.writeFloatTag(NBT_COORDINATE_SCALE, coordinateScale);
		writer.writeByteTag(NBT_HAS_CEILING, ceiling);
		writer.writeIntTag(NBT_HEIGHT, height);
		writer.writeIntTag(NBT_MIN_Y, minY);
	}

	@Override
	protected NBTFieldSet<? extends Dimension> getFieldSetRoot() {
		return NBT_FIELDS;
	}

	@Override
	public NamespacedKey getNamespacedKey() {
		return name;
	}

	public int getMinY() {
		return minY;
	}

	public int getHeight() {
		return height;
	}

	public boolean hasFixedTime() {
		return fixedTime >= 0;
	}

}
