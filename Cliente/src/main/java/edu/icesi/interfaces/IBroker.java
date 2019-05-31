package edu.icesi.interfaces;


import org.osoa.sca.annotations.Service;

/**
 * IBroker
 */
@Service
public interface IBroker {

    public void attach(String url);

    public void deAttach(String url);

    public String getOperation();

    public void removeProces(String url);

}