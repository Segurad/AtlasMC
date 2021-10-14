package de.atlasmc.util;

/**
 * Utility for 3D Arrays with ordered by <b><u>Y > Z > X</u></b>
 */
public class Array3DUtil {
	
	private Array3DUtil() {};
	
	//--- Object -----------------------------------------------------
	/**
	 * 
	 * @param array[x][y][z]
	 * @param lenX
	 * @param lenY
	 * @param lenZ
	 * @param offX
	 * @param offY
	 * @param offZ
	 */
	public static void flipX(final Object[][][] array, final int lenX, final int lenY, final int lenZ, final int offX, final int offY, final int offZ) {
		final int workLen = lenX/2;
		for (int x = offX; x < workLen; x++) {
			for (int y = offY; y < lenY; y++) {
				for (int z = offZ; z < lenZ; z++) {
					final Object temp = array[x][y][z];
					array[x][y][z] = array[lenX - x - 1][y][z];
					array[lenX - x - 1][y][z] = temp;
				}
			}
		}
	}
	
	public static void flipY(final Object[][][] array, final int lenX, final int lenY, final int lenZ, final int offX, final int offY, final int offZ) {
		final int workLen = lenY/2;
		for (int x = offX; x < lenX; x++) {
			for (int y = offY; y < workLen; y++) {
				for (int z = offZ; z < lenZ; z++) {
					final Object temp = array[x][y][z];
					array[x][y][z] = array[x][lenX - y - 1][z];
					array[x][lenX - y - 1][z] = temp;
				}
			}
		}
	}
	
	public static void flipZ(final Object[][][] array, final int lenX, final int lenY, final int lenZ, final int offX, final int offY, final int offZ) {
		final int workLen = lenZ/2;
		for (int x = offX; x < lenX; x++) {
			for (int y = offY; y < lenY; y++) {
				for (int z = offZ; z < workLen; z++) {
					final Object temp = array[x][y][z];
					array[x][y][z] = array[x][y][lenX - z - 1];
					array[x][y][lenX - z - 1] = temp;
				}
			}
		}
	}
	
	//--- int --------------------------------------------------------
	
	public static void flipX(final int[][][] array, final int lenX, final int lenY, final int lenZ, final int offX, final int offY, final int offZ) {
		final int workLen = lenX/2;
		for (int x = offX; x < workLen; x++) {
			for (int y = offY; y < lenY; y++) {
				for (int z = offZ; z < lenZ; z++) {
					final int temp = array[x][y][z];
					array[x][y][z] = array[lenX - x - 1][y][z];
					array[lenX - x - 1][y][z] = temp;
				}
			}
		}
	}
	
	public static void flipY(final int[][][] array, final int lenX, final int lenY, final int lenZ, final int offX, final int offY, final int offZ) {
		final int workLen = lenY/2;
		for (int x = offX; x < lenX; x++) {
			for (int y = offY; y < workLen; y++) {
				for (int z = offZ; z < lenZ; z++) {
					final int temp = array[x][y][z];
					array[x][y][z] = array[x][lenX - y - 1][z];
					array[x][lenX - y - 1][z] = temp;
				}
			}
		}
	}
	
	public static void flipZ(final int[][][] array, final int lenX, final int lenY, final int lenZ, final int offX, final int offY, final int offZ) {
		final int workLen = lenZ/2;
		for (int x = offX; x < lenX; x++) {
			for (int y = offY; y < lenY; y++) {
				for (int z = offZ; z < workLen; z++) {
					final int temp = array[x][y][z];
					array[x][y][z] = array[x][y][lenX - z - 1];
					array[x][y][lenX - z - 1] = temp;
				}
			}
		}
	}
	
	//--- short --------------------------------------------------------
	
	public static void flipX(final short[][][] array, final int lenX, final int lenY, final int lenZ, final int offX, final int offY, final int offZ) {
		final int workLen = lenX/2;
		for (int x = offX; x < workLen; x++) {
			for (int y = offY; y < lenY; y++) {
				for (int z = offZ; z < lenZ; z++) {
					final short temp = array[x][y][z];
					array[x][y][z] = array[lenX - x - 1][y][z];
					array[lenX - x - 1][y][z] = temp;
				}
			}
		}
	}
		
	public static void flipY(final short[][][] array, final int lenX, final int lenY, final int lenZ, final int offX, final int offY, final int offZ) {
		final int workLen = lenY/2;
		for (int x = offX; x < lenX; x++) {
			for (int y = offY; y < workLen; y++) {
				for (int z = offZ; z < lenZ; z++) {
					final short temp = array[x][y][z];
					array[x][y][z] = array[x][lenX - y - 1][z];
					array[x][lenX - y - 1][z] = temp;
				}
			}
		}
	}
	
	public static void flipZ(final short[][][] array, final int lenX, final int lenY, final int lenZ, final int offX, final int offY, final int offZ) {
		final int workLen = lenZ/2;
		for (int x = offX; x < lenX; x++) {
			for (int y = offY; y < lenY; y++) {
				for (int z = offZ; z < workLen; z++) {
					final short temp = array[x][y][z];
					array[x][y][z] = array[x][y][lenX - z - 1];
					array[x][y][lenX - z - 1] = temp;
				}
			}
		}
	}
	
	//--- byte --------------------------------------------------------
	
	public static void flipX(final byte[][][] array, final int lenX, final int lenY, final int lenZ, final int offX, final int offY, final int offZ) {
		final int workLen = lenX/2;
		for (int x = offX; x < workLen; x++) {
			for (int y = offY; y < lenY; y++) {
				for (int z = offZ; z < lenZ; z++) {
					final byte temp = array[x][y][z];
					array[x][y][z] = array[lenX - x - 1][y][z];
					array[lenX - x - 1][y][z] = temp;
				}
			}
		}
	}
		
	public static void flipY(final byte[][][] array, final int lenX, final int lenY, final int lenZ, final int offX, final int offY, final int offZ) {
		final int workLen = lenY/2;
		for (int x = offX; x < lenX; x++) {
			for (int y = offY; y < workLen; y++) {
				for (int z = offZ; z < lenZ; z++) {
					final byte temp = array[x][y][z];
					array[x][y][z] = array[x][lenX - y - 1][z];
					array[x][lenX - y - 1][z] = temp;
				}
			}
		}
	}
	
	public static void flipZ(final byte[][][] array, final int lenX, final int lenY, final int lenZ, final int offX, final int offY, final int offZ) {
		final int workLen = lenZ/2;
		for (int x = offX; x < lenX; x++) {
			for (int y = offY; y < lenY; y++) {
				for (int z = offZ; z < workLen; z++) {
					final byte temp = array[x][y][z];
					array[x][y][z] = array[x][y][lenX - z - 1];
					array[x][y][lenX - z - 1] = temp;
				}
			}
		}
	}
	
	//----------------------------------------------------------------------------------
	
	public static void fill(final Object[][][] array, final Object value, final int lenX, final int lenY, final int lenZ, final int offX, final int offY, final int offZ) {
		for (int x = offX; x < lenX; x++) {
			for (int y = offY; y < lenY; y++) {
				for (int z = offZ; z < lenZ; z++) {
					array[x][y][z] = value;
				}
			}
		}
	}

}
