package de.atlascore.io;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import de.atlasmc.SimpleLocation;

public class AbstractPacket {

	private final int id, version;
	private boolean cancelled;
	public static int BOOLEAN = 1,
			BYTE = 1,
			SHORT = 2,
			INT = 4,
			LONG = 8,
			FLOAT = 4,
			DOUBLE = 8,
			UUID = 16,
			POSITION = 8,
			ANGLE = 1,
			ID = 1;
	
	public AbstractPacket(int id, int version) {
		this.id = id;
		this.version = version;
		this.cancelled = false;
	}

	public boolean isCancelled() {
		return cancelled;
	}

	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}

	public int getVersion() {
		return version;
	}

	public int getID() {
		return id;
	}
	
	public static int readVarInt(DataInputStream input) throws IOException {
	    int numRead = 0;
	    int result = 0;
	    byte read;
	    do {
	        read = input.readByte();
	        int value = (read & 0b01111111);
	        result |= (value << (7 * numRead));

	        numRead++;
	        if (numRead > 5) {
	            throw new RuntimeException("VarInt is too big");
	        }
	    } while ((read & 0b10000000) != 0);

	    return result;
	}
	
	public static long readVarLong(DataInputStream input) throws IOException {
	    int numRead = 0;
	    long result = 0;
	    byte read;
	    do {
	        read = input.readByte();
	        int value = (read & 0b01111111);
	        result |= (value << (7 * numRead));

	        numRead++;
	        if (numRead > 10) {
	            throw new RuntimeException("VarLong is too big");
	        }
	    } while ((read & 0b10000000) != 0);

	    return result;
	}
	
	public static void writeVarInt(int value, DataOutputStream output) throws IOException {
	    do {
	        int temp = value & 0b01111111;
	        // Note: >>> means that the sign bit is shifted with the rest of the number rather than being left alone
	        value >>>= 7;
	        if (value != 0) {
	            temp |= 0b10000000;
	        }
	        output.writeByte(temp);
	    } while (value != 0);
	}
	
	public static void writeVarLong(long value, DataOutputStream output) throws IOException {
	    do {
	        int temp = (int) (value & 0b01111111);
	        // Note: >>> means that the sign bit is shifted with the rest of the number rather than being left alone
	        value >>>= 7;
	        if (value != 0) {
	            temp |= 0b10000000;
	        }
	        output.writeByte(temp);
	    } while (value != 0);
	}
	
	public static SimpleLocation readPosition(DataInputStream input) throws IOException {
		long in = input.readLong();
		int x = (int) (in >> 38);
		int y = (int) ((in >> 26) & 0xFFF);
		int z = (int) (in << 38 >> 38);
		return new SimpleLocation(x, y, z);
	}
	
	public static void writePosition(SimpleLocation loc, DataOutputStream output) throws IOException {
		long out = ((loc.getBlockX() & 0x3FFFFFF) << 38) | ((loc.getBlockZ() & 0x3FFFFFF) << 12) | (loc.getBlockY() & 0xFFF);
		output.writeLong(out);
	}
	
	public static String readString(DataInputStream input) throws IOException {
		int len = readVarInt(input);
		byte[] buffer = new byte[len];
		input.read(buffer);
		return new String(buffer);
	}
	
	public static void writeString(String value, DataOutputStream output) throws IOException {
		byte[] buffer = value.getBytes();
		writeVarInt(buffer.length, output);
		output.write(buffer);
	}

}
