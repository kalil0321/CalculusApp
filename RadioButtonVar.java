//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.util.ArrayList;
//import java.util.List;
//
//import static java.awt.SystemColor.window;
//
//public class RadioButtonVar extends JRadioButton implements ActionListener {
//
//
//    private final ButtonGraph buttonGraph;
//    public static boolean onlyOneVariable;
//    private String[] variables;
//
//    public RadioButtonVar(String text, ButtonGraph buttonGraph){
//        this.buttonGraph = buttonGraph;
//        addActionListener(this);
//        setText(text);
//        setVisible(true);
//        setEnabled(true);
//    }
//
//    @Override
//    public void actionPerformed(ActionEvent e) {
//        update();
//    }
//
//    private void update() {
//        if(isSelected() && getText().equals("(x, y, z)")){
//            variables = new String[]{"x", "y", "z"};
//            buttonGraph.setEnabled(false);
//            JOptionPane.showMessageDialog(getParent(),
//                    "No plot available.",
//                    "Plotting Information",
//                    JOptionPane.INFORMATION_MESSAGE);
//            buttonGraph.setForeground(Color.RED);
//            buttonGraph.setText("Plot not available");
//        }else if(isSelected() && getText().equals("(x, y)")){
//            variables = new String[]{"x", "y"};
//            buttonGraph.setEnabled(true);
//            buttonGraph.reset();
//        }else if(isSelected() && getText().equals("x")){
//            variables = new String[]{"x"};
//            buttonGraph.reset();
//            onlyOneVariable = true;
//        }
//
//    }
//
//    public String[] getVariables(){
//        return variables;
//    }
//}
