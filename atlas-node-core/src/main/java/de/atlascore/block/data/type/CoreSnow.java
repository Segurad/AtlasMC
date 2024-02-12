package de.atlascore.block.data.type;

import java.io.IOException;

import de.atlascore.block.data.CoreBlockData;
import de.atlasmc.Material;
import de.atlasmc.block.data.type.Snow;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.io.NBTWriter;

public class CoreSnow extends CoreBlockData implements Snow {

	protected static final CharKey
	LAYERS = CharKey.literal("layers");
	
	static {
		NBT_FIELDS.setField(LAYERS, (holder, reader) -> {
			((Snow) holder).setLayers(reader.readIntTag());
		});
	}
	
	private int layers;
	
	public CoreSnow(Material material) {
		super(material);
		layers = 1;
	}

	@Override
	public int getLayers() {
		return layers;
	}

	@Override
	public int getMaxLayers() {
		return 8;
	}

	@Override
	public int getMinLayers() {
		return 1;
	}

	@Override
	public void setLayers(int layers) {
		if (layers < 1 || layers > 8) throw new IllegalArgumentException("NamespaceID is not between 1 and 8: " + layers);
		this.layers = layers;
	}

	@Override
	public int getStateID() {
		return super.getStateID()+(layers-1);
	}
	
	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		super.toNBT(writer, systemData);
		if (getLayers() > 1) writer.writeIntTag(LAYERS, getLayers());
	}
	
}
