/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.deitel.messenger;

import java.awt.Color;
import java.awt.Point;
import java.util.List;

/**
 *
 * @author avilapm
 */
public interface CommandListener {
    
    public int getCommand();
    public int getSizeEraser();
    public Color getColor();
    public String getMessage();
    
    public List<Point> getArrayPoint();
    public List<Point> getArrayPointApagar();
    public void setClearArrayPoint();
    public boolean getArrayPointIsReady();
    public void setArrayPoint(List<Point> list);
    public void setArrayPointApagar(List<Point> list);

    public void restoreColor();

    public void setClearArrayPointApagar();
    
}
