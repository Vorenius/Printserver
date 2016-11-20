package setup;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.List;

import crypto.SHAImpl;

public class HashFactory {
	static final String path = "files/passwords";

	public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeySpecException, IOException {

		String[] usernames = { "Alice", "Bob", "Cecilia", "David", "Erica", "Fred", "George" };
		String[] originalPasswords = { "AlicesSecretPassword", "BobsSecretPassword", "CeciliasSecretPassword", "DavidsSecretPassword", "EricasSecretPassword", "FredsSecretPassword", "GeorgesSecretPassword" };
		
		List<String> lines = new ArrayList<String>();

		for (int i = 0; i < originalPasswords.length; i++) {

			lines.add(SHAImpl.generatePasswordHash(usernames[i], originalPasswords[i]));
		}

		Path file = Paths.get(path);
		Files.write(file, lines, Charset.forName("UTF-8"));

	}

}
