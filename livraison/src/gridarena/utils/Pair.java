package gridarena.utils;

/**
 * Représente un tuple de deux élements quelconques et immuables.
 * 
 * @author Florian Pépin
 * @version 1.0
 * @param <A> élement quelconque.
 * @param <B> élement quelconque.
 */
public class Pair<A, B> {
    
    private final A first;
    private final B second;
    
    /**
     * Construction d'un tuple de deux éléments quelconques et immuables.
     * 
     * @param first élement quelconque immuable.
     * @param second entier quelconque immuable.
     */
    public Pair(A first, B second) {
        this.first = first;
        this.second = second;
    }
    
    public A getFirst() {
        return this.first;
    }
    
    public B getSecond() {
        return this.second;
    }
    
    @Override
    public String toString() {
        return "("+this.first+", "+this.second+")";
    }
    
}
