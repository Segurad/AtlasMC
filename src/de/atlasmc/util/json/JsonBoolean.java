package de.atlasmc.util.json;

public class JsonBoolean implements JsonElement {

	public boolean value;
	
	public JsonBoolean() {}
	
	public JsonBoolean(boolean value) {
		this.value = value;
	}
	
	@Override
	public ElementType getType() {
		return ElementType.JSON_BOOLEAN;
	}

	@Override
	public Object getValue() {
		return value;
	}

	@Override
	public String toString() {
		return Boolean.toString(value);
	}
	
	@Override
	public StringBuilder toJsonString(StringBuilder builder) {
		return builder.append(value);
	}

	@Override
	public String toJsonString() {
		return Boolean.toString(value);
	}

}
