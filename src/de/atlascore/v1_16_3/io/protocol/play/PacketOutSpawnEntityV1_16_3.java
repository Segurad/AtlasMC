package de.atlascore.v1_16_3.io.protocol.play;

import de.atlasmc.entity.Entity;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.play.PacketOutSpawnEntity;

public class PacketOutSpawnEntityV1_16_3 extends AbstractPacket implements PacketOutSpawnEntity {

	public PacketOutSpawnEntityV1_16_3(int id, int version) {
		super(id, version);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Entity getEntity() {
		// TODO Auto-generated method stub
		return null;
	}

}
