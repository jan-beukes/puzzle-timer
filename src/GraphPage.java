
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JFrame;

public class GraphPage extends JFrame { //The JFrame for the Graph page

    GraphPage() { //Constructs the JFrame and adds the Graph panel
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setResizable(false);

        GraphPanel gPanel = new GraphPanel();
        gPanel.setPreferredSize(new Dimension(610, 500));
        gPanel.setBackground(Color.DARK_GRAY);

        this.add(gPanel);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

}
