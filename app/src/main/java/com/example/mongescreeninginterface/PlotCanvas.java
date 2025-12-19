package com.example.mongescreeninginterface;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.Pair;
import android.view.View;

import androidx.annotation.NonNull;

import java.util.Objects;

public class PlotCanvas extends View {
    private final Paint objectPaint;
    private final Paint pointPaint;
    private final Paint axisPaint;

    private PlotCanvasViewInfo plotCanvasViewInfo;
    private DrawModel drawModel;

    public PlotCanvas(Context context){
        super(context);

        objectPaint = new Paint();
        objectPaint.setColor(Color.WHITE);
        objectPaint.setAntiAlias(true);
        objectPaint.setTextSize(24);
        objectPaint.setStrokeWidth(5);

        axisPaint = new Paint(objectPaint);
        axisPaint.setStrokeWidth(3);
        axisPaint.setPathEffect(new DashPathEffect(new float[]{5,10}, 2));

        pointPaint = new Paint();
        pointPaint.setColor(Color.YELLOW);
        pointPaint.setAntiAlias(true);
        pointPaint.setTextSize(24);
        pointPaint.setStrokeWidth(10);
    }

    public void setDrawModel(DrawModel drawModel){
        this.drawModel = drawModel;
        invalidate();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh){
        super.onSizeChanged(w, h, oldw, oldh);
        plotCanvasViewInfo = new PlotCanvasViewInfo(w, h, new PointF((w /2f), (h /2f)), 30, 23, 10);
        invalidate();
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);

        //axis
        //canvas.drawLine(plotCanvasViewInfo.centerPoint.x, 0, plotCanvasViewInfo.centerPoint.x, plotCanvasViewInfo.canvasHeight, axisPaint);
        canvas.drawLine(0, plotCanvasViewInfo.centerPoint.y, plotCanvasViewInfo.canvasWidth, plotCanvasViewInfo.centerPoint.y, axisPaint);

        if(drawModel != null){
            for(var pt : drawModel.getPointsToDraw()){
                drawPoint3d(canvas, pt);
            }

            for(var seg : drawModel.getSegmentsToDraw()){
                drawLine(canvas, seg);
            }
        }
    }

    public void drawPoint3d(Canvas canvas, Point3d point){
        var pointM = point.toMachineObject(plotCanvasViewInfo);

        canvas.drawPoint(pointM.x, pointM.y, pointPaint);
        canvas.drawPoint(pointM.x, pointM.z, pointPaint);

        if(!Objects.equals(point.name, "")){
            canvas.drawText("[" + pointM.name + "]1", pointM.x-plotCanvasViewInfo.nameOffset, pointM.y-plotCanvasViewInfo.nameOffset, pointPaint);
            canvas.drawText("[" + pointM.name + "]2", pointM.x-plotCanvasViewInfo.nameOffset, pointM.z-plotCanvasViewInfo.nameOffset, pointPaint);
        }
    }

    public void drawLine(Canvas canvas, Line line){
        Pair<PointF, PointF> bothFSScreenings;
        PointF fs1 = null;
        PointF fs2 = null;

        if(line.floorStopper != null){
            bothFSScreenings = line.floorStopper.to2Screenings(plotCanvasViewInfo);
            fs1 = bothFSScreenings.first;
            fs2 = bothFSScreenings.second;
        }

        Pair<PointF, PointF> bothPSScreenings;
        PointF ps1 = null;
        PointF ps2 = null;

        if(line.profileStopper != null){
            bothPSScreenings = line.profileStopper.to2Screenings(plotCanvasViewInfo);
            ps1 = bothPSScreenings.first;
            ps2 = bothPSScreenings.second;
        }

        var bothStartScreenings = line.startPoint.to2Screenings(plotCanvasViewInfo);
        var s1 = bothStartScreenings.first;
        var s2 = bothStartScreenings.second;

        var bothEndScreenings = line.endPoint.to2Screenings(plotCanvasViewInfo);
        var e1 = bothEndScreenings.first;
        var e2 = bothEndScreenings.second;

        if(line.floorStopper != null && line.profileStopper != null){
            if(line.floorStopper.distanceTo(line.profileStopper) != 0){
                canvas.drawLine(ps1.x, ps1.y, fs1.x, fs1.y, objectPaint);
                canvas.drawLine(ps2.x, ps2.y, fs2.x, fs2.y, objectPaint);
            }else {
                var bothFurthestPointScreenings = line.floorStopper.getFurtherPoint(line.startPoint, line.endPoint).to2Screenings(plotCanvasViewInfo);
                var furthest1 = bothFurthestPointScreenings.first;
                var furthest2 = bothFurthestPointScreenings.second;

                canvas.drawLine(furthest1.x, furthest1.y, fs1.x, fs1.y, objectPaint);
                canvas.drawLine(furthest2.x, furthest2.y, fs2.x, fs2.y, objectPaint);
            }

        }else if(line.floorStopper != null){
            var bothFurthestPointScreenings = line.floorStopper.getFurtherPoint(line.startPoint, line.endPoint).to2Screenings(plotCanvasViewInfo);
            var furthest1 = bothFurthestPointScreenings.first;
            var furthest2 = bothFurthestPointScreenings.second;

            canvas.drawLine(furthest1.x, furthest1.y, fs1.x, fs1.y, objectPaint);
            canvas.drawLine(furthest2.x, furthest2.y, fs2.x, fs2.y, objectPaint);

        }else if(line.profileStopper != null){
            var bothFurthestPointScreenings = line.profileStopper.getFurtherPoint(line.startPoint, line.endPoint).to2Screenings(plotCanvasViewInfo);
            var furthest1 = bothFurthestPointScreenings.first;
            var furthest2 = bothFurthestPointScreenings.second;

            canvas.drawLine(furthest1.x, furthest1.y, ps1.x, ps1.y, objectPaint);
            canvas.drawLine(furthest2.x, furthest2.y, ps2.x, ps2.y, objectPaint);

        }else {
            canvas.drawLine(s1.x, s1.y, e1.x, e1.y, objectPaint);
            canvas.drawLine(s2.x, s2.y, e2.x, e2.y, objectPaint);
        }

        if(!Objects.equals(line.name, "")){
            canvas.drawText(line.name + "1", e1.x+plotCanvasViewInfo.nameOffset, e1.y+plotCanvasViewInfo.nameOffset, pointPaint);
            canvas.drawText(line.name + "2", e2.x+plotCanvasViewInfo.nameOffset, e2.y+plotCanvasViewInfo.nameOffset, pointPaint);
        }
    }
}
