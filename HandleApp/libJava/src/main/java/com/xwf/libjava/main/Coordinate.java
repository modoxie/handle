package com.xwf.libjava.main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.Point;
import java.awt.Robot;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.net.ServerSocket;
import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class Coordinate extends JFrame {

    public static void main(String[] args) {
        new Coordinate("Coordinate");
    }

    public Coordinate(String title) throws HeadlessException {
        super(title);
        JFrame jf = new JFrame(title);
        jf.setBounds(200, 200, 100, 90);
        jf.setVisible(true);
        jf.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        jf.setAlwaysOnTop(true);
        Container conn = jf.getContentPane();
        JPanel contentPanel = new JPanel();
        conn.setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        conn.add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(null);


        JLabel value_x = new JLabel("0");
        value_x.setForeground(Color.BLUE);
        value_x.setFont(new Font("宋体", Font.PLAIN, 20));
        value_x.setBounds(22, 0, 46, 31);
        contentPanel.add(value_x);

        JLabel value_y = new JLabel("0");
        value_y.setForeground(Color.BLUE);
        value_y.setFont(new Font("宋体", Font.PLAIN, 20));
        value_y.setBounds(82, 0, 66, 31);
        contentPanel.add(value_y);
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Point point = java.awt.MouseInfo.getPointerInfo().getLocation();
                // System.out.println("Location:x=" + point.x + ", y=" +
                // point.y);
                value_x.setText("" + point.x);
                value_y.setText("" + point.y);
            }
        }, 100, 100);
        jf.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent windowEvent) {

            }

            @Override
            public void windowClosing(WindowEvent windowEvent) {

            }

            @Override
            public void windowClosed(WindowEvent windowEvent) {
                timer.cancel();
            }

            @Override
            public void windowIconified(WindowEvent windowEvent) {

            }

            @Override
            public void windowDeiconified(WindowEvent windowEvent) {

            }

            @Override
            public void windowActivated(WindowEvent windowEvent) {

            }

            @Override
            public void windowDeactivated(WindowEvent windowEvent) {

            }
        });
    }
}
