package de.atlasmc.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateCrtKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.util.Base64;

public final class EncryptionUtil {
	
	private EncryptionUtil() {}
	
	public static KeyPair loadRSAFromFile(Path path) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
		byte[] data = Files.readAllBytes(path);
		PKCS8EncodedKeySpec privateSpec = new PKCS8EncodedKeySpec(data);
		KeyFactory kf = KeyFactory.getInstance("RSA");
		RSAPrivateCrtKey privateKey = (RSAPrivateCrtKey) kf.generatePrivate(privateSpec);;
		RSAPublicKeySpec publicSpec = new RSAPublicKeySpec(privateKey.getModulus(), privateKey.getPublicExponent());
		PublicKey publicKey = kf.generatePublic(publicSpec);
		return new KeyPair(publicKey, privateKey);
	}
	
	public static String keyToString(PublicKey key) {
		return keyToString(key, "PUBLIC");
	}
	
	public static String keyToString(PrivateKey key) {
		return keyToString(key, "PRIVATE");
	}
	
	private static String keyToString(Key key, String name) {
		String base64 = Base64.getEncoder().encodeToString(key.getEncoded());
		String keyData = "";
		for (int i = 0; i < base64.length(); i += 65)  {
			keyData += base64.substring(i, Math.min(base64.length(), i + 65)) + "\n";
		}
		return String.format("-----BEGIN %s %s KEY-----\n%s-----END %s %s KEY-----\n", key.getAlgorithm(), name, keyData, key.getAlgorithm(), name);
	}

}
