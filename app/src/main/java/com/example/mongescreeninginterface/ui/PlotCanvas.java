package com.example.mongescreeninginterface.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;

import com.example.mongescreeninginterface.drawable3d.Object3d;
import com.example.mongescreeninginterface.helpers.LineLike;
import com.example.mongescreeninginterface.projectableObjects.Point3d;
import com.example.mongescreeninginterface.helpers.line.LineBothScrs;

import java.util.Objects;

public class PlotCanvas extends View implements DrawModelListener {
    private Paint objectPaint;
    private Paint pointPaint;
    private Paint axisPaint;

    private PlotCanvasViewInfo plotCanvasViewInfo;
    private DrawModel drawModel;

    public PlotCanvas(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PlotCanvas(Context context){
        super(context);
        init();
    }

    private void init(){
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

        this.drawModel = DrawModel.getInstance();
        this.drawModel.drawModelListener = this;
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
        if(plotCanvasViewInfo == null) return;
        super.onDraw(canvas);

        //axis
        //canvas.drawLine(plotCanvasViewInfo.centerPoint.x, 0, plotCanvasViewInfo.centerPoint.x, plotCanvasViewInfo.canvasHeight, axisPaint);
        canvas.drawLine(0, plotCanvasViewInfo.centerPoint.y, plotCanvasViewInfo.canvasWidth, plotCanvasViewInfo.centerPoint.y, axisPaint);

        if(drawModel != null){
            for(var pt : drawModel.getPointsToDraw()){
                drawPoint3d(canvas, pt);
            }

            for(var line : drawModel.getLinesToDraw()){
                drawLine(canvas, line);
            }

            for(var object : drawModel.getObject3dsToDraw()){
                drawObject3d(canvas, object);
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

    public void drawLine(Canvas canvas, LineLike line){
        var bothLineScrs = (LineBothScrs) line.to2Screenings(plotCanvasViewInfo);

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

        if(!Objects.equals(line.name, "")){
            canvas.drawText(line.name + "1", bothLineScrs.floorScr().end().x()+plotCanvasViewInfo.nameOffset,
                    bothLineScrs.floorScr().end().y()+plotCanvasViewInfo.nameOffset, pointPaint);
            canvas.drawText(line.name + "2", bothLineScrs.profileScr().end().x()+plotCanvasViewInfo.nameOffset,
                    bothLineScrs.profileScr().end().z()+plotCanvasViewInfo.nameOffset, pointPaint);
        }
    }

    public void drawObject3d(Canvas canvas, Object3d object3d){
        for (var edge : object3d.getEdges()){
            drawLine(canvas, edge);
        }
        for(var pt : object3d.getPoints()){
            drawPoint3d(canvas, pt);
        }
    }

    @Override
    public void onModelChanged() {
        invalidate();
    }
}
