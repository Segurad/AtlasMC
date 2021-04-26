package de.atlascore.io.protocol.play;

import java.io.IOException;
import java.util.List;

import de.atlascore.io.protocol.CoreProtocolAdapter;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.play.PacketOutDeclareRecipes;
import de.atlasmc.recipe.Recipe;
import io.netty.buffer.ByteBuf;

public class CorePacketOutDeclareRecipes extends AbstractPacket implements PacketOutDeclareRecipes {

	public CorePacketOutDeclareRecipes() {
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
	public List<Recipe> getRecipes() {
		// TODO Auto-generated method stub
		return null;
	}

}
