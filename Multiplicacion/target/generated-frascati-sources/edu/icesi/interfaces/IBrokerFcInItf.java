/*
 * Generated by: org.ow2.frascati.tinfi.opt.oo.ServerInterfaceClassGenerator
 * on: Sat Jun 01 16:55:55 COT 2019
 */

package edu.icesi.interfaces;


public class IBrokerFcInItf
extends org.ow2.frascati.tinfi.TinfiComponentInterface<edu.icesi.interfaces.IBroker>
implements edu.icesi.interfaces.IBroker {

  public IBrokerFcInItf()  {
  }

  public IBrokerFcInItf(org.objectweb.fractal.api.Component component,String s,org.objectweb.fractal.api.Type type,boolean flag,Object obj)  {
    super(component,s,type,flag,obj);
  }

  public java.lang.String getOperation()  {
    if ( impl == null )
    {
      throw new java.lang.NullPointerException("Trying to invoke a method on a client or server interface whose complementary interface is not bound.");
    }
    java.lang.String ret = impl.getOperation();
    return ret;
  }

  public void deAttach(final java.lang.String arg0)  {
    if ( impl == null )
    {
      throw new java.lang.NullPointerException("Trying to invoke a method on a client or server interface whose complementary interface is not bound.");
    }
    impl.deAttach(arg0);
  }

  public void removeProces(final java.lang.String arg0)  {
    if ( impl == null )
    {
      throw new java.lang.NullPointerException("Trying to invoke a method on a client or server interface whose complementary interface is not bound.");
    }
    impl.removeProces(arg0);
  }

  public void attach(final java.lang.String arg0)  {
    if ( impl == null )
    {
      throw new java.lang.NullPointerException("Trying to invoke a method on a client or server interface whose complementary interface is not bound.");
    }
    impl.attach(arg0);
  }

}
