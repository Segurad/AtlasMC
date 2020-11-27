package de.atlascore.v1_16_3.io.pack;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import de.atlascore.v1_16_3.io.V1_16_3;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.pack.PacketInSetDisplayedRecipe;

public class PacketInSetDisplayedRecipeV1_16_3 extends AbstractPacket implements PacketInSetDisplayedRecipe {

	public PacketInSetDisplayedRecipeV1_16_3() {
		super(0x1E, V1_16_3.version);
	}
	
	private String recipeID;

	@Override
	public void read(int length, DataInput input) throws IOException {
		recipeID = readString(input);
	}

	@Override
	public void write(DataOutput output) throws IOException {}

	@Override
	public String RecipeID() {
		return recipeID;
	}

}
