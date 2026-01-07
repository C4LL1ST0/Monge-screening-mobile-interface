package com.example.mongescreeninginterface.helpers;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.example.mongescreeninginterface.ui.PlotCanvasViewInfo;

public interface IDrawable {
    void drawSelf(Canvas canvas, PlotCanvasViewInfo plotCanvasViewInfo,
                  Paint pointPaint, Paint objectPaint);
}
