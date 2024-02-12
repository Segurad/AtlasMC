package de.atlascore.io;

import de.atlasmc.log.Log;
import io.netty.channel.socket.SocketChannel;

public class SimpleConnectionHandler extends CoreConnectionHandler {

	private final Log logger;
	
	public SimpleConnectionHandler(SocketChannel channel, Log logger) {
		super(channel);
		this.logger = logger;
	}

	@Override
	public Log getLogger() {
		return logger;
	}

}
