package de.atlasmc.world;

import java.io.IOException;

import de.atlasmc.util.nbt.AbstractNBTBase;
import de.atlasmc.util.nbt.NBTFieldContainer;
import de.atlasmc.util.nbt.io.NBTWriter;

public class Dimension extends AbstractNBTBase {
	
	private final String name;
	private final int id;
	private String infiniburn;
	private String effects;
	private boolean piglinSafe;
	private boolean natural;
	private boolean allowRespawnAnchor;
	private boolean skylight;
	private boolean allowBeds;
	private boolean raids;
	private boolean ultrawarm;
	private boolean ceiling;
	private float ambientLight;
	private float coordinateScale;
	private long fixedTime;
	private int logicalHeight;
	
	private final static NBTFieldContainer NBT_FIELDS;
	
	protected static final String
	NAME = "name",
	ID = "id",
	ELEMENT = "element",
	PIGLIN_SAFE = "piglin_safe",
	NATURAL = "natural",
	AMBIENT_LIGHT = "ambient_light",
	FIXED_TIME = "fixed_time",
	INFINIBURN = "infiniburn",
	RESPAWN_ANCHOR_WORKS = "respawn_anchor_works",
	HAS_SKYLIGHT = "has_skylight",
	BED_WORKS = "bed_works",
	EFFECTS = "effects",
	HAS_RAIDS = "has_raids",
	LOGICAL_HEIGHT = "logical_height",
	COORDINATE_SCALE = "coordinate_scale",
	ULTRAWARM = "ultrawarm",
	HAS_CEILING = "has_ceiling";
	
	static {
		NBT_FIELDS = new NBTFieldContainer();
		// TODO NBT Fields
	}
	
	public Dimension(String name, int id) {
		this.name = name;
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public int getID() {
		return id;
	}
	
	public String getInfiniburn() {
		return infiniburn;
	}
	
	public String getEffects() {
		return effects;
	}
	
	public boolean isPiglinSafe() {
		return piglinSafe;
	}
	
	public boolean isNatural() {
		return natural;
	}
	
	public boolean isAllowRespawnAnchor() {
		return allowRespawnAnchor;
	}
	
	public boolean isSkylight() {
		return skylight;
	}
	
	public boolean isAllowBeds() {
		return allowBeds;
	}
	
	public boolean isRaids() {
		return raids;
	}
	
	public boolean isUltrawarm() {
		return ultrawarm;
	}
	
	public boolean isCeiling() {
		return ceiling;
	}
	
	public float getAmbientLight() {
		return ambientLight;
	}
	
	public float getCoordinateScale() {
		return coordinateScale;
	}
	
	public long getFixedTime() {
		return fixedTime;
	}
	
	public int getLogicalHeight() {
		return logicalHeight;
	}
	
	public void setInfiniburn(String infiniburn) {
		this.infiniburn = infiniburn;
	}
	
	public void setEffects(String effects) {
		this.effects = effects;
	}
	
	public void setPiglinSafe(boolean piglinSafe) {
		this.piglinSafe = piglinSafe;
	}
	
	public void setNatural(boolean natural) {
		this.natural = natural;
	}
	
	public void setAllowRespawnAnchor(boolean allowRespawnAnchor) {
		this.allowRespawnAnchor = allowRespawnAnchor;
	}
	
	public void setSkylight(boolean skylight) {
		this.skylight = skylight;
	}
	
	public void setAllowBeds(boolean allowBeds) {
		this.allowBeds = allowBeds;
	}
	
	public void setRaids(boolean raids) {
		this.raids = raids;
	}
	
	public void setUltrawarm(boolean ultrawarm) {
		this.ultrawarm = ultrawarm;
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
		writer.writeStringTag(NAME, name);
		writer.writeIntTag(ID, id);
		writer.writeCompoundTag(ELEMENT);
		{
			writer.writeByteTag(PIGLIN_SAFE, piglinSafe);
			writer.writeByteTag(NATURAL, natural);
			writer.writeFloatTag(AMBIENT_LIGHT, ambientLight);
			if (fixedTime >= 0) writer.writeLongTag(FIXED_TIME, fixedTime);
			writer.writeStringTag(INFINIBURN, infiniburn);
			writer.writeByteTag(RESPAWN_ANCHOR_WORKS, allowRespawnAnchor);
			writer.writeByteTag(HAS_SKYLIGHT, skylight);
			writer.writeByteTag(BED_WORKS, allowBeds);
			writer.writeStringTag(EFFECTS, effects);
			writer.writeByteTag(HAS_RAIDS, raids);
			writer.writeIntTag(LOGICAL_HEIGHT, logicalHeight);
			writer.writeFloatTag(COORDINATE_SCALE, coordinateScale);
			writer.writeByteTag(ULTRAWARM, ultrawarm);
			writer.writeByteTag(HAS_CEILING, ceiling);
		}
		writer.writeEndTag();
	}

	@Override
	protected NBTFieldContainer getFieldContainerRoot() {
		return NBT_FIELDS;
	}

}
