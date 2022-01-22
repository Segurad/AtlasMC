package de.atlasmc.entity;

import de.atlasmc.SimpleLocation;

public interface EndCrystal extends Entity {

	public SimpleLocation getBeamTarget();
	
	public SimpleLocation getBeamTarget(SimpleLocation loc);
	
	public void setBeamTarget(SimpleLocation loc);
	
	public void setBeamTarget(int x, int y, int z);
	
	public boolean hasTarget();
	
	public void resetTarget();
	
	public boolean getShowBottom();
	
	public void setShowBottom(boolean show);
	
}
