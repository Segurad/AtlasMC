package de.atlasmc.atlasnetwork.player;

import de.atlasmc.util.nbt.serialization.NBTSerializable;
import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public class ProfileProperty implements NBTSerializable {

	public static final NBTSerializationHandler<ProfileProperty>
	NBT_HANDLER = NBTSerializationHandler
					.builder(ProfileProperty.class)
					.defaultConstructor(ProfileProperty::new)
					.string("name", ProfileProperty::getName, ProfileProperty::setName)
					.string("value", ProfileProperty::getValue, ProfileProperty::setValue)
					.string("signature", ProfileProperty::getSignature, ProfileProperty::setSignature)
					.build();
	
	private String name;
	private String value;
	private String signature;
	
	public ProfileProperty() {
		this(null, null, null);
	}
	
	public ProfileProperty(String name, String value, String signature) {
		this.name = name;
		this.value = value;
		this.signature = signature;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
	
	public String getSignature() {
		return signature;
	}
	
	public void setSignature(String signature) {
		this.signature = signature;
	}
	
	@Override
	public NBTSerializationHandler<? extends ProfileProperty> getNBTHandler() {
		return NBT_HANDLER;
	}
	
}
