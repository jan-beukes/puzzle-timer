
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

import MyComponents.InfoFrame;

public class MyFrame extends JFrame implements KeyListener { // My custom JFrame

    private static CardLayout cl;
    private static JPanel content;
    private static boolean isOnTimerScreen;
    private ImageIcon cubeIcon, smallCubeIcon;
    Stopwatch stopwatch;
    static StatsScreen statsScreen;

    static TimerScreen timerScreen;
    
    MyFrame() { //Constructs the JFrame
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Puzzle Timer");
        this.setResizable(false);

        // sets all the icons to their image contained in in the resources
        try {
            cubeIcon = new ImageIcon(ImageIO.read(this.getClass().getResourceAsStream("/CubeIcon.png")));
            smallCubeIcon = new ImageIcon(ImageIO.read(this.getClass().getResourceAsStream("/SmallerCubeIcon.png")));
        } catch (IOException e) {

        }

        this.setIconImage(cubeIcon.getImage());

        UserManager.loadUsers();
        
        Stats.loadTimes();

        timerScreen = new TimerScreen();

        GuideScreen guideScreen = new GuideScreen();

        statsScreen = new StatsScreen();
        
        UserScreen userScreen = new UserScreen();

        stopwatch = timerScreen.getStopwatch();

        content = new JPanel();
        cl = new CardLayout();
        content.setLayout(cl);
        content.setPreferredSize(new Dimension(900, 680));

        content.add(timerScreen, "1");
        content.add(guideScreen, "2");
        content.add(statsScreen, "3");
        content.add(userScreen, "4");

        cl.show(content, "1");
        isOnTimerScreen = true;

        this.addKeyListener(this);

        this.add(content);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);

        String welcome = "<html>WELCOME TO PUZZLE TIMER!<br><br>"
                + "This program will allow you to record<br>and save your Rubik’s cube solve times<br>"
                + "You will be able to see your statistics change<br>over time as you improve,"
                + "with the dedicated statistics page<br>"
                + "There are also useful guides and resources<br>that you can access by opening the guides page<br>"
                + "If you are ever lost or need help just<br>click on the help button in the upper left corner of every page<html>";
        // Displays the welcome message if the user is new
        if (UserManager.checkIfNew()) {
            InfoFrame f = new InfoFrame(welcome, false);
            f.setIcon(smallCubeIcon);
            f.setTitle("Welcome");
        }

    }

    public static void setScreen(String num) { // Sets the screen that is displaying on this JFrame
        cl.show(content, num);
        if (Integer.parseInt(num) == 3) {
            statsScreen.update();
        }
        
        if (Integer.parseInt(num) == 1) {
            isOnTimerScreen = true;
            timerScreen.updateUserIcon();
        } else {
            isOnTimerScreen = false;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) { //Must be Overriden when using KeyListener

    }

    @Override
    public void keyPressed(KeyEvent e) { // Called when key is pressed taking in the KeyEvent as a parameter
        //Stops the stopwatch if SPACE is pressed while it's running
        if (e.getKeyCode() == KeyEvent.VK_SPACE && isOnTimerScreen) {

            if (stopwatch.getCanPressSpace()) {

                stopwatch.setStartPressedColor(true);
                if (stopwatch.getStarted() == true) {
                    stopwatch.stop();
                }
            }
        }

    }

    @Override
    public void keyReleased(KeyEvent e) { // Called when key is released taking in the KeyEvent as a parameter
        //Starts the stopwatch if SPACE is pressed while it's not running
        if (e.getKeyCode() == KeyEvent.VK_SPACE && isOnTimerScreen) {
            stopwatch.setStartPressedColor(false);

            if (stopwatch.getStarted() == false && stopwatch.getCanPressSpace()) {
                stopwatch.start();
            }

        }

    }
}
