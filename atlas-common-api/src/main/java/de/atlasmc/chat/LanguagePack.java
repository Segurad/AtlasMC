package de.atlasmc.chat;

import java.util.Set;

public interface LanguagePack {

	public String getLang();
	public String get(String key);
	public boolean hasKey(String key);
	public void add(String key, String msg);
	public void remove(String key);
	public Set<String> getKeys();
	
}
