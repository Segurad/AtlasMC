package de.atlasmc.atlasnetwork.player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import de.atlasmc.util.CloneException;
import de.atlasmc.util.nbt.serialization.NBTSerializable;
import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public class PlayerProfile implements NBTSerializable, Cloneable {
	
	public static final NBTSerializationHandler<PlayerProfile>
	NBT_HANDLER = NBTSerializationHandler
					.builder(PlayerProfile.class)
					.defaultConstructor(PlayerProfile::new)
					.string("name", PlayerProfile::getName, PlayerProfile::setName)
					.uuid("id", PlayerProfile::getUUID, PlayerProfile::setUUID)
					.typeList("properties", PlayerProfile::hasProperties, PlayerProfile::getProperties, ProfileProperty.NBT_HANDLER)
					.build();
	
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
	public NBTSerializationHandler<? extends PlayerProfile> getNBTHandler() {
		return NBT_HANDLER;
	}

}
