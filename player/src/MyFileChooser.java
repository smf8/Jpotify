import javax.swing.*;
import java.io.File;

public class MyFileChooser {
    private JButton open ;
    private JFileChooser fileChooser ;
    private String pickedFile;

    public MyFileChooser(){
        open = new JButton();
        fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory( new java.io.File("C:" + File.separator + "Users" + File.separator + "ASUS" + File.separator + "Desktop"));
        fileChooser.setDialogTitle("Choose File");
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
     //   fileChooser.setAcceptAllFileFilterUsed(false);

        if (fileChooser.showOpenDialog(open) == JFileChooser.APPROVE_OPTION){
            //////////
        }
        pickedFile = fileChooser.getSelectedFile().getAbsolutePath();
        System.out.println(pickedFile);
    }
}
