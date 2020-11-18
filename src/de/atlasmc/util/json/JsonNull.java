package de.atlasmc.util.json;

public class JsonNull implements JsonElement {

	@Override
	public ElementType getType() {
		return ElementType.JSON_NULL;
	}

	@Override
	public Object getValue() {
		return null;
	}

	@Override
	public String toString() {
		return "null";
	}
	
	@Override
	public String toJsonString() {
		return "null";
	}

	@Override
	public StringBuilder toJsonString(StringBuilder builder) {
		return builder.append("null");
	}

}
