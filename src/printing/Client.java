package printing;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import Interfaces.PrintService;

public class Client {

	public static void main(String[] args) throws MalformedURLException, RemoteException, NotBoundException {
		
		PrintService ps = (PrintService) Naming.lookup("rmi://localhost:5099/printing");

		System.out.println("--- " + ps.echo("hey server"));
		ps.print("something.docx", "101E");
		ps.restart();
		System.out.println(ps.queue());
	}
}
