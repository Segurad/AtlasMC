package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.protocol.CoreProtocolAdapter;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.play.PacketInSetDisplayedRecipe;
import io.netty.buffer.ByteBuf;

public class CorePacketInSetDisplayedRecipe extends AbstractPacket implements PacketInSetDisplayedRecipe {

	public CorePacketInSetDisplayedRecipe() {
		super(0x1F, CoreProtocolAdapter.VERSION);
	}
	
	private String recipeID;

	@Override
	public void read(ByteBuf in) throws IOException {
		recipeID = readString(in);
	}

	@Override
	public void write(ByteBuf out) throws IOException {
		writeString(recipeID, out);
	}

	@Override
	public String RecipeID() {
		return recipeID;
	}

}
