package Interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface PrintService extends Remote {
	
	public String echo(String input) throws RemoteException;
	public void print(String filename, String printer) throws RemoteException;
	public String queue() throws RemoteException;
	public void topQueue(int job) throws RemoteException;
	public void start() throws RemoteException;
	public void stop() throws RemoteException;
	public void restart() throws RemoteException;
	public String status() throws RemoteException;
	public String readConfig(String parameter) throws RemoteException;
	public void setConfig(String parameter, String value) throws RemoteException;
}
