package de.atlasmc.util.json;

import java.util.HashMap;
import java.util.Iterator;

public class JsonObject implements JsonElement {

	public final HashMap<String, JsonElement> fields;
	
	public JsonObject() {
		fields = new HashMap<String, JsonElement>();
	}
	
	public boolean hasField(String field) {
		return fields.containsKey(field);
	}

	@Override
	public ElementType getType() {
		return ElementType.JSON_OBJECT;
	}

	@Override
	public Object getValue() {
		return fields;
	}
	
	@Override
	public String toString() {
		return toJsonString();
	}

	@Override
	public StringBuilder toJsonString(StringBuilder builder) {
		if (fields.isEmpty()) return builder.append("{}");
		Iterator<String> keys = fields.keySet().iterator();
		builder.append("{ ");
		while (keys.hasNext()) {
			String key = keys.next();
			builder.append('"').append(keys).append("\": ");
			fields.get(key).toJsonString(builder);
			if (keys.hasNext()) {
				builder.append(", ");
			}
		}
		return builder.append(" }");
	}

	@Override
	public String toJsonString() {
		StringBuilder builder = new StringBuilder();
		toJsonString(builder);
		return builder.toString();
	}
	
}
