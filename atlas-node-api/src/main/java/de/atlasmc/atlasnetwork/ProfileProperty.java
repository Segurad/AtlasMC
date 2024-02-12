package de.atlasmc.atlasnetwork;

public class ProfileProperty {
	
	private final String name;
	private final String value;
	private final String signature;
	
	public ProfileProperty(String name, String value) {
		this(name, value, null);
	}
	
	public ProfileProperty(String name, String value, String signature) {
		this.name = name;
		this.value = value;
		this.signature = signature;
	}
	
	public String getName() {
		return name;
	}
	
	public String getValue() {
		return value;
	}
	
	public String getSignature() {
		return signature;
	}

}
