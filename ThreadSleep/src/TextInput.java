import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.*;

public class TextInput extends JTextField implements KeyListener {

    public TextInput(){
        addKeyListener(this);
    }


    @Override
    public void keyTyped(KeyEvent e) {
        Character s = e.getKeyChar();
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

}
