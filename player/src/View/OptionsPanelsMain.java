package View;

import javax.swing.*;
import java.awt.*;

public class OptionsPanelsMain {
    public static void main(String[] args) {
        JFrame frame = new JFrame("JPOTIFY");
        frame.setLayout(new GridBagLayout());
        OptionsPanel optionsPanel = new OptionsPanel();
        optionsPanel.addPlaylist("salam");
        optionsPanel.addPlaylist("f");
        optionsPanel.addPlaylist("f");
        optionsPanel.addPlaylist("f");
        optionsPanel.addPlaylist("f");
        optionsPanel.addPlaylist("f");
        optionsPanel.addPlaylist("f");
        optionsPanel.addPlaylist("f");
        optionsPanel.addPlaylist("f");
        optionsPanel.addPlaylist("f");
        optionsPanel.addPlaylist("f");
        optionsPanel.addPlaylist("f");
        optionsPanel.addPlaylist("f");
        optionsPanel.addPlaylist("f");
        optionsPanel.addPlaylist("e");
        optionsPanel.addPlaylist("r");
        optionsPanel.showingPlaylist();
        optionsPanel.removePlaylist("salam");
        optionsPanel.showingPlaylist();
        GridBagConstraints frameConstraints = new GridBagConstraints();

        JScrollPane scrollPane = new JScrollPane(optionsPanel,   ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setPreferredSize(new Dimension(200, 500));
        frameConstraints.gridx = 2;
        frameConstraints.gridy = 2;
        frameConstraints.weighty =2;
        frame.add(scrollPane, frameConstraints);
        frame.add(scrollPane);
        frame.pack();
        frame.setVisible(true);
    }


}
