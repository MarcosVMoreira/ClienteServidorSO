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

    private List listaDesenhoServidor;
    private List listaApagarServidor;
    private int borracha;
    private final List listaDesenhoCliente;
    private final List listaApagarCliente;
    private final CommandListener command;
    private final MessageManager manager;

    public MyCanvas(CommandListener command, MessageManager manager) {
        this.listaDesenhoServidor = new ArrayList<>();
        this.listaDesenhoCliente = new ArrayList<>();
        this.listaApagarCliente = new ArrayList<>();
        this.listaApagarServidor = new ArrayList<>();

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

        /* clona a lista recebida do servidor */
        listaDesenhoServidor = (List) ((CopyOnWriteArrayList<Point>) command.getArrayPoint()).clone();
        listaApagarServidor = (List) ((CopyOnWriteArrayList<Point>) command.getArrayPointApagar()).clone();

        if (listaDesenhoServidor == null || listaDesenhoServidor.isEmpty()) {
            listaDesenhoServidor = listaDesenhoCliente;
        }

        if (listaApagarServidor == null || listaApagarServidor.isEmpty()) {
            listaApagarServidor = listaApagarCliente;
        }

        if (command.getSizeEraser() == -1) {
            borracha = command.getSizeEraser();
        } else {
            borracha = command.getSizeEraserFromServer();
        }

        command.restoreSizeEraser();

        System.out.println("BORRACHA: " + borracha);

        System.out.println("Pontos: " + listaDesenhoServidor.size());
        System.out.println("Apagar: " + listaApagarServidor.size());

        command.setClearArrayPoint();
        command.setClearArrayPointApagar();

        switch (command.getCommand()) {
            case CanvasConstants.DESENHO_LIVRE:
            case CanvasConstants.APAGAR:
                /* processa pontos para desenhar */
                Point temp[] = new Point[100000];
                listaDesenhoServidor.toArray(temp);
                for (int i = 0; i < listaDesenhoServidor.size() - 1; i++) {
                    g.setColor(command.getColor());
                    g.drawLine(temp[i].x, temp[i].y, temp[i + 1].x, temp[i + 1].y);
                    System.out.println(">" + temp[i].x + " " + temp[i].y);
                }

                /* processa pontos para apagar */
                Point temp_apagar[] = new Point[100000];
                listaApagarServidor.toArray(temp_apagar);
                System.out.println("VALOR BORRACHA DENTRO DO PAINT: "+borracha);
                if (borracha == -1) {
                    borracha = command.getSizeEraser();
                }
                for (int i = 0; i < listaApagarServidor.size() - 1; i++) {
                    g.clearRect(temp_apagar[i].x, temp_apagar[i].y, borracha, borracha);
                }
                break;
            case CanvasConstants.ESCREVER:
                int x = ((Point) listaDesenhoServidor.get(listaDesenhoServidor.size())).x;
                int y = ((Point) listaDesenhoServidor.get(listaDesenhoServidor.size())).y;
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
            listaDesenhoCliente.add(new Point(e.getX(), e.getY()));

        } else {
            setCursor(new Cursor(Cursor.HAND_CURSOR));
            listaApagarCliente.add(new Point(e.getX(), e.getY()));
        }

        repaint();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        listaDesenhoCliente.add(new Point(e.getX(), e.getY()));
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

        command.setArrayPoint(listaDesenhoCliente);
        command.setArrayPointApagar(listaApagarCliente);

        //limpa o arraylist
        listaDesenhoCliente.clear();
        listaApagarCliente.clear();

        //restaura o listener
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        repaint();

    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }

}
