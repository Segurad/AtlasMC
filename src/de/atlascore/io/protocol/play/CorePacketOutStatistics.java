package de.atlascore.io.protocol.play;

import java.io.IOException;
import java.util.Arrays;

import de.atlascore.io.protocol.CoreProtocolAdapter;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.play.PacketOutStatistics;
import io.netty.buffer.ByteBuf;

public class CorePacketOutStatistics extends AbstractPacket implements PacketOutStatistics {

	private int[] statistics;
	private int elements;
	
	public CorePacketOutStatistics() {
		super(CoreProtocolAdapter.VERSION);
	}
	
	public CorePacketOutStatistics(int[] statistics) {
		this();
		int elements = statistics.length%3;
		if (elements != 0) throw new IllegalArgumentException("Invalid Statistic size (must be multiple of 3): " + statistics.length);
		this.statistics = Arrays.copyOf(statistics, statistics.length);
		this.elements = elements;
	}

	@Override
	public void read(ByteBuf in) throws IOException {
		elements = readVarInt(in);
		final int length = elements*3;
		statistics = new int[length];
		for (int i = 0; i < length; i++) {
			statistics[i] = readVarInt(in);
		}
	}

	@Override
	public void write(ByteBuf out) throws IOException {
		writeVarInt(elements, out);
		for (int i : statistics) {
			writeVarInt(i, out);
		}
	}

	@Override
	public int[] getStatistics() {
		if (statistics == null) return new int[0];
		return Arrays.copyOf(statistics, statistics.length);
	}

}
