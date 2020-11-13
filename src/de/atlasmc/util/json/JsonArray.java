package de.atlasmc.util.json;

import java.util.ArrayList;
import java.util.List;

public class JsonArray implements JsonContainer {
	
	public final List<JsonContainer> values;
	
	public JsonArray() {
		values = new ArrayList<JsonContainer>();
	}
	
	public JsonArray(List<JsonContainer> values) {
		this.values = new ArrayList<JsonContainer>(values);
	}

	@Override
	public ContainerType getType() {
		return ContainerType.JSON_ARRAY;
	}

}
