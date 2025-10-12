package de.atlasmc.core.node.io.protocol.play;

import de.atlasmc.core.node.io.protocol.common.CoreAbstractPacketClientInformation;
import de.atlasmc.io.Packet;
import de.atlasmc.node.io.protocol.play.ServerboundClientInformation;

public class CorePacketInClientInformation extends CoreAbstractPacketClientInformation<ServerboundClientInformation> {
	
	@Override
	public ServerboundClientInformation createPacketData() {
		return new ServerboundClientInformation();
	}
	
	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(ServerboundClientInformation.class);
	}

}
