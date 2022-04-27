package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.protocol.CoreProtocolAdapter;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.play.PacketOutSetPassengers;
import io.netty.buffer.ByteBuf;

public class CorePacketOutSetPassengers extends AbstractPacket implements PacketOutSetPassengers {

	private int vehicleID;
	private int[] passengerIDs;
	
	public CorePacketOutSetPassengers() {
		super(CoreProtocolAdapter.VERSION);
	}
	
	public CorePacketOutSetPassengers(int vehicleID, int... passengerIDs) {
		this();
		this.vehicleID = vehicleID;
		this.passengerIDs = passengerIDs;
	}

	@Override
	public void read(ByteBuf in) throws IOException {
		vehicleID = readVarInt(in);
		final int size = readVarInt(in);
		passengerIDs = new int[size];
		for (int i = 0; i < size; i++) {
			passengerIDs[i] = readVarInt(in);
		}
	}

	@Override
	public void write(ByteBuf out) throws IOException {
		writeVarInt(vehicleID, out);
		writeVarInt(passengerIDs.length, out);
		for (int i : passengerIDs) {
			writeVarInt(i, out);
		}
	}

	@Override
	public int getVehicleID() {
		return vehicleID;
	}

	@Override
	public int[] getPassengers() {
		return passengerIDs;
	}

	@Override
	public void setVehicleID(int id) {
		this.vehicleID = id;
	}

	@Override
	public void setPassengers(int[] passengers) {
		this.passengerIDs = passengers;
	}

}
