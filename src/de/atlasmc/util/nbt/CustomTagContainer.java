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
		if (tags == null) 
			tags = new ArrayList<>();
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
		if (systemTags == null) 
			systemTags = new ArrayList<>();
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
		List<NBT> list = new ArrayList<>(tags.size());
		for (NBT nbt : tags) {
			list.add(nbt.clone());
		}
		return list;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((systemTags == null) ? 0 : systemTags.hashCode());
		result = prime * result + ((tags == null) ? 0 : tags.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CustomTagContainer other = (CustomTagContainer) obj;
		if (systemTags == null) {
			if (other.systemTags != null)
				return false;
		} else if (!systemTags.equals(other.systemTags))
			return false;
		if (tags == null) {
			if (other.tags != null)
				return false;
		} else if (!tags.equals(other.tags))
			return false;
		return true;
	}

}
