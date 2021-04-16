package de.atlasmc.io.channel;

import java.nio.ByteOrder;

import de.atlasmc.io.AbstractPacket;
import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/**
 * A simple extension of {@link LengthFieldBasedFrameDecoder} that support varints
 * just use -1 as lengthFieldLength
 */
public class VarLengthFieldFrameDecoder extends LengthFieldBasedFrameDecoder {

	private final int lengthFieldLength;
	
	public VarLengthFieldFrameDecoder(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength, int lengthAdjustment, int initialBytesToStrip, boolean failFast) {
		super(maxFrameLength, lengthFieldOffset, 1, lengthAdjustment, lengthFieldLength == -1 ? 0 : initialBytesToStrip, failFast);
		this.lengthFieldLength = lengthFieldLength;
	}
	
	public VarLengthFieldFrameDecoder(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength, int lengthAdjustment, int initialBytesToStrip) {
		super(maxFrameLength, lengthFieldOffset, 1, lengthAdjustment, lengthFieldLength == -1 ? 0 : initialBytesToStrip);
		this.lengthFieldLength = lengthFieldLength;
	}

	public VarLengthFieldFrameDecoder(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength) {
		super(maxFrameLength, lengthFieldOffset, 1);
		this.lengthFieldLength = lengthFieldLength;
	}
	
	public VarLengthFieldFrameDecoder(ByteOrder byteOrder, int maxFrameLength, int lengthFieldOffset, int lengthFieldLength, int lengthAdjustment, int initialBytesToStrip, boolean failFast) {
		super(byteOrder, maxFrameLength, lengthFieldOffset, 1, lengthAdjustment, lengthFieldLength == -1 ? 0 : initialBytesToStrip, failFast);
		this.lengthFieldLength = lengthFieldLength;
	}

	@Override
	protected long getUnadjustedFrameLength(ByteBuf buf, int offset, int length, ByteOrder order) {
		length = lengthFieldLength;
		if (length == -1) {
			return AbstractPacket.readVarInt(buf);
		} else
		return super.getUnadjustedFrameLength(buf, offset, length, order);
	}
}
