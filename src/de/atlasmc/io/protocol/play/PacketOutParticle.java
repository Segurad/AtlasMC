package de.atlasmc.io.protocol.play;

import de.atlasmc.Particle;
import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.io.PacketOutbound;

@DefaultPacketID(PacketPlay.OUT_PARTICLE)
public interface PacketOutParticle extends PacketPlay, PacketOutbound {
	
	public Particle getParticle();
	
	public boolean isLongDistance();
	
	public double getX();
	
	public double getY();
	
	public double getZ();
	
	public float getOffsetX();
	
	public float getOffsetY();
	
	public float getOffsetZ();
	
	public float getParticleData();
	
	public int getParticleCount();
	
	public Object getData();
	
	public void setParticle(Particle particle);
	
	public void setLongDistance(boolean longdistance);
	
	public void setX(double x);
	
	public void setY(double y);
	
	public void setZ(double z);
	
	public void setPoition(double x, double y, double z);
	
	public void setOffsetX(float x);
	
	public void setOffsetY(float y);
	
	public void setOffsetZ(float z);
	
	public void setOffset(float x, float y, float z);
	
	public void setParticleData(float data);
	
	public void setParticleCount(int count);
	
	public void setData(Object data);
	
	@Override
	public default int getDefaultID() {
		return OUT_PARTICLE;
	}

}
