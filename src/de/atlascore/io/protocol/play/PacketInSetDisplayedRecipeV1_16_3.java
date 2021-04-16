package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.V1_16_3;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.play.PacketInSetDisplayedRecipe;
import io.netty.buffer.ByteBuf;

public class PacketInSetDisplayedRecipeV1_16_3 extends AbstractPacket implements PacketInSetDisplayedRecipe {

	public PacketInSetDisplayedRecipeV1_16_3() {
		super(0x1E, V1_16_3.version);
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
