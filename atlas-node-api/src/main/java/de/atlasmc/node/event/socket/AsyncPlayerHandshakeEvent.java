package de.atlasmc.node.event.socket;

import java.util.Objects;

import de.atlasmc.event.Cancellable;
import de.atlasmc.io.connection.ServerSocketConnectionHandler;
import de.atlasmc.node.event.SocketHandlerList;
import de.atlasmc.node.io.protocol.handshake.HandshakeData;
import de.atlasmc.node.io.socket.NodeSocket;

public class AsyncPlayerHandshakeEvent extends SocketEvent implements Cancellable {

	private static final SocketHandlerList HANDLERS = new SocketHandlerList();
	
	private boolean cancelled;
	private final HandshakeData data;
	private final ServerSocketConnectionHandler con;
	
	public AsyncPlayerHandshakeEvent(boolean async, ServerSocketConnectionHandler con, HandshakeData data) {
		super(async, (NodeSocket) con.getSocket());
		this.data = Objects.requireNonNull(data);
		this.con = con;
	}
	
	public ServerSocketConnectionHandler getConnection() {
		return con;
	}
	
	public HandshakeData getData() {
		return data;
	}

	@Override
	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}

	@Override
	public boolean isCancelled() {
		return cancelled;
	}

	@Override
	public SocketHandlerList getHandlers() {
		return HANDLERS;
	}
	
	public static SocketHandlerList getHandlerList() {
		return HANDLERS;
	}

}
