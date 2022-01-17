import frame.MainFrame;
import utils.DataUtil;

import javax.swing.*;
import java.awt.*;

public class DesktopApp {
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            var frame = new MainFrame();
            JTable table = null;
            try {
                table = new JTable(DataUtil.getDataFromDatabase(), DataUtil.getColumnNames());
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
            var contents = new Box(BoxLayout.PAGE_AXIS);
            contents.add(new JScrollPane(table));
            frame.setContentPane(contents);

            frame.setVisible(true);
        });

    }
}
