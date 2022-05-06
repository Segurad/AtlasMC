package de.atlascore.inventory.meta;

import java.io.IOException;

import de.atlasmc.Color;
import de.atlasmc.Material;
import de.atlasmc.inventory.meta.ItemMeta;
import de.atlasmc.inventory.meta.MapMeta;
import de.atlasmc.map.MapView;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.NBTField;
import de.atlasmc.util.nbt.io.NBTWriter;

public class CoreMapMeta extends CoreItemMeta implements MapMeta {

	private Color color;
	private MapView view;
	private int mapID;
	
	protected static final CharKey
			MAP = CharKey.of("map"),
			MAP_SCALE_DIRECTION = CharKey.of("map_scale_direction"),
			MAP_COLOR = CharKey.of("MapColor"),
			MAP_TRACING_POSITION = CharKey.of("map_tracking_position"),
			DECORATION = CharKey.of("Decoration");
	
	static {
		NBT_FIELDS.setField(MAP, (holder, reader) -> {
			if (holder instanceof MapMeta) {
				((MapMeta) holder).setMapID(reader.readIntTag());
			} else ((ItemMeta) holder).getCustomTagContainer().addCustomTag(reader.readNBT());
		});
		NBT_FIELDS.setField(MAP_SCALE_DIRECTION, NBTField.SKIP); // TODO skipped NBT
		NBT_FIELDS.setField(MAP_TRACING_POSITION, NBTField.SKIP); // TODO skipped NBT
		NBT_FIELDS.getContainer(NBT_DISPLAY).setField(MAP_COLOR, (holder, reader) -> {
			if (holder instanceof MapMeta) {
				((MapMeta) holder).setColor(new Color(reader.readIntTag()));
			} else ((ItemMeta) holder).getCustomTagContainer().addCustomTag(reader.readNBT());
		});
		NBT_FIELDS.setField(DECORATION, NBTField.SKIP); // TODO skipped NBT
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
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		super.toNBT(writer, systemData);
		writer.writeIntTag(MAP, mapID);
	}
	
	@Override
	protected boolean hasDisplayCompound() {
		return hasColor() || super.hasDisplayCompound();
	}
	
	@Override
	protected void writeDisplayCompound(NBTWriter writer, boolean systemData) throws IOException {
		super.writeDisplayCompound(writer, systemData);
		if (hasColor()) writer.writeIntTag(MAP_COLOR, getColor().asRGB());
	}

	@Override
	public void setMapID(int mapID) {
		this.mapID = mapID;
		if (view != null && view.getMapID() != mapID) view = null;
	}
	
	@Override
	public boolean isSimilar(ItemMeta meta, boolean ignoreDamage) {
		if (!super.isSimilar(meta, ignoreDamage))
			return false;
		MapMeta mapMeta = (MapMeta) meta;
		if (getColor() != null && !getColor().equals(mapMeta.getColor())) {
			return false;
		} else if (mapMeta.getColor() != null)
			return false;
		if (getMapId() != mapMeta.getMapId())
			return false;
		// TODO isSimilar
		return true;
	}

}
