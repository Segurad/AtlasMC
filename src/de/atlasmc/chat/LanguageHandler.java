package de.atlasmc.chat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class LanguageHandler {

	public static final String 
		de_DE = "de_DE",
		en_US = "en_US";
	
	private final List<LanguagePack> packs = new ArrayList<LanguagePack>();
	private final HashMap<UUID, LanguagePack> lang = new HashMap<UUID, LanguagePack>();
	private LanguagePack defaultPack;

	public LanguageHandler(LanguagePack defaultPack) {
		this.defaultPack = defaultPack;
	}

	public String getLang(UUID player) {
		if (lang.containsKey(player)) {
			return lang.get(player).getLang();
		} else
			return defaultPack.getLang();
	}

	public LanguagePack getLangPack(UUID player) {
		if (lang.containsKey(player)) {
			return lang.get(player);
		} else
			return defaultPack;
	}

	public void setLangPack(UUID player, LanguagePack pack) {
		lang.put(player, pack);
	}

	public void removePlayer(UUID player) {
		lang.remove(player);
	}

	public LanguagePack getPack(String lang) {
		if (lang.equals("default")) return defaultPack;
		for (LanguagePack pack : packs) {
			if (pack.getLang().equalsIgnoreCase(lang))
				return pack;
		}
		return defaultPack;
	}

	public void addLangPack(LanguagePack pack) {
		if (!packs.contains(pack))
			packs.add(pack);
	}

	public void removeLangPack(LanguagePack pack) {
		packs.remove(pack);
	}

	public List<LanguagePack> getPacks() {
		List<LanguagePack> packs = new ArrayList<LanguagePack>();
		packs.addAll(this.packs);
		return packs;
	}

	public LanguagePack getDefaultPack() {
		return defaultPack;
	}

	public void setDefaultPack(LanguagePack pack) {
		defaultPack = pack;
	}
}
