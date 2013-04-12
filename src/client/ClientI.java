package client;

import java.rmi.*;

import server.Message;

public interface ClientI extends Remote {
	void receive(Message msg) throws RemoteException;
}