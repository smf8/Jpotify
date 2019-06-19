import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;

public class UI {
    private JButton button1;
    private JPanel optionsBar;
    private JLabel Songs;

    public UI() {
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        optionsBar.addMouseListener(new MouseAdapter() {
        });
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
