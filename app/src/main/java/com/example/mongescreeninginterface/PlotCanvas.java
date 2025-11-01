package com.example.mongescreeninginterface;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.PointF;
import android.view.View;

import androidx.annotation.NonNull;

public class PlotCanvas extends View {
    private Paint objectPaint;
    private Paint pointPaint;

    private Paint axisPaint;

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
        plotCanvasViewInfo = new PlotCanvasViewInfo(w, h, new PointF((w /2f), (h /2f)), 18, 20, 10);
        invalidate();
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);

        //axis
        canvas.drawLine(plotCanvasViewInfo.centerPoint.x, 0, plotCanvasViewInfo.centerPoint.x, plotCanvasViewInfo.canvasHeight, axisPaint);
        canvas.drawLine(0, plotCanvasViewInfo.centerPoint.y, plotCanvasViewInfo.canvasWidth, plotCanvasViewInfo.centerPoint.y, axisPaint);

        if(drawModel != null){
            for(var object : drawModel.getObjectsToDraw()){

                if(object instanceof Point3d){
                    drawPoint3d(canvas, (Point3d) object);
                }else if(object instanceof Segment){
                    drawSegment(canvas, (Segment) object);
                }

            }
        }
    }

    public void drawPoint3d(Canvas canvas, Point3d point){
        point = point.toMachineObject(plotCanvasViewInfo);

        canvas.drawPoint(point.x, point.y, pointPaint);
        canvas.drawText(point.name + "1", point.x+plotCanvasViewInfo.nameOffset, point.y-plotCanvasViewInfo.nameOffset, pointPaint);

        canvas.drawPoint(point.x, point.z, pointPaint);
        canvas.drawText(point.name + "2", point.x+plotCanvasViewInfo.nameOffset, point.z-plotCanvasViewInfo.nameOffset, pointPaint);
    }

    public void drawSegment(Canvas canvas, Segment segment){
        drawPoint3d(canvas, segment.startPoint);
        drawPoint3d(canvas, segment.endPoint);

        var bothStartScreenings = segment.startPoint.to2Screenings(plotCanvasViewInfo);
        var s1 = bothStartScreenings.first;
        var s2 = bothStartScreenings.second;

        var bothEndScreenings = segment.endPoint.to2Screenings(plotCanvasViewInfo);
        var e1 = bothEndScreenings.first;
        var e2 = bothEndScreenings.second;

        canvas.drawLine(s1.x, s1.y, e1.x, e1.y, objectPaint);
        canvas.drawText(segment.name + "1", e1.x+plotCanvasViewInfo.nameOffset, e1.y+plotCanvasViewInfo.nameOffset, pointPaint);

        canvas.drawLine(s2.x, s2.y, e2.x, e2.y, objectPaint);
        canvas.drawText(segment.name + "2", e2.x+plotCanvasViewInfo.nameOffset, e2.y+plotCanvasViewInfo.nameOffset, pointPaint);
    }
}
