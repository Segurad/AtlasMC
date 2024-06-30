package de.atlasmc.io.protocol.play;

import de.atlasmc.block.BlockFace;
import de.atlasmc.inventory.EquipmentSlot;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(PacketPlay.IN_USE_ITEM_ON)
public class PacketInUseItemOn extends AbstractPacket implements PacketPlayIn {
	
	private EquipmentSlot hand;
	private BlockFace face;
	private long pos;
	private float curposx;
	private float curposy;
	private float curposz;
	private boolean insideblock;
	private int sequence;
	
	public EquipmentSlot getHand() {
		return hand;
	}
	
	public void setHand(EquipmentSlot hand) {
		this.hand = hand;
	}
	
	public long getPosition() {
		return pos;
	}
	
	public void setPosition(long pos) {
		this.pos = pos;
	}
	
	public BlockFace getFace() {
		return face;
	}
	
	public void setFace(BlockFace face) {
		this.face = face;
	}
	
	public float getCursurPositionX() {
		return curposx;
	}
	
	public void setCursurPositionX(float posX) {
		this.curposx = posX;
	}
	
	public float getCursurPositionY() {
		return curposy;
	}
	
	public void setCursurPositionY(float posY) {
		this.curposy = posY;
	}
	
	public float getCursurPositionZ() {
		return curposz;
	}
	
	public void setCursurPositionZ(float posZ) {
		this.curposz = posZ;
	}
	
	public boolean isInsideblock() {
		return insideblock;
	}
	
	public void setInsideblock(boolean insideblock) {
		this.insideblock = insideblock;
	}
	
	public int getSequence() {
		return sequence;
	}
	
	public void setSequence(int sequence) {
		this.sequence = sequence;
	}
	
	@Override
	public int getDefaultID() {
		return IN_USE_ITEM_ON;
	}

}
