package de.atlasmc.util.json;

public interface JsonElement {

	public ElementType getType();
	public Object getValue();
	public String toJsonString();
	public StringBuilder toJsonString(StringBuilder builder);
	
}
