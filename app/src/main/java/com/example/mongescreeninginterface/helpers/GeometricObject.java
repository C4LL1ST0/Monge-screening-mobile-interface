package com.example.mongescreeninginterface.helpers;

public abstract class GeometricObject {
    public String name;
    public GeometricObject(String name){
        this.name = name;
    }

    @Override
    public abstract boolean equals(Object obj);

    @Override
    public abstract int hashCode();
}
