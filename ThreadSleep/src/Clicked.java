import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Clicked extends KeyEvent implements KeyListener {

    private boolean VK_0 = false;

    public Clicked(Component source,
                   int id,
                   long when,
                   int modifiers,
                   int keyCode,
                   char keyChar,
                   int keyLocation) {
        super(source, id, when, modifiers, keyCode, keyChar, keyLocation);
    }

    public boolean is0Down() {
        return VK_0;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        VK_0 = e.getKeyCode() == KeyEvent.VK_0;
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
