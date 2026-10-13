package de.atlasmc.util.secrets;

import java.security.Key;
import java.security.cert.Certificate;

/**
 * Manager for handling secrets
 */
public interface SecretManager {
	
	Key getKey(String alias) throws SecretManagerException;
	
	void setKey(String alias, Key key) throws SecretManagerException;
	
	Certificate getCertificate(String alias) throws SecretManagerException;
	
	void store() throws SecretManagerException;
	
}
