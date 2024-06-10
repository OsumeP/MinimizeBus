/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.rutasautobus.Model;

/**
 *
 * @author Juand
 */
public class Trio<U, V, W> {
    private final U first;
    private final V second;
    private final W third;

    Trio(U first, V second, W third)
    {
        this.first = first;
        this.second = second;
        this.third = third;
    }
    public U getFirst(){
        return this.first;
    }
    
    public V getSecond(){
        return this.second;
    }
 
    public W getThird(){
        return this.third;
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
 
        Trio<?, ?, ?> trio = (Trio<?, ?, ?>) o;
 
        if (!first.equals(trio.first)) {
            return false;
        }
        if (!second.equals(trio.second)) {
            return false;
        }
        return third.equals(trio.third);
    }
 
    @Override
    public int hashCode()
    {
        return 31 * first.hashCode() + second.hashCode() + third.hashCode();
    }
 
    @Override
    public String toString() {
        return "(" + first + ", " + second + ")";
    }
 
    public static <U, V, W> Trio <U, V, W> of(U a, V b, W c)
    {
        return new Trio<>(a, b, c);
    }
}
