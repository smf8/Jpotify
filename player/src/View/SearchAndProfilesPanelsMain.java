package View;

import javax.swing.*;

public class SearchAndProfilesPanelsMain {
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        SearchAndProfilesPanel searchAndProfilePanel = new SearchAndProfilesPanel();
      //  searchAndProfilePanel.setProfileInformation("zahraJoon");
        frame.add(searchAndProfilePanel);
        frame.pack();
        frame.setVisible(true);
    }


}
