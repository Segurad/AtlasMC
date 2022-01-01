package de.atlasmc.chat.component;

import java.io.Reader;
import java.io.StringReader;

import com.google.gson.stream.JsonReader;

public class ChatReader extends JsonReader {

	public ChatReader(Reader in) {
		super(in);
	}
	
	public ChatReader(String text) {
		super(new StringReader(text));
	}
	
	public ChatComponent readChat() {
		return null;
	}

}
