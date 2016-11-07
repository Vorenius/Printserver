package crypto;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class SHAImpl {
	static final String ALGORITHM = "PBKDF2WithHmacSHA256";
	static final int ITERATIONS = 20000;
	
	public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeySpecException {
		String originalPassword = "password";
		String username = "Jonatan";
		String generatedSecuredPasswordHash = generatePasswordHash(username, originalPassword);
		System.out.println(generatedSecuredPasswordHash);
		
        boolean matched = validatePassword("password", generatedSecuredPasswordHash);
        System.out.println(matched);
         
        matched = validatePassword("password1", generatedSecuredPasswordHash);
        System.out.println(matched);
	}

	public static boolean validatePassword(String password, String storedPassword)
			throws NoSuchAlgorithmException, InvalidKeySpecException {
		String[] parts = storedPassword.split(":");
		int iterations = Integer.parseInt(parts[1]);
		byte[] salt = fromHex(parts[2]);
		byte[] hash = fromHex(parts[3]);

		PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt, iterations, hash.length * 8);
		SecretKeyFactory skf = SecretKeyFactory.getInstance(ALGORITHM);
		byte[] testHash = skf.generateSecret(spec).getEncoded();

		int diff = hash.length ^ testHash.length;
		for (int i = 0; i < hash.length && i < testHash.length; i++) {
			diff |= hash[i] ^ testHash[i];
		}
		return diff == 0;
	}

	public static String generatePasswordHash(String username, String password)
			throws NoSuchAlgorithmException, InvalidKeySpecException {
		char[] chars = password.toCharArray();
		byte[] salt = getSalt();

		PBEKeySpec spec = new PBEKeySpec(chars, salt, ITERATIONS, 64 * 8);
		SecretKeyFactory skf = SecretKeyFactory.getInstance(ALGORITHM);
		byte[] hash = skf.generateSecret(spec).getEncoded();
		return username + ":" + ITERATIONS + ":" + toHex(salt) + ":" + toHex(hash);
	}

	private static byte[] getSalt() throws NoSuchAlgorithmException {
		SecureRandom sr = SecureRandom.getInstanceStrong();
		byte[] salt = new byte[16];
		sr.nextBytes(salt);
		return salt;
	}

	private static String toHex(byte[] array) {
		BigInteger bi = new BigInteger(1, array);
		String hex = bi.toString(16);
		int paddingLength = (array.length * 2) - hex.length();
		if (paddingLength > 0) {
			return String.format("%0" + paddingLength + "d", 0) + hex;
		} else {
			return hex;
		}
	}

	private static byte[] fromHex(String hex)  {
		byte[] bytes = new byte[hex.length() / 2];
		for (int i = 0; i < bytes.length; i++) {
			bytes[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
		}
		return bytes;
	}
}
