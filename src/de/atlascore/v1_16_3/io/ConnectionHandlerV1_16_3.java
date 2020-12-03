package de.atlascore.v1_16_3.io;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import de.atlasmc.entity.Player;
import de.atlasmc.inventory.Inventory;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PlayerConnectionState;
import de.atlasmc.io.ProtocolAdapter;
import de.atlasmc.util.ByteDataBuffer;

public class ConnectionHandlerV1_16_3 implements ConnectionHandler {
	
	private final Socket socket;
	private final ProtocolAdapter prot;
	
	public ConnectionHandlerV1_16_3(Socket socket, ProtocolAdapter protocol) {
		this.socket = socket;
		this.prot = protocol;
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
			ByteDataBuffer buffer = new ByteDataBuffer();
			pack.write(buffer);
			byte[] data = buffer.toByteArray();
			DataOutputStream out = new DataOutputStream(socket.getOutputStream());
			AbstractPacket.writeVarInt(data.length, out);
			out.write(data);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public boolean canRead() {
		try {
			return socket.getInputStream().available() > 1;
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
			DataInputStream input = new DataInputStream(socket.getInputStream());
			int length = AbstractPacket.readVarInt(input);
			byte[] data = new byte[length];
			input.read(data);
			ByteDataBuffer buffer = new ByteDataBuffer(data);
			int id = AbstractPacket.readVarInt(buffer);
			Packet pack = prot.createPlayInPacket(id);
			pack.read(length, buffer);
			return pack;
		} catch(IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void close() {
		try {
			socket.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void updateWindowSlots(Inventory abstractCoreInventory, int... slot) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Player getPlayer() {
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
