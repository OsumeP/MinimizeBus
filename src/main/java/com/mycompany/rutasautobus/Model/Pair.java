/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.rutasautobus.Model;
 
// Pair class
class Pair<U, V extends Comparable<V>>{
    private final U first;
    private final V second;

    Pair(U first, V second)
    {
        this.first = first;
        this.second = second;
    }
    
    public U getFirst(){
        return this.first;
    }
    
    public V getSecond(){
        return this.second;
    }
 
    @Override
    public boolean equals(Object o)
    {
        if (this == o) {
            return true;
        }
 
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
 
        Pair<?, ?> pair = (Pair<?, ?>) o;
 
        if (!first.equals(pair.first)) {
            return false;
        }
        return second.equals(pair.second);
    }
 
    @Override
    public int hashCode()
    {
        return 31 * first.hashCode() + second.hashCode();
    }
 
    @Override
    public String toString() {
        return "(" + first + ", " + second + ")";
    }
}
