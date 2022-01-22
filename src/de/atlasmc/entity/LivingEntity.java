package de.atlasmc.entity;

import de.atlasmc.Color;
import de.atlasmc.potion.PotionEffect;

public interface LivingEntity extends Damageable {

	public float getHeadPitch();
	
	public int getDisplayedArrows();
	
	public int getDisplayedBeeStingers();
	
	public void setDisplayedArrows(int arrows);
	
	public void setDisplayedBeeStringers(int stingers);
	
	/**
	 * Returns the Color value of particles spawned through {@link PotionEffect}s<br>
	 * if non particle effect is active the {@link Color} of 0x000000 will be returned
	 * @return color
	 */
	public Color getPotionAmbientColor();
	
	/**
	 * Sets the Color of ambient particles caused by a {@link PotionEffect}<br>
	 * 0x000000 and null will display no particles
	 * @param color
	 */
	public void setPotionAmbientColor(Color color);
	
	/**
	 * Returns the RGB value of particles spawned through {@link PotionEffect}s
	 * @return rgb or 0 if no particle
	 */
	public int getPotionAmbientColorRGB();
	
	/**
	 * Sets the Color of ambient particles caused by a {@link PotionEffect}<br>
	 * 0x000000 will display no particles
	 * @param rgb
	 */
	public void setPotionAmbientColor(int rgb);

	/**
	 * Whether or not the ambient particles are reduced to 1/5
	 * @return true if reduced
	 */
	public boolean isPotionAmbientReduced();
	
	/**
	 * Sets whether or not the ambient particles should be reduced to 1/5
	 * @param reduced
	 */
	public void setPotionAmbientReduced(boolean reduced);
	
}
