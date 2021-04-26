package de.atlascore.io.protocol.play;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import de.atlascore.io.protocol.CoreProtocolAdapter;
import de.atlasmc.advancement.Advancement;
import de.atlasmc.advancement.AdvancementProgress;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.play.PacketOutAdvancements;
import io.netty.buffer.ByteBuf;

public class CorePacketOutAdvancements extends AbstractPacket implements PacketOutAdvancements {

	public CorePacketOutAdvancements() {
		super(CoreProtocolAdapter.VERSION);
	}

	@Override
	public void read(ByteBuf in) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void write(ByteBuf out) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isReset() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Map<String, Advancement> getAdvancements() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getRemoved() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, AdvancementProgress> getProgress() {
		// TODO Auto-generated method stub
		return null;
	}

}
