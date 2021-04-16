package de.atlascore.io;

import de.atlasmc.atlasnetwork.proxy.Proxy;
import de.atlasmc.atlasnetwork.server.AtlasPlayer;
import de.atlasmc.io.Packet;
import de.atlasmc.io.protocol.PlayerConnection;
import de.atlasmc.io.protocol.PlayerConnectionState;
import de.atlasmc.io.protocol.ProtocolAdapter;
import de.atlasmc.util.ByteDataBuffer;
import io.netty.channel.socket.SocketChannel;

public class CorePlayerConnection implements PlayerConnection {

	private final SocketChannel socket;
	
	public CorePlayerConnection(SocketChannel socket) {
		this.socket = socket;
	}

	@Override
	public void send(Packet pack) {
		ByteDataBuffer buffer = new ByteDataBuffer();
		pack.write(buffer);
		
	}

	@Override
	public ProtocolAdapter getProtocol() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Proxy getMainProxy() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Proxy getProxy() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AtlasPlayer getPlayer() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PlayerConnectionState getState() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setState(PlayerConnectionState state) {
		// TODO Auto-generated method stub
		
	}

}
