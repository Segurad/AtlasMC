package de.atlasmc.util.json;

public class JsonBoolean implements JsonContainer {

	public boolean value;
	
	public JsonBoolean() {}
	
	public JsonBoolean(boolean value) {
		this.value = value;
	}
	
	@Override
	public ContainerType getType() {
		return ContainerType.JSON_BOOLEAN;
	}

}
