package de.atlasmc.entity;

public interface Explosive {

	public float getExplosionRadius();
	
	public void setExplosionRadius(float radius);
	
	public boolean isIncendiary();

	public void setIncendiary(boolean incendiary);
	
	public void setExplosionPower(int power);
	
	public int getExplosionPower();
	
}
