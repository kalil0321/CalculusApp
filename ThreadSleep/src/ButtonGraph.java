import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class ButtonGraph extends ButtonTools implements ActionListener{

    private final JTextComponent tf;
    private final String text;
    private final JLabel nameOfField;

    public ButtonGraph(String label, JTextComponent tf, JLabel nameOfField) {
        text = label;
        setText(label);
        this.nameOfField = nameOfField;
        this.tf = tf;
        this.setVisible(true);
        setEnabled(true);
        addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        File functionsFile = new File("functions.txt");
        String newFileName = "Python/JavAPP.py";
        File pyFile = new File(newFileName);
        try {
            if(!functionsFile.createNewFile())
                return;
            ButtonTools.writeToPython(tf, functionsFile, pyFile);
            //JLabel label = new JLabel();
            //JTextArea tx = new JTextArea();//JLabel Creation

            File f = new File("output_javapp_"+ ButtonTools.occurred + ".png");
            System.out.println(f.exists());
            ImageIcon im = new ImageIcon(f.getName());
            //JPanel panel = new JPanel();
            //panel.setLayout(new GridLayout(2, 3));
            im.setDescription("LATEX");
            nameOfField.setIcon(im);
            nameOfField.setVisible(true);//Sets the location of the image
            //panel.add(tx);
//            tx.setText("FFFFF");
//            tx.setVisible(true);
//            tx.setEditable(true);
//            tx.setEnabled(true);
//            tx.setDragEnabled(true);
//            panel.setVisible(true);
//            panel.setEnabled(true);
//            .add(panel).setVisible(true);
//            panel.add(label);
//            panel.setVisible(true);

            //Adds objects to the container
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        ++occurred;
//        JLabel label = new JLabel(); //JLabel Creation
//        File f = new File("figure.png");
//        System.out.println(f.exists());
//        ImageIcon im = new ImageIcon("figure.png");
//        label.setIcon(im);
//        System.out.println("INN");//Sets the image to be displayed as an icon
//        label.setVisible(true);
//        Frame fr;
//        (fr =new Frame()).add(label);
//        fr.setVisible(true);//Adds objects to the container


    }

    public void reset() {
        setText(text);
        setForeground(Color.BLACK);
    }





}
