package de.atlasmc.util.json;

public class JsonString implements JsonContainer {

	public String value;
	
	public JsonString(String value) {
		this.value = value;
	}

	@Override
	public ContainerType getType() {
		return ContainerType.JSON_STRING;
	}
}
