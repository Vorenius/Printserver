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
	private static final String path = "files/passwords";
	private static final String ACLPath = "files/ACL.txt";

	public PrintServant() {

	}

	public static void main(String[] args) throws RemoteException {

		PrintServant obj = new PrintServant();
		PrintService stub = (PrintService) UnicastRemoteObject.exportObject(obj, 5099); // add
																						// server
																						// sockets

		Registry registry = LocateRegistry.createRegistry(5099);
		registry.rebind("printing", stub);

	}

	
	@Override
	public String print(String filename, String printer, String username, String password) throws RemoteException {
		String name = authenticate(username, password);

		if (authorize(name, "print")) {
			System.out.println("printing out " + filename + " on printer with ID: " + printer);
			return "added to queue";
		}
		return "unauthorized access";

	}

	@Override
	public String queue(String username, String password) throws RemoteException {
		String name = authenticate(username, password);

		if (authorize(name, "queue")) {
			return "1 \t example.doc \n" + "2 \t document.pdf";

		}
		return "unauthorized access";
	}

	@Override
	public String topQueue(int job, String username, String password) throws RemoteException {
		String name = authenticate(username, password);

		if (authorize(name, "topQueue")) {
			System.out.println("job " + job + " moved to top of queue");
			return "moved to top of queue";
		}
		return "unauthorized access";
	}

	@Override
	public String start(String username, String password) throws RemoteException {
		String name = authenticate(username, password);

		if (authorize(name, "start")) {
			System.out.println("print server started");
			return "print server started";
		}
		return "unauthorized access";

	}

	@Override
	public String stop(String username, String password) throws RemoteException {
		String name = authenticate(username, password);

		if (authorize(name, "stop")) {
			System.out.println("print server stopped");
			return "print server stopped";
		}
		return "unauthorized access";
	}

	@Override
	public String restart(String username, String password) throws RemoteException {
		String name = authenticate(username, password);

		if (authorize(name, "restart")) {
			System.out.println("print server restarted");
			return "print server restarted";
		}
		return "unauthorized access";
	}

	@Override
	public String status(String username, String password) throws RemoteException {
		String name = authenticate(username, password);

		if (authorize(name, "status")) {
			return "status: OK";
		}
		return "unauthorized access";
	}

	@Override
	public String readConfig(String parameter, String username, String password) throws RemoteException {
		String name = authenticate(username, password);

		if (authorize(name, "readConfig")) {
			if (parameter.equals("black ink")) {
				return "60%";
			} else if (parameter.equals("color ink")) {
				return "80%";
			} else {
				return "no such parameter";
			}
		}
		return "unauthorized access";

	}

	@Override
	public String setConfig(String parameter, String value, String username, String password) throws RemoteException {
		String name = authenticate(username, password);
		
		if (authorize(name, "setConfig")) {
			System.out.println(parameter + " has been set to " + value);
			return "parameter set";
		}
		return "unauthorized access";
	}

	private String authenticate(String username, String password) {

		List<String> pwhashes;
		try {
			pwhashes = Files.readAllLines(Paths.get(path));
			for (String pwhash : pwhashes) {
				if (pwhash.split(":")[0].equals(username)) {
					if (SHAImpl.validatePassword(password, pwhash)){
						return username;
					}
				}
			}
			return null;
		} catch (IOException | NoSuchAlgorithmException | InvalidKeySpecException e) {
			e.printStackTrace();
			return null;
		}
	}

	private boolean authorize(String name, String op) {
		List<String> entities;
		try {
			entities = Files.readAllLines(Paths.get(ACLPath));
			for (String entity : entities) {
				String[] split = entity.split(":");
				if (split[0].equals(name)) { // if Subject's name is in ACL
					for (int i = 1; i < split.length; i++) {
						if (split[i].equals(op)){ // if the operation is in the entity
							return true;
						}
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
}
