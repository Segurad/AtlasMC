package de.atlasmc.node.block.data;

public interface Ageable extends BlockData {
	
	public int getAge();
	public int getMaxAge();
	public void setAge(int age);

}
