package com.example.mongescreeninginterface.ui;

import android.graphics.PointF;

public class PlotCanvasViewInfo {

    public float canvasWidth;
    public float canvasHeight;
    public PointF centerPoint;
    public int nameOffset;
    private int heightSegmentsCount;
    private int widthSegmentsCount;
    private float heightSegmentSize;
    private float widthSegmentSize;

    public PlotCanvasViewInfo(float canvasWidth, float canvasHeight, PointF centerPoint, int nameOffset, int heightSegmentsCount, int widthSegmentsCount) {
        this.canvasWidth = canvasWidth;
        this.canvasHeight = canvasHeight;
        this.centerPoint = centerPoint;
        this.nameOffset = nameOffset;
        setHeightSegmentsCount(heightSegmentsCount);
        setWidthSegmentsCount(widthSegmentsCount);
    }

    public int getHeightSegmentsCount() {
        return heightSegmentsCount;
    }

    public void setHeightSegmentsCount(int heightSegmentsCount) {
        this.heightSegmentsCount = heightSegmentsCount;
        this.heightSegmentSize = canvasHeight / heightSegmentsCount;
    }

    public int getWidthSegmentsCount() {
        return widthSegmentsCount;
    }

    public void setWidthSegmentsCount(int widthSegmentsCount) {
        this.widthSegmentsCount = widthSegmentsCount;
        this.widthSegmentSize = canvasWidth / widthSegmentsCount;
    }

    public float getHeightSegmentSize() {
        return heightSegmentSize;
    }

    public float getWidthSegmentSize() {
        return widthSegmentSize;
    }
}