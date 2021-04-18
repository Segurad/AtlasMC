package de.atlasmc.util.json.io;

public interface JsonWriter {
	
	public void writeObject();
	public void writeObject(String name);
	public void writeString(String name, String value);
	public void writeArray(String name);
	public void writeInt(String name, int value);
	public void writeBoolean(String name, boolean value);
	public void writeDouble(String name, double value);
	public void writeEndObject();
	public void writeEndArray();

}
