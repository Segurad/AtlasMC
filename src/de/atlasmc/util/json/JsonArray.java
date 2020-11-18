package de.atlasmc.util.json;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class JsonArray implements JsonElement {
	
	public final List<JsonElement> values;
	
	public JsonArray() {
		values = new ArrayList<JsonElement>();
	}
	
	public JsonArray(List<JsonElement> values) {
		this.values = new ArrayList<JsonElement>(values);
	}

	@Override
	public ElementType getType() {
		return ElementType.JSON_ARRAY;
	}

	@Override
	public Object getValue() {
		return values;
	}
	
	@Override
	public String toString() {
		return toJsonString();
	}

	@Override
	public StringBuilder toJsonString(StringBuilder builder) {
		if (values.isEmpty()) return builder.append("[]");
		Iterator<JsonElement> values = this.values.iterator();
		builder.append('[');
		while (values.hasNext()) {
			values.next().toJsonString(builder);
			if (values.hasNext()) builder.append(", ");
		}
		return builder.append(']');
	}

	@Override
	public String toJsonString() {
		StringBuilder builder = new StringBuilder();
		toJsonString(builder);
		return builder.toString();
	}

}
