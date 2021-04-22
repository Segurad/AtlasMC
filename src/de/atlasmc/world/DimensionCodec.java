package de.atlasmc.world;

import java.io.IOException;
import java.util.List;

import de.atlasmc.util.nbt.NBTHolder;
import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;

public class DimensionCodec implements NBTHolder {
	
	private List<Dimension> dimensions;
	private List<Biome> biomes;
	
	protected static final String
	DIMANSION_TYPE = "minecraft:dimension_type",
	WORLDGEN_BIOME = "minecraft:worldgen/biome",
	TYPE = "type",
	VALUE = "value";
	
	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		writer.writeCompoundTag(DIMANSION_TYPE);
		{
			writer.writeStringTag(TYPE, DIMANSION_TYPE);
			writer.writeListTag(VALUE, TagType.COMPOUND, dimensions.size());
			for (Dimension d : dimensions) {
				d.toNBT(writer, systemData);
				writer.writeEndTag();
			}
		}
		writer.writeEndTag();
		writer.writeCompoundTag(WORLDGEN_BIOME);
		{
			writer.writeStringTag(TYPE, WORLDGEN_BIOME);
			writer.writeListTag(VALUE, TagType.COMPOUND, biomes.size());
			for (Biome b : biomes) {
				b.toNBT(writer, systemData);
				writer.writeEndTag();
			}
		}
		writer.writeEndTag();
	}
	
	@Override
	public void fromNBT(NBTReader reader) throws IOException {
		// TODO Auto-generated method stub
		
	}

}
