package server;

import java.rmi.*;

import Commandes.Command;

public interface ServerI extends Remote {
	public void addCommand(Command command) throws RemoteException;
}
