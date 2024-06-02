package de.atlasmc.datarepository;

public interface LocalRepository extends Repository {
	
	void registerNamespace(String namespace, String path);

}
