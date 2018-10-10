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

    private List ListaPontoDesenhoServidor;
    private List ListaPontoApagarServidor;
    private final List MinhaListaPontoDesenho;
    private final List MinhaListaApagar;
    private final CommandListener command;

    /**
     * Responsável por desenhar no Canvas
     *
     * @param command interface de comunicação entre a GUI e a Canvas permite
     * enviar dados da GUI para o canvas e do CANVAS para a GUI.
     */
    public MyCanvas(CommandListener command) {
        /* inicializa os ArrayList de Point */
        this.ListaPontoDesenhoServidor = new ArrayList<>();
        this.ListaPontoApagarServidor = new ArrayList<>();
        this.MinhaListaPontoDesenho = new ArrayList<>();
        this.MinhaListaApagar = new ArrayList<>();

        int width = (int) ((SwingControlDemo) command).getPreferredSize().getWidth();
        int height = (int) ((SwingControlDemo) command).getPreferredSize().getHeight();

        this.setSize(new Dimension(width, height));
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        this.command = command;

    }

    @Override
    public void paint(Graphics g) {
        super.paintComponents(g);
        Graphics2D g2;
        g2 = (Graphics2D) g;

        /* clona a lista recebida do servidor */
        ListaPontoDesenhoServidor = (List) ((CopyOnWriteArrayList<Point>) command.getArrayPoint()).clone();
        ListaPontoApagarServidor = (List) ((CopyOnWriteArrayList<Point>) command.getArrayPointApagar()).clone();

        /* se a lista recebida do servidor estiver vazia ou for nula usa a lista
        preenchida com o desenho feito pelo usuário.
         */
        if (ListaPontoDesenhoServidor == null || ListaPontoDesenhoServidor.isEmpty()) {
            ListaPontoDesenhoServidor = MinhaListaPontoDesenho;
        }

        /* se a lista recebida do servidor estiver vazia ou for nula usa a lista
        preenchida com o desenho feito pelo usuário.
         */
        if (ListaPontoApagarServidor == null || ListaPontoApagarServidor.isEmpty()) {
            ListaPontoApagarServidor = MinhaListaApagar;
        }

        System.out.println("Pontos: " + ListaPontoDesenhoServidor.size());
        System.out.println("Apagar: " + ListaPontoApagarServidor.size());

        /* recuperou a lista de pontos do servidor, então, apaga a lista.
        * isso evita ficar concanentando novos pontos aos pontos já recebidos anteriormente. 
         */
        command.setClearArrayPoint();
        command.setClearArrayPointApagar();

        switch (command.getCommand()) {
            case CanvasConstants.DESENHO_LIVRE:
            case CanvasConstants.APAGAR:

                /* processa pontos para desenhar */
                Point temp[] = new Point[100000];
                ListaPontoDesenhoServidor.toArray(temp);
                for (int i = 0; i < ListaPontoDesenhoServidor.size() - 1; i++) {
                    g.setColor(command.getColor());
                    g.drawLine(temp[i].x, temp[i].y, temp[i + 1].x, temp[i + 1].y);
                    System.out.println(">" + temp[i].x + " " + temp[i].y);
                }

                /* processa pontos para apagar */
                Point temp_apagar[] = new Point[100000];
                ListaPontoApagarServidor.toArray(temp_apagar);
                for (int i = 0; i < ListaPontoApagarServidor.size() - 1; i++) {

                    g.clearRect(temp_apagar[i].x, temp_apagar[i].y, command.getSizeEraser(), command.getSizeEraser());
                }
                break;

            case CanvasConstants.ESCREVER:
                int x = ((Point) ListaPontoDesenhoServidor.get(ListaPontoDesenhoServidor.size())).x;
                int y = ((Point) ListaPontoDesenhoServidor.get(ListaPontoDesenhoServidor.size())).y;
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
            MinhaListaPontoDesenho.add(new Point(e.getX(), e.getY()));

        } else {
            setCursor(new Cursor(Cursor.HAND_CURSOR));
            MinhaListaApagar.add(new Point(e.getX(), e.getY()));
        }

        repaint();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        //MinhaListaPontoDesenho.add(new Point(e.getX(), e.getY()));
        // repaint();
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

        /* precisamos enviar os pontos armazaneados no ArrayList arrNew para a
           interface gráfica.
         */
        
        command.setArrayPoint(MinhaListaPontoDesenho);
        command.setArrayPointApagar(MinhaListaApagar);
        
        
        //limpa o arraylist
        MinhaListaPontoDesenho.clear();
        MinhaListaApagar.clear();

        //restaura o listener
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        repaint();

    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }

}
