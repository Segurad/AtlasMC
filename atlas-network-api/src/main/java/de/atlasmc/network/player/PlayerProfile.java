package de.atlasmc.network.player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.nbt.codec.NBTCodecs;
import de.atlasmc.nbt.codec.NBTSerializable;
import de.atlasmc.util.CloneException;

public class PlayerProfile implements NBTSerializable, Cloneable {
	
	public static final NBTCodec<PlayerProfile>
	NBT_CODEC = NBTCodec
					.builder(PlayerProfile.class)
					.defaultConstructor(PlayerProfile::new)
					.codec("name", PlayerProfile::getName, PlayerProfile::setName, NBTCodecs.STRING)
					.codec("id", PlayerProfile::getUUID, PlayerProfile::setUUID, NBTCodecs.UUID_CODEC)
					.codecList("properties", PlayerProfile::hasProperties, PlayerProfile::getProperties, ProfileProperty.NBT_HANDLER)
					.build();
	
	public static final NBTCodec<PlayerProfile> NAME_NBT_CODEC = NBTCodec.stringToObject(PlayerProfile.class, PlayerProfile::new, PlayerProfile::getName);
	
	private String name;
	private UUID uuid;
	private List<ProfileProperty> properties;
	
	public PlayerProfile() {
		this(null);
	}
	
	public PlayerProfile(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public UUID getUUID() {
		return uuid;
	}
	
	public void setUUID(UUID uuid) {
		this.uuid = uuid;
	}
	
	public boolean hasProperties() {
		return properties != null && !properties.isEmpty();
	}
	
	public List<ProfileProperty> getProperties() {
		if (properties == null)
			properties = new ArrayList<>();
		return properties;
	}
	
	public void addProperty(ProfileProperty property) {
		getProperties().add(property);
	}
	
	@Override
	public PlayerProfile clone() {
		try {
			PlayerProfile profile = (PlayerProfile) super.clone();
			if (hasProperties()) {
				profile.properties = new ArrayList<>(properties.size());
				for (ProfileProperty property : properties) {
					profile.properties.add(property.clone());
				}
			}
			return profile;
		} catch (CloneNotSupportedException e) {
			throw new CloneException(e);
		}
	}
	
	@Override
	public NBTCodec<? extends PlayerProfile> getNBTCodec() {
		return NBT_CODEC;
	}

}
