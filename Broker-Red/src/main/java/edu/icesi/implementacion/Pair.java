package edu.icesi.implementacion;

/**
 * Pair
 */
public class Pair {

    public String x;
    public int y;
    public Pair(String x,int y){
        this.x=x;
        this.y=y;
    }
    public int compareTo(Pair o){
        return Integer.valueOf(y).compareTo(Integer.valueOf(o.y));
    }
}