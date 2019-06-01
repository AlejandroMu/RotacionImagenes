/*
 * Generated by: org.ow2.frascati.tinfi.opt.oo.InterceptorClassGenerator
 * on: Sat Jun 01 16:55:55 COT 2019
 */

package edu.icesi.interfaces;


public class IMatrixOperationsInterceptorSCAIntent
extends org.ow2.frascati.tinfi.TinfiComponentInterceptor<edu.icesi.interfaces.IMatrixOperations>
implements edu.icesi.interfaces.IMatrixOperations,org.objectweb.fractal.julia.Interceptor {

  private static java.lang.reflect.Method[] METHODS;
  public IMatrixOperationsInterceptorSCAIntent()  {
  }

  private IMatrixOperationsInterceptorSCAIntent(Object obj)  {
    setFcItfDelegate(obj);
  }

  public void initFcController(org.objectweb.fractal.julia.InitializationContext ic) throws org.objectweb.fractal.api.factory.InstantiationException  {
    super.initFcController(ic);
    initIntentHandlersMap(METHODS);
  }

  public Object clone()  {
    IMatrixOperationsInterceptorSCAIntent clone = new IMatrixOperationsInterceptorSCAIntent(getFcItfDelegate());
    initFcClone(clone);
    clone.initIntentHandlersMap(METHODS);
    return clone;
  }

  public double[][] matrixMultiplication(final double[][] arg0,final double[][] arg1) throws java.rmi.RemoteException  {
    java.util.List<org.ow2.frascati.tinfi.api.IntentHandler> handlers = intentHandlersMap.get(METHODS[0]);
    try {
      if( handlers.size() == 0 ) {
        double[][] ret = impl.matrixMultiplication(arg0,arg1);
        return ret;
      }
      else {
        org.objectweb.fractal.api.Component comp = getFcComponent();
        org.objectweb.fractal.api.Interface itf = getFcItf();
        org.ow2.frascati.tinfi.IntentJoinPointImpl<edu.icesi.interfaces.IMatrixOperations> ijp = new org.ow2.frascati.tinfi.IntentJoinPointImpl();
        ijp.init(handlers,comp,itf,impl,METHODS[0],(Object)arg0,(Object)arg1);
    double[][] ret = (double[][]) ijp.proceed();
    return ret;
      }
    }
    catch( Throwable t ) {
      if( t instanceof java.rmi.RemoteException ) {
        throw (java.rmi.RemoteException) t;
      }
      if( t instanceof RuntimeException ) {
        throw (RuntimeException) t;
      }
      throw new org.ow2.frascati.tinfi.TinfiRuntimeException(t);
    }
  }

  public boolean rotar(final int[] arg0,final int[] arg1,final int[] arg2,final double arg3,final java.lang.String arg4) throws java.rmi.RemoteException  {
    java.util.List<org.ow2.frascati.tinfi.api.IntentHandler> handlers = intentHandlersMap.get(METHODS[1]);
    try {
      if( handlers.size() == 0 ) {
        boolean ret = impl.rotar(arg0,arg1,arg2,arg3,arg4);
        return ret;
      }
      else {
        org.objectweb.fractal.api.Component comp = getFcComponent();
        org.objectweb.fractal.api.Interface itf = getFcItf();
        org.ow2.frascati.tinfi.IntentJoinPointImpl<edu.icesi.interfaces.IMatrixOperations> ijp = new org.ow2.frascati.tinfi.IntentJoinPointImpl();
        ijp.init(handlers,comp,itf,impl,METHODS[1],(Object)arg0,(Object)arg1,(Object)arg2,(Object)arg3,(Object)arg4);
    Object proceed = ijp.proceed();
    boolean ret = (proceed==null) ? false : (java.lang.Boolean)proceed;
    return ret;
      }
    }
    catch( Throwable t ) {
      if( t instanceof java.rmi.RemoteException ) {
        throw (java.rmi.RemoteException) t;
      }
      if( t instanceof RuntimeException ) {
        throw (RuntimeException) t;
      }
      throw new org.ow2.frascati.tinfi.TinfiRuntimeException(t);
    }
  }

  static  {
    try {
      METHODS = new java.lang.reflect.Method[]{
        edu.icesi.interfaces.IMatrixOperations.class.getMethod("matrixMultiplication",double[][].class,double[][].class),
        edu.icesi.interfaces.IMatrixOperations.class.getMethod("rotar",int[].class,int[].class,int[].class,double.class,java.lang.String.class),
      };
    }
    catch( NoSuchMethodException e ) {
      throw new org.ow2.frascati.tinfi.TinfiRuntimeException(e);
    }
  }

}
