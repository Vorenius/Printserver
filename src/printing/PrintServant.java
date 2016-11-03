package printing;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

import Interfaces.PrintService;

public class PrintServant extends UnicastRemoteObject implements PrintService {

	protected PrintServant() throws RemoteException {
		super();
	}

	@Override
	public String echo(String input) throws RemoteException {

		return "from server: " + input;
	}

	@Override
	public void print(String filename, String printer) throws RemoteException {
		System.out.println("printing out " + filename + " on printer with ID: " + printer);
	}

	@Override
	public String queue() throws RemoteException {
		return "1 \t example.doc \n" + "2 \t document.pdf";
	}

	@Override
	public void topQueue(int job) throws RemoteException {
		System.out.println("job " + job + " moved to top of queue");

	}

	@Override
	public void start() throws RemoteException {
		System.out.println("print server started");
	}

	@Override
	public void stop() throws RemoteException {
		System.out.println("print server stopped");

	}

	@Override
	public void restart() throws RemoteException {
		System.out.println("print server restarted");
		// clear queue

	}

	@Override
	public String status() throws RemoteException {
		return "status: OK";
	}

	@Override
	public String readConfig(String parameter) throws RemoteException {
		if (parameter.equals("black ink")) {
			return "60%";
		} else if (parameter.equals("color ink")) {
			return "80%";
		} else {
			return null;
		}
	}

	@Override
	public void setConfig(String parameter, String value) throws RemoteException {
		System.out.println(parameter + " has been set to " + value);
	}

}
