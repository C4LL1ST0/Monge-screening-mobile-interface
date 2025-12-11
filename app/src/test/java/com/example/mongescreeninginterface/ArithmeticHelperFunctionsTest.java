package com.example.mongescreeninginterface;

import org.junit.Test;

import static org.junit.Assert.*;


public class ArithmeticHelperFunctionsTest {
    @Test
    public void should_findCirclesInterSections_When_CirclesCross_AndSameZ(){
        var c1 = new Circle("a", new Point3d("S", 0,0,0), 5);
        var c2 = new Circle("a", new Point3d("S", 4,0,0), 5);

        var intersections = ArithmeticHelperFunctions.findIntersection(c1, c2);
        assertEquals(2, intersections[0].x, 0);
        assertEquals(2, intersections[1].x, 0);
        assertEquals(-4.58d, intersections[0].y, 0.01);
        assertEquals(4.58d, intersections[1].y, 0.01);
    }

    @Test
    public void should_ReturnNull_When_CirclesDoesNotCross(){
        var c1 = new Circle("a", new Point3d("S", 0,0,0), 2);
        var c2 = new Circle("a", new Point3d("S", 8,0,0), 2);

        var intersections = ArithmeticHelperFunctions.findIntersection(c1, c2);
        assertNull(intersections);
    }

    @Test
    public void should_Return2EqualPoints_When_CirclesTouch(){
        var c1 = new Circle("a", new Point3d("S", 0,0,0), 4);
        var c2 = new Circle("a", new Point3d("S", 8,0,0), 4);

        var intersections = ArithmeticHelperFunctions.findIntersection(c1, c2);
        assertEquals(4, intersections[0].x, 0);
        assertEquals(4, intersections[1].x, 0);
        assertEquals(0, intersections[0].y, 0);
        assertEquals(0, intersections[1].y, 0);
    }

    @Test
    public void should_ReturnNull_When_CirclesAreEqual(){
        var c1 = new Circle("a", new Point3d("S", 0,0,0), 4);
        var c2 = new Circle("a", new Point3d("S", 0,0,0), 4);
        assertTrue(c1.equals(c2));

        var intersections = ArithmeticHelperFunctions.findIntersection(c1, c2);
        assertNull(intersections);
    }

    @Test
    public void should_getTriangleBaseLengthByCosTheorem_When_AngleIs90(){
        var length = ArithmeticHelperFunctions.cosTheoremIsosceles(2, 90);
        assertEquals(2.82, length, 0.01);
    }

    @Test
    public void should_getTriangleBaseLengthByCosTheorem_When_AngleNot90(){
        var length = ArithmeticHelperFunctions.cosTheoremIsosceles(2, 60);
        assertEquals(2, length, 0.0001);
    }
}