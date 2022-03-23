package de.atlasmc.inventory.meta;

import java.util.List;

import de.atlasmc.FireworkEffect;

public interface FireworkMeta extends ItemMeta {
	
	public void addEffect(FireworkEffect effect);
	
	public void addEffects(FireworkEffect... effects);
	
	public void clearEffects();
	
	public FireworkMeta clone();
	
	public List<FireworkEffect> getEffects();
	
	public int getEffectSize();
	
	public int getPower();
	
	public boolean hasEffects();
	
	public void removeEffect(int index);
	
	public void setPower(int power);

}
