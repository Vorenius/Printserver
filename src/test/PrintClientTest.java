package test;

import static org.junit.Assert.*;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import org.junit.Test;

import Interfaces.PrintService;

public class PrintClientTest {

	@Test
	public void testQueue() throws RemoteException, MalformedURLException, NotBoundException {
		String username = "Bob";
		String password = "bobsSecretPassword";
		
		PrintService ps = (PrintService) Naming.lookup("rmi://localhost:5099/printing");
		
		assertEquals("1 \t example.doc \n" + "2 \t document.pdf", ps.queue(username, password));
	}

}
