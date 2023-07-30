package MyComponents;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JButton;

public class MyButton extends JButton { // Custom JButton

    private Color hoverColor;
    private Color pressedColor;

    public MyButton() { // Constructs JButton without setting text

        this.setFocusable(false);
        this.setContentAreaFilled(false);
        this.setBorder(null);

    }

    public MyButton(String text) { // Constructs JButton with text

        this.setText(text);
        this.setFocusable(false);
        this.setContentAreaFilled(false);
        this.setBorder(null);
    }

    @Override
    public void paintComponent(Graphics g) { // Custom painting so that background and pressed colour can be changed

        if (this.getModel().isPressed()) {
            g.setColor(pressedColor);
        } else if (this.getModel().isRollover()) {
            hoverColor = this.getBackground();
            g.setColor(hoverColor);
        } else {
            g.setColor(this.getBackground());
        }

        g.fillRect(0, 0, getWidth(), getHeight());
        super.paintComponent(g);

    }

    public void setPressedColor(Color pressedColor) { // set the pressed colour of button
        this.pressedColor = pressedColor;
    }

}
