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
            for(var object : drawModel.getObjectsToDraw()){
                object.drawSelf(canvas, plotCanvasViewInfo, pointPaint, objectPaint);
            }
        }
    }

    @Override
    public void onModelChanged() {
        invalidate();
    }
}
