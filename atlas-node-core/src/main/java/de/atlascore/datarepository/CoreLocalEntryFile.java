package de.atlascore.datarepository;

import de.atlasmc.datarepository.EntryFile;

public class CoreLocalEntryFile implements EntryFile {

	private byte[] checksum;
	private long lastTouch;
	private String file;
	private long size;
	
	public CoreLocalEntryFile(String file, long lastTouch, byte[] checksum, long size) {
		this.file = file;
		this.lastTouch = lastTouch;
		this.checksum = checksum;
		this.size = size;
	}
	
	@Override
	public byte[] checksum() {
		return checksum;
	}

	@Override
	public long lastTouch() {
		return lastTouch;
	}

	@Override
	public String file() {
		return file;
	}
	
	@Override
	public long size() {
		return size;
	}

}
