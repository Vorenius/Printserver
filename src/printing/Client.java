package printing;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import Interfaces.PrintService;

public class Client {

	public static void main(String[] args) throws MalformedURLException, RemoteException, NotBoundException {
		
		String username = "Bob";
		String password = "BobsSecretPassword";
		
		PrintService ps = (PrintService) Naming.lookup("rmi://localhost:5099/printing");

		ps.print("something.docx", "101E", "Alice", "AlicesSecretPassword");
		ps.restart(username, password);
		System.out.println("Janitor calling queue():\n" + ps.queue(username, password) + "\n");
		System.out.println("Manager calling queue():\n" + ps.queue("Alice", "AlicesSecretPassword") + "\n");

	}
}
