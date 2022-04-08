import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

import static javax.swing.BoxLayout.LINE_AXIS;
import static javax.swing.BoxLayout.X_AXIS;
import static javax.swing.JOptionPane.INFORMATION_MESSAGE;

public class WindowFrame extends JFrame implements WindowListener{

    public WindowFrame(String title){
        setLayout(new GridLayout(10, 1));

        Label labelOfFunction = new Label("Enter the function : ");

        //To be generalized x, (x,y), (x,y,z);
        Label labelOfLimit = new Label("Limit at (0,0) :");

        Label labelOfDerivation = new Label("Derivation");

        //Graph and limit should be depending on variable
        JTextField tfOfFunction = new TextInput();
        JTextArea tfOfLimit = new JTextArea();
        JTextArea tfOfDerivative = new JTextArea();
        tfOfLimit.setEditable(false);

        tfOfDerivative.setEditable(false);
        tfOfDerivative.setVisible(true);

        ButtonGraph buttonGraph = new ButtonGraph("Show the graph", tfOfFunction);
        ButtonLimit buttonLimit = new ButtonLimit("Compute the limit", tfOfFunction, tfOfLimit);
        Button buttonDerivative = new Button("Derivative");

        buttonDerivative.addActionListener(e -> {
            try {
                File functionsFile = new File("functions.txt");
                String newFileName = "/Users/kalilbouzigues/Desktop/Projets/Jupyter/JavAPP.py";
                String function = tfOfFunction.getText();
                File pyFile = new File(newFileName);
                String var = JOptionPane.showInputDialog("enter the variable for the derivative");
                if(!function.contains(var) && !(var.equals("x") || var.equals("y"))){
                    JOptionPane.showMessageDialog(this, "Please check the function and the variables.");
                    return;
                }
                System.out.println("HHH");
                ButtonTools.derivate(tfOfFunction, tfOfDerivative, functionsFile, pyFile, var);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        tfOfFunction.addActionListener(buttonLimit);
        tfOfFunction.addActionListener(e -> buttonDerivative.getActionListeners()[0].actionPerformed(e));

        setTitle(title);
        addWindowListener(this);
        add(buttonGraph);
        add(buttonLimit);
        add(buttonDerivative);
        add(labelOfFunction);
        add(tfOfFunction);
        add(labelOfLimit);
        add(tfOfLimit);
        add(labelOfDerivation);
        add(tfOfDerivative);

        setResizable(false);
        setSize(800, 800);
        setVisible(true);

        labelOfFunction.setVisible(true);
        labelOfLimit.setEnabled(true);
        labelOfLimit.setVisible(true);
    }

    @Override
    public void windowOpened(WindowEvent e) {
        System.out.println("Hi");
        JOptionPane.showMessageDialog(this,"""
                Welcome on this basic app.
                It can be really helpful for verifying a few computation and seeing the graph of some function.
                Note that this app isn't optimized for computation.
                It's really basic and can be improved a lot. """, "Welcome ! 🥳",
                INFORMATION_MESSAGE);
        JOptionPane.showMessageDialog(this,"""
                You can see a few plots, compute some limits and derivative.
                You just got to enter a function and press the buttons.
                Please use the variable 'x', 'y' or 'z' as it is just the beginning
                and all functionalities hasn't yet been implemented.""", "How to use ? 📈",
                INFORMATION_MESSAGE);
    }

    @Override
    public void windowClosing(WindowEvent e) {
        e.getWindow().dispose();
    }

    @Override
    public void windowClosed(WindowEvent e) {
    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {
    }
}
