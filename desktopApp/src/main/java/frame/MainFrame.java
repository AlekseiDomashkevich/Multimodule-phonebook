package frame;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    public MainFrame() {
        super();
        var screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        var screenSizeWidth = screenSize.width;
        var screenSizeHeight = screenSize.height;

        setSize(screenSizeWidth / 2, screenSizeHeight / 2);
        setLocation(screenSizeWidth / 2 - getSize().width / 2, screenSizeHeight / 2 - getSize().height / 2);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Phonebook");
    }
}
