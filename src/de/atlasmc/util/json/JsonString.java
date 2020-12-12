package de.atlasmc.util.json;

public class JsonString implements JsonElement {

	public String value;
	
	public JsonString(String value) {
		this.value = value;
	}

	public JsonString() {
		this.value = null;
	}

	@Override
	public ElementType getType() {
		return ElementType.JSON_STRING;
	}

	@Override
	public Object getValue() {
		return value;
	}
	
	@Override
	public String toString() {
		return value.toString();
	}
	
	@Override
	public StringBuilder toJsonString(StringBuilder builder) {
		return builder.append(toJsonString());
	}

	@Override
	public String toJsonString() {
		char[] charString = value.toCharArray();
		StringBuffer buffer = new StringBuffer(value.length());
		buffer.append('"');
		for (char c : charString) {
			if (c == '"') buffer.append("\\\"");
			else buffer.append(c);
		}
		buffer.append('"');
		return buffer.toString();
	}
}
