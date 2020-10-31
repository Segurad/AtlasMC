package de.atlasmc.io;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ConnectionHandler {

	private final Socket socket;
	private DataOutputStream out;
	private DataInputStream in;
	private final ProtocolAdapter prot;
	
	public ConnectionHandler(Socket socket, ProtocolAdapter protocol) {
		this.socket = socket;
		this.prot = protocol;
		try {
			out = new DataOutputStream(socket.getOutputStream());
			in = new DataInputStream(socket.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param pack
	 * @throws IllegalArgumentException if Protocol-Version and Packet-Version are not equal
	 */
	public void send(Packet pack) {
		if (pack.getVersion() != prot.getVersion() && pack.getVersion() != -1) 
			throw new IllegalArgumentException("Protocol-Version > Client: " + prot.getVersion() + " Packet: " + pack.getVersion());
		try {
			pack.write(out);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public boolean canRead() {
		try {
			return in.available() > 1;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	public ProtocolAdapter getProtocol() {
		return prot;
	}

	public Packet read() {
		try {
			int length = AbstractPacket.readVarInt(in);
			int id = in.readByte();
			Packet pack = prot.createPlayInPacket(id);
			pack.read(length, in);
			return pack;
		} catch(IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void close() {
		try {
			out.close();
			in.close();
			socket.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

}
