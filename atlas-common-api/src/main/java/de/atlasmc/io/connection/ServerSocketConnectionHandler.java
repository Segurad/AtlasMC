package de.atlasmc.io.connection;

import java.util.Objects;

import de.atlasmc.io.Protocol;
import de.atlasmc.io.socket.ServerSocket;
import de.atlasmc.log.Log;
import io.netty.channel.socket.SocketChannel;

public class ServerSocketConnectionHandler extends SocketConnectionHandler {

	private ServerSocket socket;
	
	public ServerSocketConnectionHandler(ServerSocket socket, SocketChannel channel, Log log) {
		super(channel, log);
		this.socket = Objects.requireNonNull(socket);
	}
	
	public ServerSocketConnectionHandler(ServerSocket socket, SocketChannel channel, Log log, Protocol protocol) {
		super(channel, log, protocol);
		this.socket = Objects.requireNonNull(socket);
	}
	
	public ServerSocket getSocket() {
		return socket;
	}

}
