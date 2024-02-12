package de.atlascore.main;

import java.io.IOException;

public class DataGenerator {

	public static void main(String[] args) throws IOException {
		System.out.println("Using resource base path: " + args[0]);
		System.out.println("Generating resources");
		//MapColor.writeIntToMapColorCache(new File(args[0], "/data/mapcolor_cache"));
	}

}
