/*
 * Generated by: org.ow2.frascati.tinfi.opt.oo.ClientInterfaceClassGenerator
 * on: Mon Jun 03 20:03:06 COT 2019
 */

package juliac.generated.java.lang;


public class RunnableFcOutItf
extends juliac.generated.java.lang.RunnableFcInItf
implements java.lang.Runnable,org.ow2.frascati.tinfi.TinfiComponentOutInterface<java.lang.Runnable> {

  public RunnableFcOutItf()  {
  }

  public RunnableFcOutItf(org.objectweb.fractal.api.Component component,String s,org.objectweb.fractal.api.Type type,boolean flag,Object obj)  {
    super(component,s,type,flag,obj);
  }

  public org.oasisopen.sca.ServiceReference<java.lang.Runnable> getServiceReference()  {
    return new juliac.generated.java.lang.RunnableFcSR(java.lang.Runnable.class,this);
  }

}
