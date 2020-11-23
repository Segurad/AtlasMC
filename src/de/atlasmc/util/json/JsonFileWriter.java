package de.atlasmc.util.json;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class JsonFileWriter extends FileWriter {
	
	public JsonFileWriter(File file) throws IOException {
		super(file);
	}
	
	public JsonFileWriter(File file, boolean append) throws IOException {
		super(file, append);
	}
	
	public void writeJson(JsonElement json) {
		writeJson(json.toJsonString());
	}
	
	public void writeJson(String json) {
		
	}

}
