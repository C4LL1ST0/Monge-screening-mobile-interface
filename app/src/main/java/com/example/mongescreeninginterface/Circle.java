package com.example.mongescreeninginterface;

public class Circle extends GeometricObject{
    public Point3d center;
    public double radius;
    public Circle(String name, Point3d center, double radius){
        super(name);
        this.center = center;
        this.radius = radius;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Circle other = (Circle) obj;

        return Double.compare(radius, other.radius) == 0
                && java.util.Objects.equals(center, other.center);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(center, radius);
    }
}
