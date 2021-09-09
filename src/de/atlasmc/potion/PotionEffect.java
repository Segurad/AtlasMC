package de.atlasmc.potion;

import java.io.IOException;

import de.atlasmc.util.nbt.NBTHolder;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;

public class PotionEffect implements Cloneable, NBTHolder {
	
	private PotionEffectType type;
	
	public PotionEffect(PotionEffectType type) {
		this.type = type;
	}

	public PotionEffect() {
		// TODO
	}

	public PotionEffect clone() {
		try {
			return (PotionEffect) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return null;
	}

	public PotionEffectType getType() {
		return type;
	}

	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void fromNBT(NBTReader reader) throws IOException {
		// TODO Auto-generated method stub
		
	}

	public static PotionEffect createByPotionID(int id) {
		// TODO Auto-generated method stub
		return null;
	}

}
