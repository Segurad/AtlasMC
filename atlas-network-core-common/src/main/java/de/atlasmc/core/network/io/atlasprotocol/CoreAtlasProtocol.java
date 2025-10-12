package de.atlasmc.core.network.io.atlasprotocol;

import de.atlasmc.io.AbstractProtocol;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.PacketListener;
import de.atlasmc.network.io.protocol.AtlasPacketIn;
import de.atlasmc.network.io.protocol.AtlasPacketOut;

public class CoreAtlasProtocol extends AbstractProtocol<AtlasPacketIn, AtlasPacketOut> {
	
	public static final CoreAtlasProtocol INSTANCE = new CoreAtlasProtocol();
	
	@SuppressWarnings("unchecked")
	protected CoreAtlasProtocol() {
		super(new PacketIO[]{}, new PacketIO[]{});
	}

	@Override
	public int getVersion() {
		return 0;
	}

	@Override
	public PacketListener createDefaultPacketListenerServerbound(Object o) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PacketListener createDefaultPacketListenerClientbound(Object o) {
		// TODO Auto-generated method stub
		return null;
	}

}
