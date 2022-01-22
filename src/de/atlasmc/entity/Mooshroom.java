package de.atlasmc.entity;

public interface Mooshroom extends Cow {
	
	public Variant getVariant();
	
	public void setVariant(Variant variant);
	
	public static enum Variant {
		RED("red"),
		BROWN("brown");
		
		private String name;
		
		private Variant(String name) {
			this.name = name;
		}
		
		public String getNameID() {
			return name;
		}
		
		public int getID() {
			return ordinal();
		}
		
		public static Variant getByID(int id) {
			switch (id) {
			case 0:
				return RED;
			case 1:
				return BROWN;
			default:
				return RED;
			}
		}
		
		public static Variant getByNameID(String name) {
			if (BROWN.getNameID().equals(name))
				return BROWN;
			else return RED;
		}
		
	}

}
