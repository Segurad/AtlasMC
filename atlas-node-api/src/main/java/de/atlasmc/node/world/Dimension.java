package de.atlasmc.node.world;

import de.atlasmc.NamespacedKey;
import de.atlasmc.NamespacedKey.Namespaced;
import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.nbt.codec.NBTCodecs;
import de.atlasmc.nbt.codec.NBTSerializable;
import de.atlasmc.registry.ProtocolRegistryValueBase;
import de.atlasmc.util.annotation.Nullable;

public class Dimension extends ProtocolRegistryValueBase implements NBTSerializable, Namespaced {
	
	public static final NBTCodec<Dimension> 
	NBT_HANDLER = NBTCodec
					.builder(Dimension.class)
					.boolField("natural", Dimension::isNatural, Dimension::setNatural)
					.boolField("has_skylight", Dimension::hasSkyLight, Dimension::setSkylight)
					.boolField("has_ceiling", Dimension::hasCeiling, Dimension::setCeiling)
					.floatField("ambient_light", Dimension::getAmbientLight, Dimension::setAmbientLight)
					.codec("fixed_time", Dimension::getFixedTime, Dimension::setFixedTime, NBTCodecs.LONG)
					.intField("logical_height", Dimension::getLogicalHeight, Dimension::setLogicalHeight)
					.intField("cloud_height", Dimension::getCloudHeight, Dimension::setCloudHeight)
					.intField("min_y", Dimension::getMinY, Dimension::setMinY)
					.intField("height", Dimension::getHeight, Dimension::setHeight)
					.codec("effects", Dimension::getEffects, Dimension::setEffects, NamespacedKey.NBT_CODEC)
					.build();
	
	private boolean natural;
	private boolean skylight;
	private boolean ceiling;
	private NamespacedKey effects;
	private float ambientLight;
	private Long fixedTime;
	private int logicalHeight;
	private int cloudHeight;
	private int minY;
	private int height;
	
	public Dimension(NamespacedKey key, int id) {
		super(key, id);
	}
	
	public Dimension(NamespacedKey key, NamespacedKey clientKey, int id) {
		super(key, clientKey, id);
	}
	
	public void setHeight(int height) {
		this.height = height;
	}
	
	public void setMinY(int minY) {
		this.minY = minY;
	}
	
	public int getCloudHeight() {
		return cloudHeight;
	}
	
	public void setCloudHeight(int cloudHeight) {
		this.cloudHeight = cloudHeight;
	}
	
	public NamespacedKey getEffects() {
		return effects;
	}
	
	public boolean hasCeiling() {
		return ceiling;
	}
	
	public void setCeiling(boolean ceiling) {
		this.ceiling = ceiling;
	}
	
	public boolean hasSkyLight() {
		return skylight;
	}
	
	public boolean isNatural() {
		return natural;
	}
	
	public float getAmbientLight() {
		return ambientLight;
	}
	
	@Nullable
	public Long getFixedTime() {
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
	
	public void setNatural(boolean ceiling) {
		this.natural = ceiling;
	}
	
	public void setAmbientLight(float ambientLight) {
		this.ambientLight = ambientLight;
	}
	
	public void setFixedTime(Long fixedTime) {
		this.fixedTime = fixedTime;
	}
	
	public void setLogicalHeight(int logicalHeight) {
		this.logicalHeight = logicalHeight;
	}

	@Override
	public NBTCodec<? extends Dimension> getNBTCodec() {
		return NBT_HANDLER;
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
