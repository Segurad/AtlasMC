package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.V1_16_3;
import de.atlasmc.SimpleLocation;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.play.PacketInUpdateJigsawBlock;
import io.netty.buffer.ByteBuf;

public class PacketInUpdateJigsawBlockV1_16_3 extends AbstractPacket implements PacketInUpdateJigsawBlock {

	public PacketInUpdateJigsawBlockV1_16_3() {
		super(0x28, V1_16_3.version);
	}
	
	private SimpleLocation pos;
	private String name,target,pool,finalstate,jointtype;

	@Override
	public void read(ByteBuf in) throws IOException {
		pos = readPosition(in);
		name = readString(in);
		target = readString(in);
		pool = readString(in);
		finalstate = readString(in);
		jointtype = readString(in);
	}

	@Override
	public void write(ByteBuf out) throws IOException {
		writePosition(pos, out);
		writeString(name, out);
		writeString(target, out);
		writeString(pool, out);
		writeString(finalstate, out);
		writeString(jointtype, out);
	}

	@Override
	public SimpleLocation Position() {
		return pos;
	}

	@Override
	public String Name() {
		return name;
	}

	@Override
	public String Target() {
		return target;
	}

	@Override
	public String Pool() {
		return pool;
	}

	@Override
	public String FinalState() {
		return finalstate;
	}

	@Override
	public String Jointtype() {
		return jointtype;
	}

}
