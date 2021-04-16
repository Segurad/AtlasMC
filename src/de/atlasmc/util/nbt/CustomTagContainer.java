package de.atlasmc.util.nbt;

import java.util.ArrayList;
import java.util.List;

import de.atlasmc.util.Validate;

public class CustomTagContainer {
	
	private List<NBT> tags;
	private List<NBT> systemTags;
	
	public boolean hasCustomTags() {
		return tags != null;
	}
	
	public List<NBT> getCustomTags() {
		if (tags == null) tags = new ArrayList<NBT>();
		return tags;
	}
	
	public void addCustomTag(NBT nbt) {
		Validate.notNull(nbt, "NBT can not be null!");
		getCustomTags().add(nbt);
	}
	
	public boolean hasSystemTags() {
		return systemTags != null;
	}
	
	public List<NBT> getSystemTags() {
		if (systemTags == null) systemTags = new ArrayList<NBT>();
		return systemTags;
	}
	
	public void addSystemTag(NBT nbt) {
		Validate.notNull(nbt, "NBT can not be null!");
		getCustomTags().add(nbt);
	}

}
