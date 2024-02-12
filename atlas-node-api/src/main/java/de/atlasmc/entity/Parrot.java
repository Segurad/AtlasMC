package de.atlasmc.entity;

public interface Parrot extends Tameable {
	
	public Type getParrotType();
	
	public void setParrotType(Type type);
	
	public static enum Type {
		RED_BLUE,
		BLUE,
		GREEN,
		YELLOW_BLUE,
		GREY;
		
		public int getID() {
			return ordinal();
		}
		
		public static Type getByID(int id) {
			switch(id) {
			case 0:
				return RED_BLUE;
			case 1:
				return BLUE;
			case 2:
				return GREEN;
			case 3:
				return YELLOW_BLUE;
			case 4:
				return GREY;
			default:
				return RED_BLUE;
			}
		}
		
	}

}
