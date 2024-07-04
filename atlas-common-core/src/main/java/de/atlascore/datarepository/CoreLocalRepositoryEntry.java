package de.atlascore.datarepository;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HexFormat;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import de.atlasmc.NamespacedKey;
import de.atlasmc.datarepository.Repository;
import de.atlasmc.datarepository.RepositoryEntry;
import de.atlasmc.datarepository.RepositoryEntryUpdate;
import de.atlasmc.util.concurrent.future.CompleteFuture;
import de.atlasmc.util.concurrent.future.Future;
import de.atlasmc.util.configuration.ConfigurationSection;
import de.atlasmc.util.configuration.ConfigurationSerializeable;
import de.atlasmc.util.configuration.MemoryConfigurationSection;

public class CoreLocalRepositoryEntry implements RepositoryEntry, ConfigurationSerializeable {

	private final CoreAbstractLocalRepository repo;
	private final NamespacedKey key;
	private String description;
	private String root;
	private final List<CoreLocalEntryFile> files;
	private byte[] checksum;
	
	public CoreLocalRepositoryEntry(CoreAbstractLocalRepository repo, NamespacedKey key, ConfigurationSection config) {
		this.repo = repo;
		this.key = key;
		this.root = config.getString("root");
		this.description = config.getString("description");
		String checksumRaw = config.getString("checksum");
		HexFormat hex = HexFormat.of();
		this.checksum = hex.parseHex(checksumRaw);
		List<ConfigurationSection> fileConfigs = config.getListOfType("files", ConfigurationSection.class);
		List<CoreLocalEntryFile> files = new ArrayList<>(fileConfigs.size());
		for (ConfigurationSection fileConfig : fileConfigs) {
			String filePath = fileConfig.getString("file");
			long lastTouch = fileConfig.getLong("lastTouch");
			String fileChecksumRaw = fileConfig.getString("checksum");
			byte[] fileChecksum = hex.parseHex(fileChecksumRaw);
			long size = fileConfig.getLong("size");
			CoreLocalEntryFile file = new CoreLocalEntryFile(filePath, lastTouch, fileChecksum, size);
			files.add(file);
		}
		this.files = new CopyOnWriteArrayList<>(files);
	}
	
	public CoreLocalRepositoryEntry(CoreAbstractLocalRepository repo, NamespacedKey key, String description, byte[] checksum, Collection<? extends CoreLocalEntryFile> files) {
		this.repo = repo;
		this.key = key;
		this.checksum = Arrays.copyOf(checksum, checksum.length);
		this.files = new CopyOnWriteArrayList<>(files);
		this.description = description;
	}
	
	
	@Override
	public byte[] checksum() {
		return checksum;
	}
	
	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public Collection<? extends CoreLocalEntryFile> getFiles() {
		return files;
	}

	@Override
	public boolean isLocalAvailable() {
		return true;
	}

	@Override
	public boolean copyTo(File destination) throws IOException {
		return copyTo(destination, true);
	}
	
	@Override
	public boolean copyTo(File destination, boolean override) throws IOException {
		return repo.copyEntry(this, destination, override);
	}

	@Override
	public NamespacedKey getNamespacedKey() {
		return key;
	}
	
	public String getRoot() {
		return root;
	}

	@Override
	public Future<Boolean> delete() {
		return repo.delete(this);
	}

	@Override
	public boolean isTouched() throws IOException {
		return isTouched(true);
	}
	
	@Override
	public boolean isTouched(boolean shallow) throws IOException {
		return repo.isTouched(null, shallow);
	}

	@Override
	public Future<RepositoryEntryUpdate> update() {
		return repo.update(this);
	}
	
	@Override
	public boolean matchChecksum(byte[] checksum) {
		return this.checksum.equals(checksum);
	}

	public void addFile(String file, long lastTouch, byte[] checksum, long size) {
		files.add(new CoreLocalEntryFile(file, lastTouch, checksum, size));
	}

	public void setChecksum(byte[] checksum) {
		this.checksum = checksum;
	}
	
	@Override
	public boolean isDirectory() {
		return files.size() > 1 || !files.get(0).file().equals(root);
	}
	
	public <T extends ConfigurationSection> T toConfiguration(T config) {
		config.set("root", root);
		config.set("description", description);
		config.set("checksum", HexFormat.of().formatHex(checksum));
		ArrayList<ConfigurationSection> files = new ArrayList<>(this.files.size());
		config.set("files", files);
		for (CoreLocalEntryFile file : this.files) {
			ConfigurationSection fileCfg = new MemoryConfigurationSection(config);
			fileCfg.set("file", file.file());
			fileCfg.set("checksum", HexFormat.of().formatHex(file.checksum()));
			fileCfg.set("size", file.size());
			fileCfg.set("lastTouch", file.lastTouch());
		}
		return config;
	}

	@Override
	public Repository getRepository() {
		return repo;
	}

	@Override
	public Future<RepositoryEntry> makeAvailable() {
		return CompleteFuture.of(this);
	}
	
}
