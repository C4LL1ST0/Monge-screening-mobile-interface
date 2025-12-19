package com.example.mongescreeninginterface;

import org.junit.Test;
import static org.junit.Assert.*;

import com.example.mongescreeninginterface.drawableObjects.Point3d;
import com.example.mongescreeninginterface.helpers.PlaneOrientation;


public class Point3dTest {
    @Test
    public void should_RotatePointBy90deg_When_PlaneOrientationXY_And_SameLevel(){
        var p = new Point3d("P", 2, 0, 1);
        var s = new Point3d("S", 0,0,1);

        var rotated = p.rotate(s, 90, PlaneOrientation.XY);
        assertEquals(0, rotated.x, 0.001);
        assertEquals(2, rotated.y, 0.001);
        assertEquals(1, rotated.z, 0.001);
    }
    @Test
    public void should_RotatePointBy90deg_When_PlaneOrientationXY_And_DiffLevel(){
        var p = new Point3d("P", 2, 0, 5);
        var s = new Point3d("S", 0,0,0);

        var rotated = p.rotate(s, 90, PlaneOrientation.XY);
        assertEquals(0, rotated.x, 0.001);
        assertEquals(2, rotated.y, 0.001);
        assertEquals(5, rotated.z, 0.001);
    }


    @Test
    public void should_RotatePointBy90deg_When_PlaneOrientationXZ_And_SameLevel(){
        var p = new Point3d("P", 2, 1, 0);
        var s = new Point3d("S", 0,1,0);

        var rotated = p.rotate(s, 90, PlaneOrientation.XZ);
        assertEquals(0, rotated.x, 0.001);
        assertEquals(1, rotated.y, 0.001);
        assertEquals(2, rotated.z, 0.001);
    }

    @Test
    public void should_RotatePointBy90deg_When_PlaneOrientationXZ_And_DiffLevel(){
        var p = new Point3d("P", 2, 1, 0);
        var s = new Point3d("S", 0,80,0);

        var rotated = p.rotate(s, 90, PlaneOrientation.XZ);
        assertEquals(0, rotated.x, 0.001);
        assertEquals(1, rotated.y, 0.001);
        assertEquals(2, rotated.z, 0.001);
    }


    @Test
    public void should_RotatePointBy90deg_When_PlaneOrientationYZ_And_SameLevel(){
        var p = new Point3d("P", 0, 3, 0);
        var s = new Point3d("S", 0,0,0);

        var rotated = p.rotate(s, 90, PlaneOrientation.YZ);
        assertEquals(0, rotated.x, 0.001);
        assertEquals(0, rotated.y, 0.001);
        assertEquals(3, rotated.z, 0.001);
    }

    @Test
    public void should_RotatePointBy90deg_When_PlaneOrientationYZ_And_DiffLevel(){
        var p = new Point3d("P", 15, 3, 0);
        var s = new Point3d("S", 0,0,0);

        var rotated = p.rotate(s, 90, PlaneOrientation.YZ);
        assertEquals(15, rotated.x, 0.001);
        assertEquals(0, rotated.y, 0.001);
        assertEquals(3, rotated.z, 0.001);
    }
}
