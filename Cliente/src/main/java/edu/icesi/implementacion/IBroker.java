package edu.icesi.implementacion;

import java.rmi.*;

// import org.osoa.sca.annotations.Service;

/**
 * IBroker
 */
// @Service
public interface IBroker extends Remote{

    public void attach(String url)throws RemoteException;

    public void deAttach(String url)throws RemoteException;

    public String getOperation()throws RemoteException;

    public void removeProces(String url)throws RemoteException;

}