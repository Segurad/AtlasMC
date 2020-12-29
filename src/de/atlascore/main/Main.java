package de.atlascore.main;

import java.util.List;

import de.atlasmc.Material;

public class Main {
	
	public static void main(String[] args) {
		System.out.println("Start...");
		List<Material> materials = Material.getMaterials();
		int count = 0;
		for (Material mat : materials) {
			System.out.println(count++ + " : " + mat.getName() + " : " + mat);
			if (count == 20) break;
		}
	}

}
