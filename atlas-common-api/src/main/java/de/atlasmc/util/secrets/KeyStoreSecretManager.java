package de.atlasmc.util.secrets;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;

public class KeyStoreSecretManager implements SecretManager {

	private final KeyStore store;
	private final char[] password;
	private final File file;
	
	public KeyStoreSecretManager(File file, String password) throws SecretManagerException {
		this.password = password != null ? password.toCharArray() : null;
		this.file = file;
		if (file.exists()) {
			try {
				store = KeyStore.getInstance(file, this.password);
			} catch (KeyStoreException | NoSuchAlgorithmException | CertificateException | IOException e) {
				throw new SecretManagerException("Failed to load KeyStore", e);
			}
		} else {
			try {
				store = KeyStore.getInstance(KeyStore.getDefaultType());
				store.load(null, null); // to initialize the newly created key store
				store();
			} catch (KeyStoreException | NoSuchAlgorithmException | CertificateException | IOException e) {
				throw new SecretManagerException("Failed to create KeyStore", e);
			}
		}
	}
	
	@Override
	public Key getKey(String alias) throws SecretManagerException {
		try {
			return store.getKey(alias, null);
		} catch (UnrecoverableKeyException | KeyStoreException | NoSuchAlgorithmException e) {
			throw new SecretManagerException("Error while fetching key: " + alias, e);
		}
	}
	
	@Override
	public void setKey(String alias, Key key) throws SecretManagerException {
		try {
			store.setKeyEntry(alias, key, null, null);
		} catch (KeyStoreException e) {
			throw new SecretManagerException("Failed to store key: " + alias, e);
		}
	}

	@Override
	public Certificate getCertificate(String alias) throws SecretManagerException {
		try {
			return store.getCertificate(alias);
		} catch (KeyStoreException e) {
			throw new SecretManagerException("Error while fetching certificate: " + alias, e);
		}
	}
	
	@Override
	public synchronized void store() throws SecretManagerException {
		try (var out = new FileOutputStream(file)) {
			store.store(out, this.password);
		} catch (IOException | KeyStoreException | NoSuchAlgorithmException | CertificateException e) {
			throw new SecretManagerException("Failed to store keystore!", e);
		}
	}

}
