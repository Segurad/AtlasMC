package de.atlasmc.util.json;

public enum ElementType {

	JSON_OBJECT,
	JSON_NUMBER,
	JSON_STRING,
	JSON_BOOLEAN,
	JSON_ARRAY,
	JSON_NULL;
	
	public JsonElement create() {
		switch (this) {
		case JSON_NUMBER: return new JsonNumber();
		case JSON_STRING: return new JsonString();
		case JSON_BOOLEAN: return new JsonBoolean();
		case JSON_NULL: return Json.JSON_NULL;
		case JSON_OBJECT: return new JsonObject();
		case JSON_ARRAY: return new JsonArray();
		default:
			return Json.JSON_NULL;
		}
	}
}
