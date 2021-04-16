package de.atlasmc.io;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import io.netty.channel.socket.SocketChannel;

public class ConnectionHandler {
	
	private Queue<Packet> queue;
	private final SocketChannel channel;
	
	public ConnectionHandler(SocketChannel channel) {
		queue = new ConcurrentLinkedQueue<Packet>();
		this.channel = channel;
	}
	
	public void sendPacket(Packet packet) {
		if (channel.isActive()) {
			channel.writeAndFlush(packet);
		} else {
			queue.add(packet);
		}
	}

	public void writeQueued() {
		while (!queue.isEmpty()) {
			channel.writeAndFlush(queue.poll());
		}
	}

	public boolean hasQueued() {
		return !queue.isEmpty();
	}

}
