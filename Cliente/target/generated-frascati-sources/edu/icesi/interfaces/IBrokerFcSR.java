/*
 * Generated by: org.ow2.frascati.tinfi.opt.oo.ServiceReferenceClassGenerator
 * on: Mon Jun 03 20:03:06 COT 2019
 */

package edu.icesi.interfaces;


public class IBrokerFcSR
extends org.ow2.frascati.tinfi.oasis.ServiceReferenceImpl<edu.icesi.interfaces.IBroker>
implements edu.icesi.interfaces.IBroker {

  public IBrokerFcSR(Class<edu.icesi.interfaces.IBroker> businessInterface,edu.icesi.interfaces.IBroker service)  {
    super(businessInterface,service);
  }

  public edu.icesi.interfaces.IBroker getService()  {
    return this;
  }

  public java.lang.String getOperation()  {
    java.lang.String ret = service.getOperation();
    return ret;
  }

  public void deAttach(final java.lang.String arg0)  {
    service.deAttach(arg0);
  }

  public void removeProces(final java.lang.String arg0)  {
    service.removeProces(arg0);
  }

  public void attach(final java.lang.String arg0)  {
    service.attach(arg0);
  }

}
