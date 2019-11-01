import ey.TJob;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;

public class Main extends JFrame {
    private static boolean isRun = true;
    private static LinkedList<TJob> tJobs;
    private ServerSocket server;
    private Robot robot;

    public static void main(String[] args) {
        new Main("Handle");
    }

    public Main(String title) throws HeadlessException {
        super(title);
        JFrame jf = new JFrame(title);
        Container conn = jf.getContentPane();
        JLabel L1 = new JLabel("Hello,world!");
        conn.add(L1);
        jf.setBounds(200, 200, 600, 500);
        jf.setVisible(true);
        jf.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        jf.addWindowListener(new WindowListener() {

            @Override
            public void windowOpened(WindowEvent e) {
                try {
                    tJobs = new LinkedList<>();
                    robot = new Robot();//创建Robot对象
                    server = new ServerSocket(5366);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                while (isRun) {
                                    Socket s = null;
                                    s = server.accept();
                                    TJob tJob = new TJob(robot, s);
                                    tJobs.add(tJob);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }

            @Override
            public void windowClosing(WindowEvent e) {
                for (TJob tJob : tJobs) {
                    tJob.stop();
                }
                tJobs.clear();
                isRun = false;
                try {
                    server.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            @Override
            public void windowClosed(WindowEvent e) {

            }

            @Override
            public void windowIconified(WindowEvent e) {

            }

            @Override
            public void windowDeiconified(WindowEvent e) {

            }

            @Override
            public void windowActivated(WindowEvent e) {

            }

            @Override
            public void windowDeactivated(WindowEvent e) {

            }
        });
    }
}
