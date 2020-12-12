package de.atlasmc.util.json;

import de.atlasmc.util.NumberConversion;
import de.atlasmc.util.Validate;

public class JsonNumber implements JsonElement {
	
	public String num;
	
	public JsonNumber(String number) {
		Validate.notNull(number, "Number can not be null!");
		Validate.isNumber(number, "Number is not a Number!");
		this.num = number;
	}
	
	public JsonNumber(Number num) {
		this.num = num.toString();
	}
	
	public JsonNumber() {
		this.num = "0";
	}

	public int getInt() {
		return NumberConversion.toInt(num);
	}
	
	public double getDouble() {
		return NumberConversion.toDouble(num);
	}
	
	public long getLong() {
		return NumberConversion.toLong(num);
	}
	
	public byte getByte() {
		return NumberConversion.toByte(num);
	}
	
	public float getFloat() {
		return NumberConversion.toFloat(num);
	}
	
	@Override
	public ElementType getType() {
		return ElementType.JSON_NUMBER;
	}

	@Override
	public Object getValue() {
		return num;
	}
	
	@Override
	public String toString() {
		return num;
	}

	@Override
	public StringBuilder toJsonString(StringBuilder builder) {
		return builder.append(num);
	}

	@Override
	public String toJsonString() {
		return num;
	}
}
