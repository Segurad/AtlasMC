package de.atlasmc.io.protocol.play;

import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.io.PacketOutbound;
import de.atlasmc.potion.PotionEffect;

@DefaultPacketID(PacketPlay.OUT_ENTITY_EFFECT)
public interface PacketOutEntityEffect extends PacketPlay, PacketOutbound {
	
	public int getEntityID();
	
	public int getEffectID();
	
	public int getAmplifier();
	
	public int getDuration();
	
	public boolean isAmbient();
	
	public boolean getShowParticles();
	
	public boolean getShowIcon();
	
	public void setEntityID(int id);
	
	public void setEffect(PotionEffect effect);
	
	@Override
	public default int getDefaultID() {
		return OUT_ENTITY_EFFECT;
	}

}
