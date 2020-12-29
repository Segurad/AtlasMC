package de.atlasmc.schematic;

public class SingleTypeSection {
	
	private short data;
	
	public Section toSection() {
		return new Section(data);
	}

}
