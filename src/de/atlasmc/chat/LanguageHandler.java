package de.atlasmc.chat;

import java.util.ArrayList;
import java.util.List;

public class LanguageHandler {

	public static final String 
		de_DE = "de_DE",
		en_US = "en_US";
	
	private final List<LanguagePack> packs = new ArrayList<>();
	private LanguagePack defaultPack;

	public LanguageHandler(LanguagePack defaultPack) {
		this.defaultPack = defaultPack;
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
		List<LanguagePack> packs = new ArrayList<>();
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
