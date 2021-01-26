package de.atlasmc.entity;

public interface Wither extends Monster {
	
	public Entity getCemterHeadsTarget();
	public Entity getLeftHeadsTarget();
	public Entity getRightHeadsTarget();
	public int getInvulnerableTime();

}
