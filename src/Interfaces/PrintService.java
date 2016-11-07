package Interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface PrintService extends Remote {
	
	public String echo(String input, String username, String password) throws RemoteException;
	public String print(String filename, String printer, String username, String password) throws RemoteException;
	public String queue(String username, String password) throws RemoteException;
	public String topQueue(int job, String username, String password) throws RemoteException;
	public String start(String username, String password) throws RemoteException;
	public String stop(String username, String password) throws RemoteException;
	public String restart(String username, String password) throws RemoteException;
	public String status(String username, String password) throws RemoteException;
	public String readConfig(String parameter, String username, String password) throws RemoteException;
	public String setConfig(String parameter, String value, String username, String password) throws RemoteException;
}
