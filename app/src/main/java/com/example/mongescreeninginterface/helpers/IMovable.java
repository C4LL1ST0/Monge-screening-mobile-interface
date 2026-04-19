package com.example.mongescreeninginterface.helpers;

public interface IMovable<T extends  IMovable<T>> extends IManipulatable{
    T move(float distance, PlaneOrientation planeOrientation);
}
