package edu.icesi.implementacion;

import java.rmi.*;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.PriorityQueue;
import edu.icesi.interfaces.IBroker;

/**
 * Broker
 */
public class Broker implements IBroker {

    private PriorityQueue<Pair> procesors;
    private HashMap<String, Pair> mProcesors;

    public Broker() throws RemoteException {
        System.out.println("Broker");
        Comparator<Pair> com = new Comparator<Pair>() {

            public int compare(Pair o1, Pair o2) {
                return o1.compareTo(o2);
            }
        };
        procesors = new PriorityQueue<Pair>(20,com);
        mProcesors = new HashMap<String, Pair>();
    }

    @Override
    public synchronized void attach(String url) {
        Pair tmp = new Pair(url, 0);
        mProcesors.put(url, tmp);
        procesors.add(tmp);
        Iterator<String> tm = mProcesors.keySet().iterator();
        for (; tm.hasNext();) {
            System.out.println("Regist: " + tm.next());
        }
    }

    @Override
    public synchronized void deAttach(String url) {
        Pair p = mProcesors.get(url);
        if (p != null) {
            procesors.remove(p);
            mProcesors.remove(url);
        }
    }

    @Override
    public synchronized String getOperation() {
        Pair ob = procesors.poll();
        if (ob != null) {
            change(ob.x, ob.y + 1);
        }
        String ret = ob.x;
        return ret;
    }

    public synchronized void change(String data, int va) {
        Pair tmp = new Pair(data, va);
        mProcesors.remove(data);
        mProcesors.put(data, tmp);
        procesors.add(tmp);
    }

    @Override
    public synchronized void removeProces(String url) {
        Pair tmp = mProcesors.get(url);
        procesors.remove(tmp);
        change(tmp.x, tmp.y - 1);
    }


}