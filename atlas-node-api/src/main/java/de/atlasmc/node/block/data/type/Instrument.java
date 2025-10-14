package de.atlasmc.node.block.data.type;

import de.atlasmc.util.EnumName;

public enum Instrument implements EnumName {
	
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

	private String name;
	
	private Instrument() {
		this.name = name().toLowerCase().intern();
	}
	
	@Override
	public String getName() {
		return name;
	}

}
