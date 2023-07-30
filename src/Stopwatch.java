
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import org.apache.commons.lang3.time.*;
import MyComponents.MyButton;

public class Stopwatch extends JPanel implements ActionListener { // JPanel which contains the Stopwatch and its
    // functionality

    private MyButton startButton, saveButton, add2Button, discardButton;
    private JLabel timeLabel = new JLabel();
    private int elapsedTime = 0;
    private int milliseconds = 0, seconds = 0, minutes = 0;
    public static boolean started = false, canPressSpace = true;

    private String millisecondsStr = String.format("%02d", milliseconds);
    private String secondsStr = String.format("%02d", seconds);
    private String minutesStr = String.format("%02d", minutes);
    private ImageIcon checkmark;
    private Font myFont;

    StopWatch stopwatch = new StopWatch();
    // Creates a timer that performs and action every 10 ms
    Timer timer = new Timer(10, new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {// when the timer performs the action
                                                    // sets the elapsedTime = to the current stopwatch time
                                                    // and calls method to display the time

            elapsedTime = (int) stopwatch.getTime();

            updateText(elapsedTime);

        }

    });

    public Stopwatch() { // Contructs the Stopwatch

        // gets the Custom font from the resources and creates a font
        // which is then registered to the Graphics environment
        try {
            InputStream is = getClass().getClassLoader().getResourceAsStream("DS-Digital.TTF");
            myFont = Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(100f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(myFont);
        } catch (IOException | FontFormatException e) {

        }

        StopWatch.create();
        // sets the checkmark icon to the image in the resources
        try {
            checkmark = new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/Checkmark.png")));
        } catch (IOException e) {

        }

        startButton = new MyButton("START");
        saveButton = new MyButton();
        add2Button = new MyButton("+2");
        discardButton = new MyButton("X");

        // Sets the Label which holds the current time of the stopwatch
        timeLabel.setText(minutesStr + ":" + secondsStr + "." + millisecondsStr);
        timeLabel.setBounds(133, 20, 334, 150);
        timeLabel.setFont(myFont);
        timeLabel.setForeground(Color.green);

        initButtons();

        this.add(startButton);
        this.add(saveButton);
        this.add(add2Button);
        this.add(discardButton);

        this.add(timeLabel);
        this.setPreferredSize(new Dimension(600, 280));
        this.setBorder(BorderFactory.createBevelBorder(1, Color.white, Color.black));
        this.setBackground(Color.darkGray);
        this.setLayout(null);

    }

    private void updateText(int e) { // Updates the text which is displaying the current time of the stopwatch
        minutes = e / 60000 % 60;
        seconds = e / 1000 % 60;
        milliseconds = Math.round(e / 10 % 100);

        millisecondsStr = String.format("%02d", milliseconds);
        secondsStr = String.format("%02d", seconds);
        minutesStr = String.format("%02d", minutes);
        timeLabel.setText(minutesStr + ":" + secondsStr + "." + millisecondsStr);

        // Stops the stopwatch if 1 hour is reached
        if (elapsedTime > 35999980) {
            stop();
        }

    }

    private void initButtons() { // Sets all the values for the buttons (size,colour,border etc)
        startButton.setBounds(60, 180, 195, 75);
        startButton.setFont(new Font("MV Boli", Font.BOLD, 45));
        startButton.setBackground(Color.white);
        startButton.setPressedColor(Color.lightGray);
        startButton.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        startButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        startButton.addActionListener(this);

        saveButton.setBounds(275, 180, 75, 75);
        saveButton.setIcon(checkmark);
        saveButton.setBackground(Color.white);
        saveButton.setPressedColor(Color.lightGray);
        saveButton.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        saveButton.setToolTipText("Save");
        saveButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        saveButton.addActionListener(this);
        saveButton.setEnabled(false);

        add2Button.setBounds(370, 180, 75, 75);
        add2Button.setFont(new Font("MV Boli", Font.PLAIN, 45));
        add2Button.setBackground(Color.white);
        add2Button.setPressedColor(Color.lightGray);
        add2Button.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        add2Button.setToolTipText("+2 seconds");
        add2Button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        add2Button.addActionListener(this);
        add2Button.setEnabled(false);

        discardButton.setBounds(465, 180, 75, 75);
        discardButton.setFont(new Font("MV Boli", Font.PLAIN, 50));
        discardButton.setBackground(Color.white);
        discardButton.setPressedColor(Color.lightGray);
        discardButton.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        discardButton.setToolTipText("Discard");
        discardButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        discardButton.addActionListener(this);
        discardButton.setEnabled(false);

    }

    @Override
    public void actionPerformed(ActionEvent e) { // called when an action is performed
                                                 // and takes an ActionEvent as a Parameter
        if (e.getSource() == startButton) { // When start button is pressed

            if (started == false) {
                start();

            } else {
                stop();

            }
        } else if (e.getSource() == saveButton) { // when save button is pressed
            saveTime();
            reset();

        } else if (e.getSource() == add2Button) { // when add2 button is pressed

            elapsedTime += 2000;
            saveTime();
            reset();
        }
        if (e.getSource() == discardButton) // when discard button is pressed
        {

            reset();
        }

    }

    public void setStartPressedColor(boolean pressed) { //sets the the colour of the start button if pressed
        if (pressed) {
            startButton.setBackground(Color.LIGHT_GRAY);
        } else if (pressed == false) {
            startButton.setBackground(Color.white);
        }

    }

    void saveTime() {
        Stats.addTime(elapsedTime);
    }

    public void start() { //Starts the Stopwatch and the timer and changes the text of the start button
        stopwatch.start();
        timer.start();
        started = true;
        startButton.setText("STOP");
    }

    public void stop() { //stops the Stopwatch and the timer, changes the text of the start button and disables it
        stopwatch.stop();
        timer.stop();
        canPressSpace = false;
        started = false;
        startButton.setText("START");
        startButton.setEnabled(false);

        saveButton.setEnabled(true);
        add2Button.setEnabled(true);
        discardButton.setEnabled(true);
    }

    void reset() { //resets the stopwatch and all the time values and enables the start button
        stopwatch.reset();
        elapsedTime = 0;
        milliseconds = 0;
        seconds = 0;
        minutes = 0;

        canPressSpace = true;

        millisecondsStr = String.format("%02d", milliseconds);
        secondsStr = String.format("%02d", seconds);
        minutesStr = String.format("%02d", minutes);
        timeLabel.setText(minutesStr + ":" + secondsStr + "." + millisecondsStr);
        startButton.setEnabled(true);

        saveButton.setEnabled(false);
        add2Button.setEnabled(false);
        discardButton.setEnabled(false);

    }

    public boolean getStarted() { //return if the stopwatch is started
        return started;
    }

    public boolean getCanPressSpace() { //return if the the user can press space
        return canPressSpace;
    }

}
