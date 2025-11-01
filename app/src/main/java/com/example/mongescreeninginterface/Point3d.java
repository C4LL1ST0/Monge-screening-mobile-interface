package com.example.mongescreeninginterface;

import android.graphics.PointF;
import android.util.Pair;

public class Point3d extends DrawableObject<Point3d>{
    public float x;
    public float y;
    public float z;
    
    public Point3d(String name, float x, float y, float z){
        this.name = name;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public Point3d toMachineObject(PlotCanvasViewInfo plotCanvasViewInfo) {
        var machineX = plotCanvasViewInfo.centerPoint.x + plotCanvasViewInfo.getWidthSegmentSize() * this.x;
        var machineY = plotCanvasViewInfo.centerPoint.y + plotCanvasViewInfo.getHeightSegmentSize() * this.y;
        var machineZ = plotCanvasViewInfo.centerPoint.y - plotCanvasViewInfo.getHeightSegmentSize() * this.z;

        return new Point3d(name, machineX, machineY, machineZ);
    }

    public Pair<PointF, PointF> to2Screenings(PlotCanvasViewInfo plotCanvasViewInfo){
        var point = toMachineObject(plotCanvasViewInfo);

        var p1 = new PointF(point.x, point.y);
        var p2 = new PointF(point.x, point.z);

        return new Pair<PointF, PointF>(p1, p2);
    }
}
