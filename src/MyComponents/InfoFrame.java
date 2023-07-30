package MyComponents;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.MouseInfo;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.SoftBevelBorder;

public class InfoFrame extends JFrame implements ActionListener { // Custom JFrame that I use for displaying the help

    private MyButton button;
    private ImageIcon icon;
    private JLabel label;

    public InfoFrame(String text, boolean useMouseLocation) { // Constructs an InfoFrame taking text and
                                                            // if the mouse location should be used as parameters

        // Initializing and customizing components
        button = new MyButton("ok");
        button.setBackground(Color.white);

        try {
            icon = new ImageIcon(ImageIO.read(this.getClass().getResourceAsStream("/SmallerHelpIcon.png")));
        } catch (IOException e) {
        }

        button.setPressedColor(Color.LIGHT_GRAY);
        button.setPreferredSize(new Dimension(80, 30));
        button.setBorder(BorderFactory.createSoftBevelBorder(SoftBevelBorder.RAISED));
        button.setFont(new Font("MV Boli", Font.PLAIN, 20));
        button.addActionListener(this);

        label = new JLabel(text);

        label.setFont(new Font("MV Boli", Font.PLAIN, 16));
        label.setIcon(icon);
        label.setVerticalTextPosition(JLabel.TOP);
        label.setForeground(Color.white);
        label.setIconTextGap(10);

        JPanel panel = new JPanel();
        panel.setBackground(Color.gray);

        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setResizable(false);
        this.getContentPane().setBackground(Color.gray);

        panel.add(button);
        this.add(label);
        this.add(panel, BorderLayout.SOUTH);

        this.pack();
        if (useMouseLocation) {
            this.setLocation(MouseInfo.getPointerInfo().getLocation());
        } else {
            this.setLocationRelativeTo(null);
        }
        this.setVisible(true);
    }

    public void setIcon(ImageIcon ic) { // sets the icon of the frame
        label.setIcon(ic);
    }

    @Override
    public void actionPerformed(ActionEvent e) {// Run when an action is performed
        if (e.getSource() == button) {// if the button is pressed closes the frame
            this.dispose();
        }

    }

}
