
import javax.swing.*;
import javax.swing.text.JTextComponent;
import javax.swing.text.html.parser.Parser;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static javax.swing.BoxLayout.LINE_AXIS;
import static javax.swing.BoxLayout.X_AXIS;
import static javax.swing.JOptionPane.INFORMATION_MESSAGE;
import static javax.swing.JOptionPane.INITIAL_VALUE_PROPERTY;

public class WindowFrame extends JFrame implements WindowListener{

    public WindowFrame(String title) throws IOException, ParserConfigurationException, SAXException {
        setLayout(new GridLayout(10, 1));

        JLabel labelOfFunction = new JLabel("Enter the function : ");

        //To be generalized x, (x,y), (x,y,z);
        JLabel labelOfLimit = new JLabel("Limit at (0,0) :");

        JLabel labelOfDerivation = new JLabel("Derivation");

        //Graph and limit should be depending on variable
        JPanel optionsPanel = new JPanel();
        JPanel backToMain = new JPanel();
        backToMain.setLayout(new BoxLayout(backToMain, LINE_AXIS));
        optionsPanel.setLayout(new GridLayout(3, 2));
        JTextField tfOfFunction = new TextInput();
        JTextArea tfOfLimit = new JTextArea();
        JTextArea tfOfDerivative = new JTextArea();
        tfOfLimit.setEditable(false);

        tfOfDerivative.setEditable(false);
        tfOfDerivative.setVisible(true);
        ButtonGraph buttonGraph = new ButtonGraph("Show the graph", tfOfFunction, labelOfFunction);
        ButtonLimit buttonLimit = new ButtonLimit("Compute the limit", tfOfFunction, tfOfLimit);
        Button buttonDerivative = new Button("Derivative");

        buttonDerivative.addActionListener(e -> {
            try {
                File functionsFile = new File("functions.txt");
                String newFileName = "Python/JavAPP.py";
                String function = tfOfFunction.getText();
                File pyFile = new File(newFileName);
                String var =
                        JOptionPane.showInputDialog("Enter the variable for the derivative");
                if(function != null && var != null ) {
                    if (!function.contains(var) && !(var.equals("x") || var.equals("y"))) {
                        JOptionPane.showMessageDialog(this, "Please check the function and the variables.");
                        return;
                    }
                }
                ButtonTools.derivate(tfOfFunction, tfOfDerivative, functionsFile, pyFile, var);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        backToMain.add(new Button("Main Menu"));
        backToMain.setVisible(true);
        backToMain.setEnabled(true);
        optionsPanel.add(buttonGraph);
        optionsPanel.add(buttonLimit);
        optionsPanel.add(buttonDerivative);
        optionsPanel.add(new Button("N-th derivative"));
        optionsPanel.add(new Button("Taylor series"));
        optionsPanel.add(new Button("Settings (variables)..."));



        tfOfFunction.addActionListener(buttonLimit);
        tfOfFunction.addActionListener(e -> buttonDerivative.getActionListeners()[0].actionPerformed(e));

        setTitle(title);
        addWindowListener(this);
//        add(buttonGraph);
//        add(buttonLimit);
//        add(buttonDerivative);
        add(backToMain);
        add(optionsPanel);
        optionsPanel.setVisible(true);
        optionsPanel.setEnabled(true);
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

        // JMathComponent j = new JMathComponent();
        // add(j);
        // j.setVisible(true);
        // j.setEnabled(true);
        // j.setBackground(Color.YELLOW);
        // j.setForeground(Color.RED);

        // /* Create vanilla SnuggleEngine and new SnuggleSession */
        // SnuggleEngine engine = new SnuggleEngine();
        // SnuggleSession session = engine.createSession();

        // /* Parse some very basic Math Mode input */
        // SnuggleInput input = new SnuggleInput("$$ x+2=3 $$");
        // session.parseInput(input);

    }

    @Override
    public void windowOpened(WindowEvent e) {
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
