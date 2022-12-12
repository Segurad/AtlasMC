package de.atlasmc.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(PacketPlay.IN_UPDATE_JIGSAW_BLOCK)
public class PacketInUpdateJigsawBlock extends AbstractPacket implements PacketPlayIn {
	
	private long position;
	private String name, target, pool, finalState, jointtype;
	
	public long getPosition() {
		return position;
	}
	
	public void setPosition(long position) {
		this.position = position;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getTarget() {
		return target;
	}
	
	public void setTarget(String target) {
		this.target = target;
	}
	
	public String getPool() {
		return pool;
	}
	
	public void setPool(String pool) {
		this.pool = pool;
	}
	
	public String getFinalState() {
		return finalState;
	}
	
	public void setFinalState(String finalState) {
		this.finalState = finalState;
	}
	
	public String getJointtype() {
		return jointtype;
	}
	
	public void setJointtype(String jointtype) {
		this.jointtype = jointtype;
	}
	
	@Override
	public int getDefaultID() {
		return IN_UPDATE_JIGSAW_BLOCK;
	}

}
