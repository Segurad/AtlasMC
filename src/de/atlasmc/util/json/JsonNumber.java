package de.atlasmc.util.json;

public class JsonNumber implements JsonContainer {
	
	public Number num;
	
	public JsonNumber(String num) {
		if (num.contains(".") || num.contains("e"))
			this.num = Double.parseDouble(num);
		else
			this.num = Long.parseLong(num);
	}
	
	public JsonNumber(Number num) {
		this.num = num;
	}
	
	public int getInt() {
		return (int) num;
	}
	
	public double getDouble() {
		return (double) num;
	}
	
	public long getLong() {
		return (long) num;
	}
	
	public byte getByte() {
		return (byte) num;
	}
	
	public float getFloat() {
		return (float) num;
	}
	
	@Override
	public ContainerType getType() {
		return ContainerType.JSON_NUMBER;
	}
}
