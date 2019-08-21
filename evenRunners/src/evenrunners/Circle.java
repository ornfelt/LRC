package evenrunners;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import static javax.swing.JOptionPane.*;

/**
 *
 * @author Jonas Örnfelt
 */
public class Circle extends JPanel implements ActionListener {

    boolean backgroundBlack = false;
    boolean calcDone = false;
    double angle, angle1, angle2, angle3, angle4, angle5, angle6, angle7;
    double vel, vel1, vel2, vel3, vel4, vel5, vel6, vel7;
    double pTime, pTemp, pTime1, pTime2, pTime3, pTime4, pTime5;
    double[] vels, velsTest;
    static long startTime, endTime, elapsedTime;
    double per, per1, per2, per3, per4, per5;
    int pCount = 2;
    int greenRunner;
    boolean runnerLonely, lonelyDone;
    static Timer timer;
    LonelyRunner lrc = new LonelyRunner();

    Circle(double cV[], ArrayList aniResults, int gRunner, boolean nLonely) {
        super(null);

        lonelyDone = false;
        vel = (cV[0]) * LonelyRunner.aniConstant;
        vel1 = (cV[1]) * LonelyRunner.aniConstant;
        try {

            vel2 = (cV[2]) * LonelyRunner.aniConstant;
            vel3 = (cV[3]) * LonelyRunner.aniConstant;
            vel4 = (cV[4]) * LonelyRunner.aniConstant;
            vel5 = (cV[5]) * LonelyRunner.aniConstant;
            vel6 = (cV[6]) * LonelyRunner.aniConstant;
            vel7 = (cV[7]) * LonelyRunner.aniConstant;
        } catch (Exception e) {
        }

        vels = new double[cV.length];
        for (int i = 0; i < cV.length; i++) {
            vels[i] = cV[i];
        }

        for (int i = 0; i < vels.length; i++) {
            vels[i] *= LonelyRunner.aniConstant;
        }

        for (int i = 0; i < aniResults.size(); i++) {
            per = (double) aniResults.get(i);

            if (per != LonelyRunner.seperate) {
                pTemp = (per / Math.abs(cV[0] - cV[i + 1])) * 10000;
            }
            if (pTime == 0) {
                pTime = pTemp;
            }
            if (pTemp < pTime) {
                pTime = pTemp;
            }
//            System.out.println("pTemp is currently set to: " + pTemp + ", because of (cV[0] - cV[i+1]): " + cV[0] + " - " + cV[i + 1]);
        }
        greenRunner = gRunner;
        runnerLonely = nLonely;
        timer = new Timer(10, this);
        timer.start();

//        System.out.println("vels are: " + vel + vel1 + vel2 + vel3 + vel4 + vel5 + vel6 + vel7 + ". pTime equals: " + pTime + "ms.");
    }

    public void actionPerformed(ActionEvent e) {

        endTime = System.currentTimeMillis();
        elapsedTime = (endTime - startTime);

        if (elapsedTime >= pTime && !calcDone) {
//            A limitation caused by implementing so that the program prints in the console if a runner is never lonely. This leads to a small limitation of the programs functioning, because the velocities can't be too big for this to work properly.

//            (elapsedTime needs to be > 20ms)
            if (!runnerLonely) {
                System.out.println("Green runner is lonely now. Time in milliseconds: " + elapsedTime);
            } else {
                System.out.println("Green runner is never lonely. Time in milliseconds: " + elapsedTime);
            }
            calcDone = true;
            try {
                Thread.sleep(3000);
            } catch (InterruptedException ex) {
                Logger.getLogger(Circle.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        angle += vels[0];
        if (angle > (2 * Math.PI)) {
            angle = 0.0;
//            System.out.println("Time for v1 to run 1 lapse: " + elapsedTime);
        }
        angle1 += vels[1];
        if (angle1 > (2 * Math.PI)) {
            angle1 = 0.0;
        }
        try {
            angle2 += vels[2];
            if (angle2 > (2 * Math.PI)) {
                angle2 = 0.0;
            }
            angle3 += vels[3];
            if (angle3 > (2 * Math.PI)) {
                angle3 = 0.0;
            }
            angle4 += vels[4];
            if (angle4 > (2 * Math.PI)) {
                angle4 = 0.0;
            }
            angle5 += vels[5];
            if (angle5 > (2 * Math.PI)) {
                angle5 = 0.0;
            }
            angle6 += vels[6];
            if (angle6 > (2 * Math.PI)) {
                angle6 = 0.0;
            }
            angle7 += vels[7];
            if (angle7 > (2 * Math.PI)) {
                angle7 = 0.0;
            }

        } catch (Exception e2) {
        }

        /**
         * Experimentation where the program constantly checks if the runner is lonely. Animation doesn't work as intented for the second runner for some reason.
         */
//                
//        double pathLength = (2 * Math.PI);
//        double kLength = (pathLength / vels.length);
//        If runner vel is 1/k away from vel1 (calculated from both directions with if and else if) 
//        if ((angle + angle1) >= pathLength && (pathLength - angle) + angle1 >= kLength && (angle + angle2) >= pathLength && (pathLength - angle) + angle2 >= kLength && (angle + angle3) >= pathLength && (pathLength - angle) + angle3 >= kLength && (angle + angle4) >= pathLength && (pathLength - angle) + angle4 >= kLength && (angle + angle5) >= pathLength && (pathLength - angle) + angle5 >= kLength && (angle + angle6) >= pathLength && (pathLength - angle) + angle6 >= kLength && (angle + angle7) >= pathLength && (pathLength - angle) + angle7 >= kLength && !lonelyDone) {
//            try {
//                System.out.println("tru1");
//                lonelyDone = true;
//                Thread.sleep(3000);
//            } catch (InterruptedException ex) {
//                Logger.getLogger(Circle.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        } else if (Math.abs(angle - angle1) >= kLength && Math.abs(angle - angle1) != pathLength && Math.abs(angle - angle2) >= kLength && Math.abs(angle - angle2) != pathLength && Math.abs(angle - angle3) >= kLength && Math.abs(angle - angle3) != pathLength && Math.abs(angle - angle4) >= kLength && Math.abs(angle - angle4) != pathLength && Math.abs(angle - angle5) >= kLength && Math.abs(angle - angle5) != pathLength && Math.abs(angle - angle6) >= kLength && Math.abs(angle - angle6) != pathLength && Math.abs(angle - angle7) >= kLength && Math.abs(angle - angle7) != pathLength && !lonelyDone) {
//            try {
//                System.out.println("angle - angle1: " + (Math.abs(angle - angle1)));
//                System.out.println("tru2");
//                lonelyDone = true;
//                Thread.sleep(3000);
//            } catch (InterruptedException ex) {
//                Logger.getLogger(Circle.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
        endTime = 0;
        elapsedTime = 0;
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (!backgroundBlack) {
            setBackground(Color.BLACK);
            backgroundBlack = true;
        }

        int width = getWidth();
        int height = getHeight();

        int x = (int) (Math.cos(angle) * (width / 3) + (width / 2));
        int y = (int) (Math.sin(angle) * (height / 3) + (height / 2));
        g.setColor(Color.green);
        g.fillOval(x, y, 10, 10);
        g.setColor(Color.white);

        int x1 = (int) (Math.cos(angle1) * (width / 3) + (width / 2));
        int y1 = (int) (Math.sin(angle1) * (height / 3) + (height / 2));
        g.fillOval(x1, y1, 10, 10);

        if (angle2 != 0) {
            int x2 = (int) (Math.cos(angle2) * (width / 3) + (width / 2));
            int y2 = (int) (Math.sin(angle2) * (height / 3) + (height / 2));
            g.fillOval(x2, y2, 10, 10);
        }
        if (angle3 != 0) {
            int x3 = (int) (Math.cos(angle3) * (width / 3) + (width / 2));
            int y3 = (int) (Math.sin(angle3) * (height / 3) + (height / 2));
            g.fillOval(x3, y3, 10, 10);
        }

        if (angle4 != 0) {
            int x4 = (int) (Math.cos(angle4) * (width / 3) + (width / 2));
            int y4 = (int) (Math.sin(angle4) * (height / 3) + (height / 2));
            g.fillOval(x4, y4, 10, 10);
        }

        if (angle5 != 0) {
            int x5 = (int) (Math.cos(angle5) * (width / 3) + (width / 2));
            int y5 = (int) (Math.sin(angle5) * (height / 3) + (height / 2));
            g.fillOval(x5, y5, 10, 10);
        }
        if (angle6 != 0) {
            int x6 = (int) (Math.cos(angle6) * (width / 3) + (width / 2));
            int y6 = (int) (Math.sin(angle6) * (height / 3) + (height / 2));
            g.fillOval(x6, y6, 10, 10);
        }
        if (angle7 != 0) {
            int x7 = (int) (Math.cos(angle7) * (width / 3) + (width / 2));
            int y7 = (int) (Math.sin(angle7) * (height / 3) + (height / 2));
            g.fillOval(x7, y7, 10, 10);
        }

        /*Circle as path for the runners */
        g.setColor(Color.green);
        g.fillOval(110, 100, 270, 270);
    }

    public static void startAnimation(double[] v, ArrayList aniR, int btnCount, boolean neverLonely) {
        int k = v.length;
        JFrame frame = new JFrame("LRC");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(500, 540);
        JPanel panel = new JPanel();
        if (btnCount < k - 1) {
            JButton next = new JButton("Next Runner");
            panel.add(next);
            panel.setBackground(Color.black);
            frame.add(panel, BorderLayout.SOUTH);
            frame.setVisible(true);

            next.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent event) {
                    LonelyRunner nextRunner = new LonelyRunner();
                    try {
                        frame.dispose();
                        nextRunner.kRunners((btnCount + 1), false, k, v);
                    } catch (IOException | InterruptedException ex) {
                        Logger.getLogger(Circle.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
        } else {
            JButton noNext = new JButton("This is the last runner! Click here to close the window.");
            panel.add(noNext);
            panel.setBackground(Color.black);
            noNext.setFocusPainted(false);
            noNext.setBorderPainted(false);
            noNext.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
            frame.add(panel, BorderLayout.SOUTH);
            frame.setVisible(true);

            noNext.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent event) {
                    frame.dispose();
                    timer.stop();

//                    0 = yes, 1 = no, 2 = cancel
                    int runAgainOption = showConfirmDialog(null, "Do you want to run any of the programs again?", "Select an option...", YES_NO_OPTION);
                    String[] runAgainArray = {"", ""};

                    if (runAgainOption == 0) {
                        try {
                            EvenRunners.main(runAgainArray);
                        } catch (IOException | InterruptedException ex) {
                            Logger.getLogger(Circle.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else {
                        showMessageDialog(null, "Goodbye!");
                        System.exit(0);
                    }
                }
            });
        }
        startTime = System.currentTimeMillis();
        frame.add(new Circle(v, aniR, btnCount, neverLonely));
//        System.out.println(startTime);
//        System.out.println("The list: " + aniR);
    }
}
