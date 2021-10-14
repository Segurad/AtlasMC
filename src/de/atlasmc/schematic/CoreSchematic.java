package de.atlasmc.schematic;

import java.util.ArrayList;
import java.util.List;

import de.atlasmc.SimpleLocation;

public class CoreSchematic implements Schematic {
	
	private final SchematicSection[] sections; // ordered by y > z > x
	private List<SchematicAccess> schematics;
	private final SimpleLocation off;
	private final int lengthX, lengthZ, hight;
	
	public CoreSchematic(SimpleLocation off) {
		this(off, 0, 0, 0);
	}
	
	public CoreSchematic(SimpleLocation off, int lengthX, int hight, int lengthZ) {
		this.off = off.clone().convertToBlock();
		this.lengthX = lengthX;
		this.lengthZ = lengthZ;
		this.hight = hight;
		sections = lengthX == 0 || lengthZ == 0 || hight == 0 ? 
				null : new SchematicSection[lengthX>>4*lengthZ>>4*hight>>4];
	}
	
	@Override
	public SchematicSection getSection(int x, int y, int z) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getHight() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getLengthX() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getLengthZ() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public SimpleLocation getOffset() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SimpleLocation getOffset(SimpleLocation loc) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOffset(SimpleLocation loc) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<SchematicAccess> getSchematics() {
		return schematics == null ? List.of() : schematics;
	}

	@Override
	public SchematicAccess addSchematic(Schematic schematic, int offX, int offY, int offZ, float rotation, boolean visible) {
		if (schematics == null) schematics = new ArrayList<>();
		SchematicAccess access = new CoreSchematicAccess(schematic, offX, offY, offZ, rotation, visible);
		schematics.add(access);
		return access;
	}

}
