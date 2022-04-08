import javax.swing.text.JTextComponent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class ButtonLimit extends ButtonTools implements ActionListener {

    private final JTextComponent tf1;
    private final JTextComponent tf2;

    public ButtonLimit(String label, JTextComponent tf1, JTextComponent tf2) {
        setOpaque(true);
        setText(label);
        this.tf1 = tf1;
        this.tf2 = tf2;
        this.setVisible(true);
        setEnabled(true);
        addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        File functionsFile = new File("functions.txt");
        String newFileName = "/Users/kalilbouzigues/Desktop/Projets/Jupyter/JavAPP.py";
        File pyFile = new File(newFileName);
        try {
            functionsFile.createNewFile();
            ButtonTools.writeToPython(tf1, tf2, functionsFile, pyFile);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        ++occurred;
    }
}
