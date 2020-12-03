package de.atlasmc.lang;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class LanguagePack {

	private final String langkey;
	private final HashMap<String, String> msgs;

	public LanguagePack(String langkey) {
		this.langkey = langkey;
		this.msgs = new HashMap<String, String>();
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

	public List<String> getKeys() {
		List<String> keys = new ArrayList<String>();
		keys.addAll(msgs.keySet());
		return keys;
	}

	/**
	 * 
	 * @return the msg with the key "prefix"
	 */
	public String getPrefix() {
		return msgs.get("prefix");
	}

	/**
	 * 
	 * @return the msg with the key "join"
	 */
	public String getJoin() {
		return msgs.get("join");
	}

	/**
	 * 
	 * @return the msg with the key "quit"
	 */
	public String getQuit() {
		return msgs.get("quit");
	}

	/**
	 * 
	 * @return the msg with the key "no_perm"
	 */
	public String getNoPermission() {
		return msgs.get("no_perm");
	}

	public List<String> getDefaultKeys() {
		return Arrays.asList(new String[] { "prefix", "join", "quit", "no_perm" });
	}
}
