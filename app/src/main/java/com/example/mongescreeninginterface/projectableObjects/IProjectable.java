package com.example.mongescreeninginterface.projectableObjects;

import com.example.mongescreeninginterface.helpers.PlaneOrientation;
import com.example.mongescreeninginterface.ui.PlotCanvasViewInfo;

public interface IProjectable<T, C> {
    T toMachineObject(PlotCanvasViewInfo plotCanvasViewInfo);
    T rotate(Point3d pointOfRotation, float angle, PlaneOrientation planeOrientation);
    C to2Screenings(PlotCanvasViewInfo plotCanvasViewInfo);
}
