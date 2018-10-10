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
    
    /* desenho livre */
    public List<Point> getArrayPoint();
    public void setClearArrayPoint();
    public void setArrayPoint(List<Point> list);
    
    
    /* apagar */
    public List<Point> getArrayPointApagar();
    public void setClearArrayPointApagar();
    public void setArrayPointApagar(List<Point> list);
    
    
}
