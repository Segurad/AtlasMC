package de.atlasmc.entity;

public interface Wither extends Monster {
	
	public Entity getCenterHeadTarget();
	
	public Entity getLeftHeadTarget();
	
	public Entity getRightHeadTarget();
	
	public int getInvulnerableTime();
	
	public void setInvulnerable(int time);
	
	public void setCenterHeadTarget(Entity entity);
	
	public void setLeftHeadTarget(Entity entity);
	
	public void setRightHeadTarget(Entity entity);

}
