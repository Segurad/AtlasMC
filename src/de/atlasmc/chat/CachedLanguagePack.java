package de.atlasmc.chat;

import java.util.HashMap;
import java.util.Set;

public class CachedLanguagePack implements LanguagePack {

	private final String langkey;
	private final HashMap<String, String> msgs;

	public CachedLanguagePack(String langkey) {
		this.langkey = langkey;
		this.msgs = new HashMap<>();
	}

	public String getLang() {
		return langkey;
	}

	public String get(String key) {
		return msgs.get(key);
	}
	
	public boolean hasKey(String key) {
		return msgs.containsKey(key);
	}

	public void add(String key, String msg) {
		msgs.put(key, msg);
	}

	public void remove(String key) {
		msgs.remove(key);
	}

	public Set<String> getKeys() {
		return msgs.keySet();
	}
}