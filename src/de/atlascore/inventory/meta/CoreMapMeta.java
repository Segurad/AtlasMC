package de.atlascore.inventory.meta;

import java.io.IOException;

import de.atlasmc.Color;
import de.atlasmc.Material;
import de.atlasmc.inventory.meta.ItemMeta;
import de.atlasmc.inventory.meta.MapMeta;
import de.atlasmc.map.MapView;
import de.atlasmc.util.nbt.NBTField;
import de.atlasmc.util.nbt.io.NBTWriter;

public class CoreMapMeta extends CoreItemMeta implements MapMeta {

	private Color color;
	private MapView view;
	private int mapID;
	
	protected static final String
			MAP = "map",
			MAP_SCALE_DIRECTION = "map_scale_direction",
			MAP_COLOR = "MapColor",
			MAP_TRACING_POSITION = "map_tracking_position",
			DECORATION = "Decoration";
	
	static {
		NBT_FIELDS.setField(MAP, (holder, reader) -> {
			if (CoreMapMeta.class.isInstance(holder)) {
				((CoreMapMeta) holder).mapID = reader.readIntTag();
			} else ((ItemMeta) holder).getCustomTagContainer().addCustomTag(reader.readNBT());
		});
		NBT_FIELDS.setField(MAP_SCALE_DIRECTION, NBTField.TRASH);
		NBT_FIELDS.setField(MAP_TRACING_POSITION, NBTField.TRASH);
		NBT_FIELDS.getContainer(DISPLAY).setField(MAP_COLOR, (holder, reader) -> {
			if (MapMeta.class.isInstance(holder)) {
				((MapMeta) holder).setColor(new Color(reader.readIntTag()));
			} else ((ItemMeta) holder).getCustomTagContainer().addCustomTag(reader.readNBT());
		});
		NBT_FIELDS.setField(DECORATION, NBTField.TRASH);
	}
	
	public CoreMapMeta(Material material) {
		super(material);
		mapID = -1;
	}

	@Override
	public CoreMapMeta clone() {
		return (CoreMapMeta) super.clone();
	}

	@Override
	public Color getColor() {
		return color;
	}

	@Override
	public int getMapId() {
		return mapID;
	}

	@Override
	public MapView getMapView() {
		return view;
	}

	@Override
	public boolean hasColor() {
		return color != null;
	}

	@Override
	public boolean hasMapView() {
		return view != null;
	}

	@Override
	public void setColor(Color color) {
		this.color = color;
	}

	@Override
	public void setMapView(MapView view) {
		this.view = view;
		mapID = view == null ? -1 : view.getMapID();
	}
	
	@Override
	public void toNBT(NBTWriter writer, String local, boolean systemData) throws IOException {
		super.toNBT(writer, local, systemData);
	}
	
	@Override
	public boolean hasDisplayCompound() {
		return hasColor() || super.hasDisplayCompound();
	}
	
	@Override
	public void writeDisplayCompound(NBTWriter writer, String local, boolean systemData) throws IOException {
		super.writeDisplayCompound(writer, local, systemData);
		if (hasColor()) writer.writeIntTag(MAP_COLOR, getColor().asRGB());
	}

}
