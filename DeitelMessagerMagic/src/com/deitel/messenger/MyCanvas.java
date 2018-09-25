package com.deitel.messenger;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.swing.JPanel;

public class MyCanvas extends JPanel implements MouseListener, MouseMotionListener {

    private List arr;
    private final List arrNew;
    private final List apagar;
    private final CommandListener command;
    private final MessageManager manager;

    public MyCanvas(CommandListener command, MessageManager manager) {
        this.arr = new ArrayList<>();
        this.arrNew = new ArrayList<>();
        this.apagar = new ArrayList<>();
        
        System.out.println(((SwingControlDemo) command).getPreferredSize());
        int width = (int) ((SwingControlDemo) command).getPreferredSize().getWidth();
        int height = (int) ((SwingControlDemo) command).getPreferredSize().getHeight();
        this.setSize(new Dimension(width, height));
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        this.command = command;
        this.manager = manager;

    }

    @Override
    public void paint(Graphics g) {
        super.paintComponents(g);
        Graphics2D g2;
        g2 = (Graphics2D) g;

        /* clona a lista */
        arr = (List) ((CopyOnWriteArrayList<Point>)command.getArrayPoint()).clone();
       
       
       if(arr == null || arr.isEmpty()){
           arr = arrNew;
       }
       
       System.out.println("Array Point is ready: " + arr.size());
       command.setClearArrayPoint();
 
        switch (command.getCommand()) {
            case CanvasConstants.DESENHO_LIVRE:
                Point temp[] = new Point[100000];
                arr.toArray(temp);
                for (int i = 0; i < arr.size() - 1; i++) {
                    g.setColor(command.getColor());
                    g.drawLine(temp[i].x, temp[i].y, temp[i + 1].x, temp[i + 1].y);
                    System.out.println(">" + temp[i].x + " " + temp[i].y);
                }
                break;
            case CanvasConstants.APAGAR:
                for (int i = 0; i < apagar.size() - 1; i++) {
                    Point temp_apagar[] = new Point[100000];
                    apagar.toArray(temp_apagar);
                    g.clearRect(temp_apagar[i].x, temp_apagar[i].y, command.getSizeEraser(), command.getSizeEraser());
                }
                break;
            case CanvasConstants.ESCREVER:
                int x = ((Point) arr.get(arr.size())).x;
                int y = ((Point) arr.get(arr.size())).y;
                g2.drawString(command.getMessage(), x, y);

                break;
            default:
                break;
        }
     
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (command.getCommand() == CanvasConstants.DESENHO_LIVRE || command.getCommand() == CanvasConstants.ESCREVER) {
            setCursor(Cursor.getDefaultCursor());
            arrNew.add(new Point(e.getX(), e.getY()));

        } else {
            setCursor(new Cursor(Cursor.HAND_CURSOR));
            apagar.add(new Point(e.getX(), e.getY()));
        }

        repaint();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        arrNew.add(new Point(e.getX(), e.getY()));
         repaint();
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
       
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {

        this.removeMouseListener(this);
        this.removeMouseMotionListener(this);

        System.out.println("Send Array points from mouse Release");
        command.setArrayPoint(arrNew);

        //limpa o arraylist
        arrNew.clear();
        apagar.clear();
       
 
        //restaura o listener
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        repaint();

    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }

}
