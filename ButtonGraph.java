import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.List;

public class ButtonGraph extends ButtonTools implements ActionListener{

    private final JTextComponent tf;
    private final String text;

    public ButtonGraph(String label, JTextComponent tf) {
        text = label;
        setText(label);
        this.tf = tf;
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
            ButtonTools.writeToPython(tf, functionsFile, pyFile);
        } catch (IOException ex) {
                ex.printStackTrace();
        }
        ++occurred;
    }

    public void reset() {
        setText(text);
        setForeground(Color.BLACK);
    }


}
