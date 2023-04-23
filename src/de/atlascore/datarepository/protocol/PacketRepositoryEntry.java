package de.atlascore.datarepository.protocol;

import java.util.Map;

import de.atlasmc.NamespacedKey;

public class PacketRepositoryEntry {
	
	private Map<String, String> signedFiles;
	private Map<String, String> additionalInfo;
	private String description;
	private NamespacedKey identifier;
	private EntryAction action;
	private int transferID;
	
	public static enum EntryAction {
		FULL_INFO,
		FILE_INFO,
		ADDITION_INFO,
		DESCRIPTION,
		REQUEST_DATA,
		SEND_DATA;
	}

}
