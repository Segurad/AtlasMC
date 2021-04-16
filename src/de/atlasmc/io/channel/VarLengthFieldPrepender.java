package de.atlasmc.io.channel;
import static io.netty.util.internal.ObjectUtil.checkPositiveOrZero;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.netty.util.internal.ObjectUtil;

import java.nio.ByteOrder;
import java.util.List;

import de.atlasmc.io.AbstractPacket;

@Sharable
public class VarLengthFieldPrepender extends MessageToMessageEncoder<ByteBuf> {

    private final ByteOrder byteOrder;
    private final int lengthAdjustment;

    public VarLengthFieldPrepender() {
        this(ByteOrder.BIG_ENDIAN, 0);
    }
    
    public VarLengthFieldPrepender(int lengthAdjustment) {
        this(ByteOrder.BIG_ENDIAN, lengthAdjustment);
    }

    public VarLengthFieldPrepender(ByteOrder byteOrder, int lengthAdjustment) {
        this.byteOrder = ObjectUtil.checkNotNull(byteOrder, "byteOrder");
        this.lengthAdjustment = lengthAdjustment;
    }

    @SuppressWarnings("deprecation")
	@Override
    protected void encode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
        int length = msg.readableBytes() + lengthAdjustment;
        checkPositiveOrZero(length, "length");
        if (length > 2097151) throw new IllegalArgumentException("Packet length is greater than 2097151: " + length);
        ByteBuf buf = ctx.alloc().buffer(AbstractPacket.getVarIntLength(length));
        buf.order(byteOrder);
        AbstractPacket.writeVarInt(length, buf);
        out.add(buf);
        out.add(msg.retain());
    }
}