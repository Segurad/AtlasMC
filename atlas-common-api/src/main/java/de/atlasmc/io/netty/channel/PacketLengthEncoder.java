package de.atlasmc.io.netty.channel;
import de.atlasmc.io.PacketUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * Adds the packets length to the data as varint
 */
@Sharable
public class PacketLengthEncoder extends MessageToByteEncoder<ByteBuf> {

    private final int lengthAdjustment;
    
    public static final PacketLengthEncoder INSTANCE = new PacketLengthEncoder();

    public PacketLengthEncoder() {
        this(0);
    }

    public PacketLengthEncoder(int lengthAdjustment) {
        this.lengthAdjustment = lengthAdjustment;
    }

	@Override
    protected void encode(ChannelHandlerContext ctx, ByteBuf msg, ByteBuf out) throws Exception {
        int length = msg.readableBytes() + lengthAdjustment;
        if (length < 0)
        	throw new IllegalArgumentException("Packet length is negative: " + length);
        if (length > PacketUtil.MAX_PACKET_LENGTH) 
        	throw new IllegalArgumentException("Packet length is greater than " + PacketUtil.MAX_PACKET_LENGTH + ": " + length);
        PacketUtil.writeVarInt(length, out);
        out.writeBytes(msg);
    }
}