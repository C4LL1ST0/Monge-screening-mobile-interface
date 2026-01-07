package com.example.mongescreeninginterface.helpers;

import android.graphics.Canvas;
import android.graphics.Paint;

import androidx.annotation.Nullable;

import com.example.mongescreeninginterface.projectableObjects.IProjectable;
import com.example.mongescreeninginterface.projectableObjects.Point3d;
import com.example.mongescreeninginterface.helpers.line.LineBothScrs;
import com.example.mongescreeninginterface.ui.PlotCanvasViewInfo;

import java.util.Objects;

public abstract class LineLike<T extends LineLike<T>> extends GeometricObject
        implements IProjectable<T, LineBothScrs>, IDrawable, IRotable<LineLike<?>> {
    public Point3d firstPoint;
    public Point3d secondPoint;
    @Nullable
    public Point3d floorStopper;
    @Nullable
    public Point3d profileStopper;
    public Vector3d directionVector;
    protected Point3d pointOfRotation;
    public Point3d getPointOfRotation() {
        return pointOfRotation;
    }
    public void setPointOfRotation(Point3d pointOfRotation) {
        this.pointOfRotation = pointOfRotation;
    }

    public LineLike(String name) {
        super(name);
    }

    @Override
    public void drawSelf(Canvas canvas, PlotCanvasViewInfo plotCanvasViewInfo,
                         Paint pointPaint, Paint objectPaint) {
        var bothLineScrs = (LineBothScrs) this.to2Screenings(plotCanvasViewInfo);

        canvas.drawLine(bothLineScrs.floorScr().start().x(),
                bothLineScrs.floorScr().start().y(),
                bothLineScrs.floorScr().end().x(),
                bothLineScrs.floorScr().end().y(),
                objectPaint);

        canvas.drawLine(bothLineScrs.profileScr().start().x(),
                bothLineScrs.profileScr().start().z(),
                bothLineScrs.profileScr().end().x(),
                bothLineScrs.profileScr().end().z(),
                objectPaint);

        if(!Objects.equals(this.name, "")){
            canvas.drawText(this.name + "1", bothLineScrs.floorScr().end().x()+plotCanvasViewInfo.nameOffset,
                    bothLineScrs.floorScr().end().y()+plotCanvasViewInfo.nameOffset, pointPaint);
            canvas.drawText(this.name + "2", bothLineScrs.profileScr().end().x()+plotCanvasViewInfo.nameOffset,
                    bothLineScrs.profileScr().end().z()+plotCanvasViewInfo.nameOffset, pointPaint);
        }
    }
}
