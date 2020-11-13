package de.atlasmc.util.json;

import java.util.HashMap;

public class JsonObject implements JsonContainer {

	public final HashMap<String, JsonContainer> fields;
	
	public JsonObject() {
		fields = new HashMap<String, JsonContainer>();
	}
	
	public boolean hasField(String field) {
		return fields.containsKey(field);
	}

	@Override
	public ContainerType getType() {
		return ContainerType.JSON_OBJECT;
	}
	
}
