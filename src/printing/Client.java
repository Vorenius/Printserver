package printing;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import Interfaces.PrintService;

public class Client {

	public static void main(String[] args) throws MalformedURLException, RemoteException, NotBoundException {
		
		String username = "Bob";
		String password = "bobsSecretPassword";
		
		PrintService ps = (PrintService) Naming.lookup("rmi://localhost:5099/printing");

		System.out.println(ps.echo("hey server", username, password));
		ps.print("something.docx", "101E", username, password);
		ps.restart(username, password);
		System.out.println(ps.queue(username, password));
	}
}
