package de.atlasmc;

public enum Instrument {
	
	HARP,
	BASEDRUM,
	SNARE,
	HAT,
	BASS,
	FLUTE,
	BELL,
	GUITAR,
	CHIME,
	XYLOPHONE,
	IRON_XYLOPHONE,
	COW_BELL,
	DIDGERIDOO,
	BIT,
	BANJO,
	PLING;

	public static Instrument getByName(String name) {
		name = name.toUpperCase();
		for (Instrument i : values()) {
			if (i.name().equals(name)) return i;
		}
		return HARP;
	}

}
