package de.atlasmc.util.nbt;

import java.util.ArrayList;
import java.util.List;

import de.atlasmc.util.nbt.tag.NBT;

/**
 * Container that stores NBT in two categories.<br>
 * <li><b>CustomTags</b> which are send to the user
 * <li><b>SystemTags</b> which are only server side and will not be send to the user
 */
public class CustomTagContainer implements Cloneable {
	
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
		if (nbt == null) 
			throw new IllegalArgumentException("NBT can not be null!");
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
		if (nbt == null) 
			throw new IllegalArgumentException("NBT can not be null!");
		getCustomTags().add(nbt);
	}
	
	@Override
	public CustomTagContainer clone() {
		CustomTagContainer clone = null;
		try {
			clone = (CustomTagContainer) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		if (clone == null)
			return null;
		if (hasCustomTags())
			tags = cloneList(tags);
		if (hasSystemTags())
			systemTags = cloneList(systemTags);
		return clone;
	}

	private List<NBT> cloneList(List<NBT> tags) {
		List<NBT> list = new ArrayList<NBT>(tags.size());
		for (NBT nbt : tags) {
			list.add(nbt.clone());
		}
		return list;
	}

}
