package de.atlasmc.core.datarepository;

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
	
	public void setChecksum(byte[] checksum) {
		this.checksum = checksum;
	}
	
	public void setLastTouch(long lastTouch) {
		this.lastTouch = lastTouch;
	}
	
	public void setSize(long size) {
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

	@Override
	public boolean matchChecksum(byte[] checksum) {
		return this.checksum.equals(checksum);
	}

}
