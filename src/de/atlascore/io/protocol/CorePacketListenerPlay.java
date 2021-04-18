package de.atlascore.io.protocol;

import de.atlasmc.entity.Player;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketListener;

public class CorePacketListenerPlay implements PacketListener {

	private final Player player;
	
	public CorePacketListenerPlay(Player player) {
		this.player = player;
	}

	@Override
	public void handlePacket(Packet packet) {
		// TODO
	}

	@Override
	public void handleUnregister() {}

}
