package edu.icesi.implementacion;

import java.rmi.*;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.PriorityQueue;
import edu.icesi.interfaces.IBroker;
import java.lang.*;

/**
 * Broker
 */

public class Broker implements IBroker {

    private static PriorityQueue<Pair> procesors=new PriorityQueue<Pair>(20,new Comparator<Pair>() {

        public int compare(Pair o1, Pair o2) {
            return o1.compareTo(o2);
        }
    });
    private static HashMap<String, Pair> mProcesors=new HashMap<String, Pair>();

    public Broker() {
        System.out.println("Broker");
        
    }
    
    @Override
    public synchronized void attach(String url) {
        Pair tmp = new Pair(url, 0);
        mProcesors.put(url, tmp);
        procesors.add(tmp);
        System.out.println("size q-> "+procesors.size());
        System.out.println("size h -> "+mProcesors.keySet().size());
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
        System.out.println("Entro");
        Pair ob=procesors.poll();
        System.out.println("Point --> "+ ob);

        if (ob != null) {
            System.out.println("not null");
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