package printing;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;

import Interfaces.PrintService;
import crypto.SHAImpl;

public class PrintServant implements PrintService {
	static final String path = "files/passwords";

	public PrintServant()  {

	}
	
	public static void main(String[] args) throws RemoteException {
		
		PrintServant obj = new PrintServant();
		PrintService stub = (PrintService) UnicastRemoteObject.exportObject(obj, 5099); // add server sockets
		
		Registry registry = LocateRegistry.createRegistry(5099);
		registry.rebind("printing", stub);

	}
	
	

	@Override
	public String echo(String input, String username, String password) throws RemoteException {
		try {
			if (!isAuthenticated(username, password)) {
				return "wrong user name and password combination";
			}
		} catch (NoSuchAlgorithmException | InvalidKeySpecException | IOException e) {
			System.err.println("Server error: ");
			e.printStackTrace();
		}
		return "from server: " + input;
	}

	@Override
	public String print(String filename, String printer, String username, String password) throws RemoteException {
		try {
			if (!isAuthenticated(username, password)) {
				return "wrong user name and password combination";
			}
		} catch (NoSuchAlgorithmException | InvalidKeySpecException | IOException e) {
			System.err.println("Server error: ");
			e.printStackTrace();
		}
		
		System.out.println("printing out " + filename + " on printer with ID: " + printer);
		return "added to queue";
	}

	@Override
	public String queue(String username, String password) throws RemoteException {
		try {
			if (!isAuthenticated(username, password)) {
				return "wrong user name and password combination";
			}
		} catch (NoSuchAlgorithmException | InvalidKeySpecException | IOException e) {
			System.err.println("Server error: ");
			e.printStackTrace();
		}
		
		return "1 \t example.doc \n" + "2 \t document.pdf";
	}

	@Override
	public String topQueue(int job, String username, String password) throws RemoteException {
		try {
			if (!isAuthenticated(username, password)) {
				return "wrong user name and password combination";
			}
		} catch (NoSuchAlgorithmException | InvalidKeySpecException | IOException e) {
			System.err.println("Server error: ");
			e.printStackTrace();
		}
		
		System.out.println("job " + job + " moved to top of queue");
		return "moved to top of queue";

	}

	@Override
	public String start(String username, String password) throws RemoteException {
		try {
			if (!isAuthenticated(username, password)) {
				return "wrong user name and password combination";
			}
		} catch (NoSuchAlgorithmException | InvalidKeySpecException | IOException e) {
			System.err.println("Server error: ");
			e.printStackTrace();
		}
		
		System.out.println("print server started");
		return "print server started";
	}

	@Override
	public String stop(String username, String password) throws RemoteException {
		try {
			if (!isAuthenticated(username, password)) {
				return "wrong user name and password combination";
			}
		} catch (NoSuchAlgorithmException | InvalidKeySpecException | IOException e) {
			System.err.println("Server error: ");
			e.printStackTrace();
		}
		
		System.out.println("print server stopped");
		return "print server stopped";

	}

	@Override
	public String restart(String username, String password) throws RemoteException {
		try {
			if (!isAuthenticated(username, password)) {
				return "wrong user name and password combination";
			}
		} catch (NoSuchAlgorithmException | InvalidKeySpecException | IOException e) {
			System.err.println("Server error: ");
			e.printStackTrace();
		}
				// clear queue
		System.out.println("print server restarted");
		return "print server restarted";

	}

	@Override
	public String status(String username, String password) throws RemoteException {
		try {
			if (!isAuthenticated(username, password)) {
				return "wrong user name and password combination";
			}
		} catch (NoSuchAlgorithmException | InvalidKeySpecException | IOException e) {
			System.err.println("Server error: ");
			e.printStackTrace();
		}
		
		return "status: OK";
	}

	@Override
	public String readConfig(String parameter, String username, String password) throws RemoteException {
		try {
			if (!isAuthenticated(username, password)) {
				return "wrong user name and password combination";
			}
		} catch (NoSuchAlgorithmException | InvalidKeySpecException | IOException e) {
			System.err.println("Server error: ");
			e.printStackTrace();
		}
		
		if (parameter.equals("black ink")) {
			return "60%";
		} else if (parameter.equals("color ink")) {
			return "80%";
		} else {
			return "no such parameter";
		}
	}

	@Override
	public String setConfig(String parameter, String value, String username, String password) throws RemoteException {
		try {
			if (!isAuthenticated(username, password)) {
				return "wrong user name and password combination";
			}
		} catch (NoSuchAlgorithmException | InvalidKeySpecException | IOException e) {
			System.err.println("Server error: ");
			e.printStackTrace();
		}
		
		System.out.println(parameter + " has been set to " + value);
		return "parameter set";
	}

	private boolean isAuthenticated(String username, String password)
			throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {

		List<String> pwhashes = Files.readAllLines(Paths.get(path));

		for (String pwhash : pwhashes) {
			if (pwhash.split(":")[0].equals(username)) {
				return SHAImpl.validatePassword(password, pwhash);
			}

		}

		return false;
	}

}
